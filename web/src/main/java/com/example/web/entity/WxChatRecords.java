package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyc
 * @since 2023-04-12 03:37:41
 */
@Getter
@Setter
@TableName("siis_wx_chat_records")
public class WxChatRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 对话上下文
     */
    private String context;

    /**
     * 删除状态：0有效，1删除
     */
    private Byte deleted;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
