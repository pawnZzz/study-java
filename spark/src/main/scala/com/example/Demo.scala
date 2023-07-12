package com.example

import org.apache.spark.sql.SparkSession

/**
 * @description: TODO 
 * @author zyc
 * @date: 2023/7/6 
 */
object Demo {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().getOrCreate()

    spark.sqlContext.setConf("spark.sql.parquet.binaryAsString", "true")
    import spark.sql

    val pathWJ = "/root/airdocwj20230705/"
    val pathItem = "/root/airdocitem20230705/"
    val pathFAMI = "/root/airdocfami20230705/"

    val wjDF = spark.read.parquet(pathWJ)
    val itemDF = spark.read.parquet(pathItem)
    val faDF = spark.read.parquet(pathFAMI)
    wjDF.createOrReplaceTempView("wj_t")
    itemDF.createOrReplaceTempView("item_t")
    faDF.createOrReplaceTempView("fa_t")

    /* source.printSchema()

     itemDF.show(1, 10000000, true)

     source.createOrReplaceTempView("source_t")*/

    /**
     * item
     * |-- tradename: string (nullable = true)
     * |-- areaname: string (nullable = true)             区域
     * |-- uid: string (nullable = true)
     * |-- workno: string (nullable = true)               “健检号"，
     * |-- examusersex: string (nullable = true)          性别
     * |-- examage: long (nullable = true)                年龄
     * |-- ccode: string (nullable = true)                检查项编号
     * |-- cname: string (nullable = true)                ‘检查项名称！
     * |-- icode: string (nullable = true)                “指标编号，
     * |-- iname: string (nullable = true)                ，指标名称，
     * |-- iresultvalue_name: string (nullable = true)    结果
     * |-- iunit: string (nullable = true)                单位
     * |-- inormalhighvalue: string (nullable = true)     上限
     * |-- inormallowvalue: string (nullable = true)      下限
     * |-- regdate: string (nullable = true)              "体检时间，
     */

    /**
     * fami
     * |-- workno: string (nullable = true)
     * |-- ccode: string (nullable = true)
     * |-- fcode: string (nullable = true)
     * |-- biaozhun_fname: string (nullable = true)
     * |-- regdate: string (nullable = true)
     * 和item通过 workno 匹配
     * workno         | 000-1020181222090
     * ccode          | 3.2.1.6                                    检查项编号
     * fcode          | 3.2.2.112                                  •诊断编号，
     * biaozhun_fname | 甲状腺结节                                  • 诊断标准名称，
     * regdate        | 2018-12-22
     */

    /**
     * wenj
     * |-- workno: string (nullable = true)
     * |-- qresult: string (nullable = true)                  问卷
     * 和item通过 workno 匹配
     */


    /**
     * --------------------------------------------------------
     * 需求链接：
     * https://www.notion.so/iuvvv/2177b92fe28a4d79a72e3f13b871dc8a?pvs=4
     * --------------------------------------------------------
     * */
    val wjBingShiDF = sql(
      """
        | select
        |    workno, qas
        | from
        | (
        |   select
        |     workno, explode(from_json(get_json_object(result, '$.qas'), 'Array<String>')) as qas
        |   from
        |     (select
        |       workno, explode(from_json(get_json_object(qresult, '$.results'), 'Array<String>')) as result
        |     from wj_t
        |   ) t
        |   where get_json_object(result, '$.title')='健康史-现病史'
        | ) tt
        | where get_json_object(qas, '$.key')='您是否患有以下疾病:'
        |""".stripMargin)

    val faItemDF = faDF.withColumnRenamed("ccode", "f_ccode").join(itemDF, Seq("workno"))
    val bsWholeDF = wjBingShiDF.join(faItemDF, Seq("workno"))
    bsWholeDF.createOrReplaceTempView("bs_whole_t")

    // 1. 问卷人群的年龄和性别分布 wj + item
    val wjItemDF = wjDF.join(itemDF, Seq("workno"))

    wjItemDF.createOrReplaceTempView("wj_item_t")

    sql(
      """
        | select
        |   examusersex,
        |   count(distinct if(examage<=19, uid, null)) as `0_19`,
        |   count(distinct if(examage<=29 and examage>=20, uid, null)) as `20_29`,
        |   count(distinct if(examage<=39 and examage>=30, uid, null)) as `30_39`,
        |   count(distinct if(examage<=49 and examage>=40, uid, null)) as `40_49`,
        |   count(distinct if(examage<=59 and examage>=50, uid, null)) as `50_59`,
        |   count(distinct if(examage>=60, uid, null)) as `60以上`,
        |   count(distinct uid) as `总计`
        | from wj_item_t
        | group by examusersex
        |""".stripMargin).show(false)

    /**
     * +-----------+----+-----+-----+-----+-----+------+------+
     * |examusersex|0_19|20_29|30_39|40_49|50_59|60以上|总计  |
     * +-----------+----+-----+-----+-----+-----+------+------+
     * |男         |288 |39972|65662|40679|38282|24532 |207777|
     * |女         |305 |45925|60628|35810|36783|23342 |201441|
     * +-----------+----+-----+-----+-----+-----+------+------+
     */

    // 2. 糖尿病的判断
    sql(
      """
        | select
        |   count(distinct uid)
        | from bs_whole_t
        | where fcode='1.4.2.26' and icode='2.1.4.67' and iresultvalue_name>7.0
        |   and instr(get_json_object(qas, '$.value'), '糖尿病')>0
        |""".stripMargin).show()
    /** --------------------------- 6372 ---------------------------*/

    // 高血压病
    sql(
      """
        | select
        |     count(distinct t1.uid)
        | from
        | (select
        |   uid
        | from bs_whole_t
        | where fcode='1.1.2.162' and instr(get_json_object(qas, '$.value'), '高血压病')>0
        |       and icode='1.2.4.4' and iresultvalue_name>140
        | ) t1
        | join
        | (select
        |   uid
        | from bs_whole_t
        | where fcode='1.1.2.162' and instr(get_json_object(qas, '$.value'), '高血压病')>0
        |   and icode='1.2.4.5' and iresultvalue_name>90
        | ) t2 on t1.uid=t2.uid
        |""".stripMargin).show()
    /** 5119 */



  }
}
