/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.querys;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * Oracle 表数据查询
 *
 * @author hubin
 * @since 2018-01-16
 */
public class OracleQuery extends AbstractDbQuery {


    @Override
    public DbType dbType() {
        return DbType.ORACLE;
    }


    @Override
    public String tablesSql() {
        return "SELECT * FROM ALL_TAB_COMMENTS WHERE OWNER='%s'";
    }


    @Override
    public String tableFieldsSql() {
        return "SELECT A.COLUMN_name,A.NULLABLE, CASE WHEN A.DATa_type='NUMBER' THEN "
            + "(CASE WHEN A.DATA_PRECISION IS NULL THEN A.DATa_type "
            + "WHEN NVL(A.DATA_SCALE, 0) > 0 THEN A.DATa_type||'('||A.DATA_PRECISION||','||A.DATA_SCALE||')' "
            + "ELSE A.DATa_type||'('||A.DATA_PRECISION||')' END) "
            + "ELSE A.DATa_type END DATa_type, B.COMMENTS,DECODE(C.POSITION, '1', 'PRI') KEY "
            + "FROM ALL_TAB_COLUMNS A "
            + " INNER JOIN ALL_COL_COMMENTS B ON A.TABLE_name = B.TABLE_name AND A.COLUMN_name = B.COLUMN_name AND B.OWNER = '#schema'"
            + " LEFT JOIN ALL_CONSTRAINTS D ON D.TABLE_name = A.TABLE_name AND D.CONSTRAINt_type = 'P' AND D.OWNER = '#schema'"
            + " LEFT JOIN ALL_CONS_COLUMNS C ON C.CONSTRAINt_name = D.CONSTRAINt_name AND C.COLUMN_name=A.COLUMN_name AND C.OWNER = '#schema'"
            + "WHERE A.OWNER = '#schema' AND A.TABLE_name = '%s' ORDER BY A.COLUMN_id ";
    }


    @Override
    public String tableName() {
        return "TABLE_name";
    }


    @Override
    public String tableComment() {
        return "COMMENTS";
    }


    @Override
    public String fieldName() {
        return "COLUMN_name";
    }


    @Override
    public String fieldType() {
        return "DATa_type";
    }


    @Override
    public String fieldComment() {
        return "COMMENTS";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }

}
