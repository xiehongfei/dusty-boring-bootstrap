/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月16日 20:45
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.visitor;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.provider.AbstractSqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties.MySqlValidateItems;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DbTypeEnum;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.SQL_COUNT_EXPRESSION;
import static com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum.*;
import static com.dusty.boring.mybatis.sql.validater.SqlValidateResult.*;

/**
 * <pre>
 *
 *       <SqlValidateVisitor>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月16日 20:45
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月16日 20:45
 * </pre>
 */
@Slf4j
public class MySqlValidateVisitor extends SqlValidateVisitorAdapter {
    
    private final MySqlValidateProvider sqlValidateProvider;
    private final SqlValidatorProperties sqlValidatorProperties;
    private final List<Violation> violations       = Lists.newArrayList();
    private final Map<String, TableInfo> tables       = Maps.newHashMap();
    private final Map<String, TableR2Column> columns  = Maps.newHashMap();
    private final Map<String, TableR2Column> orderColumns = Maps.newHashMap();
    
    
    public MySqlValidateVisitor(MySqlValidateProvider sqlValidateProvider) {
        this.sqlValidateProvider = sqlValidateProvider;
        this.sqlValidatorProperties = sqlValidateProvider.getSqlValidatorProperties();
    }
    
    @Override
    public String getDbType() {
        return DbTypeEnum.MySql.name();
    }
    
    @Override
    public AbstractSqlValidateProvider getSqlValidateProvider() {
        return sqlValidateProvider;
    }
    
    @Override
    public SqlValidatorProperties getSqlValidatorProperties() {
        return sqlValidatorProperties;
    }
    
    @Override
    public List<Violation> getViolations() {
        return violations;
    }
    
    @Override
    public void addViolation(Violation violation) {
        this.violations.add(violation);
    }
    
    @Override
    public Map<String, TableInfo> getTables() {
        return tables;
    }
    
    @Override
    public Map<String, TableR2Column> getColumns() {
        return columns;
    }
    
    @Override
    public Map<String, TableR2Column> getOrderColumns() {
        return orderColumns;
    }
    
    
    @Override
    public void preVisit(SQLObject sqlObject) {
        SqlValidateUtils.preVisitCheck(this, sqlObject);
    }
    
    @Override
    public void postVisit(SQLObject sqlObject) {
        //检查索引使用
//        final Map<String, TableInfo> tables = getTables();
//        final Iterator<Map.Entry<String, TableInfo>> iterator = tables.entrySet().iterator();
//        while (iterator.hasNext()) {
//            final Map.Entry<String, TableInfo> next = iterator.next();
//            final TableInfo table = next.getValue();
//            System.out.println(String.format("表信息-%s\t%s\t%s", next.getKey(), table.getTableName(), table.getTableAliasName()));
//        }
//        System.out.println("模拟检查...");
    }
    
    @Override
    public boolean visit(SQLAllColumnExpr sqlAllColumnExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLBetweenExpr sqlBetweenExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLBinaryOpExpr sqlBinaryOpExpr) {
        
        final SQLExpr left = sqlBinaryOpExpr.getLeft();
        if (left instanceof SQLPropertyExpr) {
    
            SQLPropertyExpr leftPropExpr = (SQLPropertyExpr) left;
            if (!columns.containsKey(Objects.toString(leftPropExpr))) {
    
                TableR2Column leftR2Column =
                        TableR2Column
                                .builder()
                                .columnName(leftPropExpr.getName())
                                .tableName((leftPropExpr.getOwnernName()))
                                .tableAliasName(leftPropExpr.getOwnernName())
                                .operator(sqlBinaryOpExpr.getOperator())
                                .build();
                columns.put(Objects.toString(leftPropExpr), leftR2Column);
            }
        }
        
        final SQLExpr right = sqlBinaryOpExpr.getRight();
        
        if (right instanceof SQLPropertyExpr) {
        
            SQLPropertyExpr rightPropExpr = (SQLPropertyExpr) right;
        
            if (!columns.containsKey(Objects.toString(rightPropExpr))) {
                TableR2Column rightR2Column =
                        TableR2Column
                                .builder()
                                .columnName(rightPropExpr.getName())
                                .tableName(rightPropExpr.getOwnernName())
                                .tableAliasName(rightPropExpr.getOwnernName())
                                .operator(sqlBinaryOpExpr.getOperator())
                                .build();
                columns.put(rightPropExpr.getName(), rightR2Column);
            }
        }
        
        return true;
    }
    
