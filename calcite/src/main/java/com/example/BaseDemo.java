package com.example;

import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlVisitor;

/**
 * @author zyc
 */
public class BaseDemo {
    public static void main(String[] args) throws SqlParseException {
        /*String sql = "SELECT o.id, o.name, c.created FROM ods_nvwa_check_info c "
                + "INNER JOIN ods_pangu_organizer o ON c.org_id = o.id "
                + "INNER JOIN ods_pangu_user u ON c.submit_user_id = u.user_id "
                + "WHERE c.ds='20230216' AND c.created >= '2022-01-01' AND c.created < '2023-01-01' "
                + "AND c.deleted = 0 AND c.org_id NOT IN (1) AND c.review_status >= 2";

        SqlParser parser = SqlParser.create(sql);
        SqlNode node = null;
        try {
            node = parser.parseStmt();
        } catch (SqlParseException e) {
            System.out.println("Failed to parse SQL statement: " + e.getMessage());
            return;
        }

        SqlVisitor<Void> visitor = new SqlVisitorImpl<Void>() {
            @Override
            public Void visit(SqlIdentifier id) {
                System.out.println("Column: " + id.toString() + " | Table: " + id.names.get(0));
                return null;
            }
        };

        node.accept(visitor);*/
    }
}