    /**
     * <pre>
     *     是否允许执行DDL语句，并执行相关操作
     *     - 加入违规sql
     *
     * @return boolean
     *             - true
     *             - false
     * </pre>
     */
    private boolean enabledDdlStatement() {
        
        final MySqlValidateItems mySqlValidItems = getSqlValidatorProperties().getMySqlValidItems();
        
        if (mySqlValidItems.isEnableDdl())
            return true;
        
        addViolation(new Violation(SqlErrorCodeEnum.SQL9001));
        return false;
    }
    
    
    @Override
    public boolean visit(SQLCaseExpr sqlCaseExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseExpr.Item item) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseStatement sqlCaseStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseStatement.Item item) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCastExpr sqlCastExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCharExpr sqlCharExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLExistsExpr sqlExistsExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIdentifierExpr sqlIdentifierExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLInListExpr sqlInListExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIntegerExpr sqlIntegerExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNCharExpr sqlnCharExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNotExpr sqlNotExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNullExpr sqlNullExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNumberExpr sqlNumberExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPropertyExpr sqlPropertyExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectGroupByClause sqlSelectGroupByClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectItem sqlSelectItem) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectStatement sqlSelectStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAggregateExpr x) {
        return true;
    }
    
    @Override
    public boolean visit(SQLVariantRefExpr x) {
        return true;
    }
    
    @Override
    public boolean visit(SQLQueryExpr x) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUnaryExpr sqlUnaryExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLHexExpr sqlHexExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSelect sqlSelect) {
    
        final SQLSelectQuery query = sqlSelect.getQuery();
        if (query instanceof MySqlSelectQueryBlock) {
            return validateSelectQueryBlock((MySqlSelectQueryBlock) query);
        }
        
        return true;
    }
    
    /**
     * <pre>
     *     验证select是否包含where条件
     *
     * @param sqlSelectQueryBlock
     * @return
     * </pre>
     */
    private boolean validateSelectQueryBlock(SQLSelectQueryBlock sqlSelectQueryBlock) {
        
        if (Objects.isNull(sqlSelectQueryBlock.getWhere()) && Objects.isNull(sqlSelectQueryBlock.getLimit())) {
            boolean hasCnt = false;
            final List<SQLSelectItem> selectList = sqlSelectQueryBlock.getSelectList();
            for (SQLSelectItem item : selectList) {
                if (SQL_COUNT_EXPRESSION.equalsIgnoreCase(item.getExpr().toString())) {
                    hasCnt = true;
                    break;
                }
            }
        
            if (!hasCnt && getSqlValidatorProperties().getMySqlValidItems().isEnableWhereCheck()) {
                
                addViolation(new Violation(SQL9003, sqlSelectQueryBlock.toString()));
                return false;
            }
        } else {
            final SQLExpr where = sqlSelectQueryBlock.getWhere();
            where.getAttributes();
            
        }
    
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectQueryBlock sqlSelectQueryBlock) {
       return validateSelectQueryBlock(sqlSelectQueryBlock);
    }
    
    /**
     * <pre>
     *     获取查询表信息
     *
     * @param  sqlExprTableSource sqlExprTableSource
     * @return 是否继续访问
     * </pre>
     */
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        
        
        String alias = sqlExprTableSource.getAlias();
        if (StringUtils.isEmpty(alias)) {
            alias = sqlExprTableSource.getName().getSimpleName();
        }
        
        if (StringUtils.isEmpty(alias)) {
            return true;
        }
        
        if (!tables.containsKey(alias)) {
            tables.put(alias,
                    TableInfo
                            .builder()
                            .tableName(sqlExprTableSource.getName().getSimpleName())
                            .owner(sqlExprTableSource.getSchema())
                            .tableAliasName(alias)
                            .build());
        }
        return true;
    }
    
    @Override
    public boolean visit(SQLOrderBy sqlOrderBy) {
        
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectOrderByItem item) {
    
        if (item.getExpr() instanceof SQLPropertyExpr) {
            SQLPropertyExpr prop = (SQLPropertyExpr) item.getExpr();
            TableR2Column tR2Col =
                    TableR2Column
                            .builder()
                            .tableName(prop.getOwnernName())
                            .tableAliasName(prop.getOwnernName())
                            .columnName(prop.getName())
                            .build();
            getOrderColumns().put(prop.getName(), tR2Col);
        }
       
        return true;
    }
    
    @Override
    public boolean visit(SQLDropTableStatement sqlDropTableStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLCreateTableStatement sqlCreateTableStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLColumnDefinition sqlColumnDefinition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnDefinition.Identity identity) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDataType sqlDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCharacterDataType sqlCharacterDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDeleteStatement sqlDeleteStatement) {
        
        //检查删除操作是否包含where条件
        if (Objects.isNull(sqlDeleteStatement.getWhere())
                && getSqlValidatorProperties().getMySqlValidItems().isEnableWhereCheck()) {
            addViolation(new Violation(SQL9004));
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean visit(SQLCurrentOfCursorExpr sqlCurrentOfCursorExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLInsertStatement sqlInsertStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLInsertStatement.ValuesClause valuesClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUpdateSetItem sqlUpdateSetItem) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUpdateStatement sqlUpdateStatement) {
    
        //检查更新操作是否包含where条件
        if (Objects.isNull(sqlUpdateStatement.getWhere())
                && getSqlValidatorProperties().getMySqlValidItems().isEnableWhereCheck()) {
            addViolation(new Violation(SQL9005));
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement sqlCreateViewStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement.Column column) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNotNullConstraint sqlNotNullConstraint) {
        return true;
    }
    
    @Override
    public boolean visit(SQLMethodInvokeExpr sqlMethodInvokeExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUnionQuery sqlUnionQuery) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSetStatement sqlSetStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAssignItem sqlAssignItem) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCallStatement sqlCallStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLJoinTableSource sqlJoinTableSource) {
        
        if (sqlJoinTableSource.getParent() instanceof MySqlUpdateStatement
                && Objects.nonNull(sqlJoinTableSource.getLeft())
                && Objects.nonNull(sqlJoinTableSource.getLeft())
                && Objects.nonNull(sqlJoinTableSource.getRight()) ) {
            //告警：关联太多表进行更新操作
            log.warn("\n-\tWARN: 使用过多JOIN语句，请检查是否需要优化表结构！");
        }
        
        return true;
    }
    
    @Override
    public boolean visit(SQLSomeExpr sqlSomeExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAnyExpr sqlAnyExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAllExpr sqlAllExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLInSubQueryExpr sqlInSubQueryExpr) {
        if (getSqlValidatorProperties().getMySqlValidItems().isEnableSqlInSubQuery()) {
            return true;
        }
        
        addViolation(new Violation(SQL9000));
        return false;
    }
    
    @Override
    public boolean visit(SQLListExpr sqlListExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSubqueryTableSource sqlSubqueryTableSource) {
        
        if (StringUtils.isNotEmpty(sqlSubqueryTableSource.getAlias())) {
            
            final SQLSelectQuery query = sqlSubqueryTableSource.getSelect().getQuery();
            if (query instanceof MySqlSelectQueryBlock) {
                
                final SQLTableSource from = ((MySqlSelectQueryBlock) query).getFrom();
                if (from instanceof SQLExprTableSource) {
                    
                    String[] nameArr = StringUtils.split(Objects.toString(((SQLExprTableSource) from).getName()), ".");
                    String dbName = null, tableName = null;
                    
                    if (nameArr.length == 1) {
                        tableName = nameArr[0];
                    } else if (nameArr.length == 2){
                        dbName = nameArr[0];
                        tableName = nameArr[1];
                    }
                    
                    String aliasName = sqlSubqueryTableSource.getAlias();
                    if (StringUtils.isEmpty(aliasName)) {
                        aliasName = from.getAlias();
                    }
                    
                    if(StringUtils.isEmpty(aliasName)) {
                        aliasName = tableName;
                    }
                    
                    if (StringUtils.isEmpty(aliasName)) {
                        return true;
                    }
    
                    if (!tables.containsKey(aliasName)) {
                        getTables().put(aliasName,
                                TableInfo
                                        .builder()
                                        .tableAliasName(sqlSubqueryTableSource.getAlias())
                                        .owner(dbName)
                                        .tableName(tableName)
                                        .tableType(2)
                                        .build());
                    }
                }
            }
        }
        
        return true;
    }
    
    @Override
    public boolean visit(SQLTruncateStatement sqlTruncateStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDefaultExpr sqlDefaultExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCommentStatement sqlCommentStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUseStatement sqlUseStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddColumn sqlAlterTableAddColumn) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropColumnItem sqlAlterTableDropColumnItem) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropIndex sqlAlterTableDropIndex) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropIndexStatement sqlDropIndexStatement) {
       return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropViewStatement sqlDropViewStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLSavePointStatement sqlSavePointStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLRollbackStatement sqlRollbackStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLReleaseSavePointStatement sqlReleaseSavePointStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCommentHint sqlCommentHint) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateDatabaseStatement sqlCreateDatabaseStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLOver sqlOver) {
        return true;
    }
    
    @Override
    public boolean visit(SQLKeep sqlKeep) {
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnPrimaryKey sqlColumnPrimaryKey) {
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnUniqueKey sqlColumnUniqueKey) {
        return true;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause sqlWithSubqueryClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause.Entry entry) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableAlterColumn sqlAlterTableAlterColumn) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCheck sqlCheck) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropForeignKey sqlAlterTableDropForeignKey) {
    
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPrimaryKey sqlAlterTableDropPrimaryKey) {
        return enabledDdlStatement();
    }
    
    
    @Override
    public boolean visit(SQLAlterTableDisableKeys sqlAlterTableDisableKeys) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableKeys sqlAlterTableEnableKeys) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableStatement sqlAlterTableStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableConstraint sqlAlterTableDisableConstraint) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableConstraint sqlAlterTableEnableConstraint) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLColumnCheck sqlColumnCheck) {
        return true;
    }
    
    @Override
    public boolean visit(SQLExprHint sqlExprHint) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropConstraint sqlAlterTableDropConstraint) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUnique sqlUnique) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPrimaryKeyImpl sqlPrimaryKey) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateIndexStatement sqlCreateIndexStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableRenameColumn sqlAlterTableRenameColumn) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLColumnReference sqlColumnReference) {
        return true;
    }
    
    @Override
    public boolean visit(SQLForeignKeyImpl sqlForeignKey) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropSequenceStatement sqlDropSequenceStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropTriggerStatement sqlDropTriggerStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropUserStatement sqlDropUserStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLExplainStatement sqlExplainStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLGrantStatement sqlGrantStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropDatabaseStatement sqlDropDatabaseStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableAddIndex sqlAlterTableAddIndex) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableAddConstraint sqlAlterTableAddConstraint) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLCreateTriggerStatement sqlCreateTriggerStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropFunctionStatement sqlDropFunctionStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropTableSpaceStatement sqlDropTableSpaceStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropProcedureStatement sqlDropProcedureStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLBooleanExpr sqlBooleanExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLUnionQueryTableSource sqlUnionQueryTableSource) {
        return true;
    }
    
    @Override
    public boolean visit(SQLTimestampExpr sqlTimestampExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLRevokeStatement sqlRevokeStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLBinaryExpr sqlBinaryExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableRename sqlAlterTableRename) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterViewRenameStatement sqlAlterViewRenameStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLShowTablesStatement sqlShowTablesStatement) {
        addViolation(new Violation(SqlErrorCodeEnum.SQL9000));
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddPartition sqlAlterTableAddPartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPartition sqlAlterTableDropPartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableRenamePartition sqlAlterTableRenamePartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableSetComment sqlAlterTableSetComment) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableSetLifecycle sqlAlterTableSetLifecycle) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableLifecycle sqlAlterTableEnableLifecycle) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableLifecycle sqlAlterTableDisableLifecycle) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableTouch sqlAlterTableTouch) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLArrayExpr sqlArrayExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLOpenStatement sqlOpenStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLFetchStatement sqlFetchStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCloseStatement sqlCloseStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLGroupingSetExpr sqlGroupingSetExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIfStatement sqlIfStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIfStatement.ElseIf elseIf) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIfStatement.Else anElse) {
        return true;
    }
    
    @Override
    public boolean visit(SQLLoopStatement sqlLoopStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLParameter sqlParameter) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateProcedureStatement sqlCreateProcedureStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLCreateFunctionStatement sqlCreateFunctionStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLBlockStatement sqlBlockStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropKey sqlAlterTableDropKey) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDeclareItem sqlDeclareItem) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPartitionValue sqlPartitionValue) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPartition sqlPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPartitionByRange sqlPartitionByRange) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPartitionByHash sqlPartitionByHash) {
        return true;
    }
    
    @Override
    public boolean visit(SQLPartitionByList sqlPartitionByList) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSubPartition sqlSubPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSubPartitionByHash sqlSubPartitionByHash) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSubPartitionByList sqlSubPartitionByList) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterDatabaseStatement sqlAlterDatabaseStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableConvertCharSet sqlAlterTableConvertCharSet) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableReOrganizePartition sqlAlterTableReOrganizePartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableCoalescePartition sqlAlterTableCoalescePartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableTruncatePartition sqlAlterTableTruncatePartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDiscardPartition sqlAlterTableDiscardPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableImportPartition sqlAlterTableImportPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableAnalyzePartition sqlAlterTableAnalyzePartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableCheckPartition sqlAlterTableCheckPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableOptimizePartition sqlAlterTableOptimizePartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableRebuildPartition sqlAlterTableRebuildPartition) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableRepairPartition sqlAlterTableRepairPartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLSequenceExpr sqlSequenceExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLMergeStatement sqlMergeStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLMergeStatement.MergeUpdateClause mergeUpdateClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLMergeStatement.MergeInsertClause mergeInsertClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLErrorLoggingClause sqlErrorLoggingClause) {
        return true;
    }
    
    @Override
    public boolean visit(SQLNullConstraint sqlNullConstraint) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateSequenceStatement sqlCreateSequenceStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDateExpr sqlDateExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLLimit sqlLimit) {
        return true;
    }
    
    @Override
    public boolean visit(SQLStartTransactionStatement sqlStartTransactionStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDescribeStatement sqlDescribeStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLWhileStatement sqlWhileStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDeclareStatement sqlDeclareStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLReturnStatement sqlReturnStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLArgument sqlArgument) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCommitStatement sqlCommitStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLFlashbackExpr sqlFlashbackExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateMaterializedViewStatement sqlCreateMaterializedViewStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLBinaryOpExprGroup sqlBinaryOpExprGroup) {
        return true;
    }
    
    @Override
    public boolean visit(SQLScriptCommitStatement sqlScriptCommitStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLReplaceStatement sqlReplaceStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateUserStatement sqlCreateUserStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterFunctionStatement sqlAlterFunctionStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTypeStatement sqlAlterTypeStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLIntervalExpr sqlIntervalExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLLateralViewTableSource sqlLateralViewTableSource) {
        return true;
    }
    
    @Override
    public boolean visit(SQLShowErrorsStatement sqlShowErrorsStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterCharacter sqlAlterCharacter) {
        return true;
    }
    
    @Override
    public boolean visit(SQLExprStatement sqlExprStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterProcedureStatement sqlAlterProcedureStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterViewStatement sqlAlterViewStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropEventStatement sqlDropEventStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropLogFileGroupStatement sqlDropLogFileGroupStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropServerStatement sqlDropServerStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropSynonymStatement sqlDropSynonymStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLRecordDataType sqlRecordDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropTypeStatement sqlDropTypeStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLExternalRecordFormat sqlExternalRecordFormat) {
        return true;
    }
    
    @Override
    public boolean visit(SQLArrayDataType sqlArrayDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLMapDataType sqlMapDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLStructDataType sqlStructDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLStructDataType.Field field) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropMaterializedViewStatement sqlDropMaterializedViewStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableRenameIndex sqlAlterTableRenameIndex) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterSequenceStatement sqlAlterSequenceStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableExchangePartition sqlAlterTableExchangePartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLValuesExpr sqlValuesExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLValuesTableSource sqlValuesTableSource) {
        return true;
    }
    
    @Override
    public boolean visit(SQLContainsExpr sqlContainsExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLRealExpr sqlRealExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLWindow sqlWindow) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDumpStatement sqlDumpStatement) {
        return true;
    }
}
