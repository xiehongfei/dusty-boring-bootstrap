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
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum;
import com.dusty.boring.mybatis.sql.common.utils.JsonUtils;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.provider.AbstractSqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DbTypeEnum;
import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.SQL_COUNT_EXPRESSION;
import static com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum.SQL9000;
import static com.dusty.boring.mybatis.sql.common.pool.SqlErrorCodeEnum.SQL9003;
import static com.dusty.boring.mybatis.sql.validater.SqlValidateResult.Violation;

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
public class MySqlValidateVisitor extends SqlValidateVisitorAdapter {
    
    private final MySqlValidateProvider sqlValidateProvider;
    private final SqlValidatorProperties sqlValidatorProperties;
    private final List<Violation> violations = Lists.newArrayList();
    
    
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
    public void preVisit(SQLObject sqlObject) {
        SqlValidateUtils.preVisitCheck(this, sqlObject);
    }
    
    @Override
    public void postVisit(SQLObject sqlObject) {
    
    }
    
    @Override
    public boolean visit(SQLAllColumnExpr sqlAllColumnExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLBetweenExpr sqlBetweenExpr) {
        System.out.println(String.format("访问到Between语法\n-\t%s\n-\t %s", sqlBetweenExpr.getBeginExpr().toString(), sqlBetweenExpr.getEndExpr().toString()));
        return true;
    }
    
    @Override
    public boolean visit(SQLBinaryOpExpr sqlBinaryOpExpr) {
        System.out.println(String.format("访问到sqlBinaryOpExpr:%s", JsonUtils.object2String(sqlBinaryOpExpr.getOperator())));
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
        
        final SqlValidatorProperties.MySqlValidateItems mySqlValidItems = getSqlValidatorProperties().getMySqlValidItems();
        
        if (mySqlValidItems.isEnableDdl())
            return true;
        
        addViolation(new Violation(SqlErrorCodeEnum.SQL9001));
        return false;
    }
    
    
    @Override
    public boolean visit(SQLCaseExpr sqlCaseExpr) {
        System.out.println(sqlCaseExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseExpr.Item item) {
        System.out.println(item);
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseStatement sqlCaseStatement) {
        System.out.println(sqlCaseStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLCaseStatement.Item item) {
        System.out.println(item);
        return true;
    }
    
    @Override
    public boolean visit(SQLCastExpr sqlCastExpr) {
        System.out.println(sqlCastExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLCharExpr sqlCharExpr) {
        System.out.println(sqlCharExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLExistsExpr sqlExistsExpr) {
        System.out.println(sqlExistsExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLIdentifierExpr sqlIdentifierExpr) {
        System.out.println(sqlIdentifierExpr.getName());
        return true;
    }
    
    @Override
    public boolean visit(SQLInListExpr sqlInListExpr) {
        System.out.println(sqlInListExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLIntegerExpr sqlIntegerExpr) {
        System.out.println(sqlIntegerExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLNCharExpr sqlnCharExpr) {
        System.out.println(sqlnCharExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLNotExpr sqlNotExpr) {
        System.out.println(sqlNotExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLNullExpr sqlNullExpr) {
        System.out.println(sqlNullExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLNumberExpr sqlNumberExpr) {
        System.out.println(sqlNumberExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLPropertyExpr sqlPropertyExpr) {
        System.out.println(sqlPropertyExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectGroupByClause sqlSelectGroupByClause) {
        System.out.println(sqlSelectGroupByClause);
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectItem sqlSelectItem) {
        System.out.println(sqlSelectItem);
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectStatement sqlSelectStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAggregateExpr x) {
        System.out.println(x);
        
        return true;
    }
    
    @Override
    public boolean visit(SQLVariantRefExpr x) {
        System.out.println(x);
        return true;
    }
    
    @Override
    public boolean visit(SQLQueryExpr x) {
        System.out.println(x);
        return true;
    }
    
    @Override
    public boolean visit(SQLUnaryExpr sqlUnaryExpr) {
        System.out.println(sqlUnaryExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLHexExpr sqlHexExpr) {
        System.out.println(sqlHexExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLSelect sqlSelect) {
    
        final SQLSelectQuery query = sqlSelect.getQuery();
        if (query instanceof MySqlSelectQueryBlock) {
            return validateSelectQueryBlock((MySqlSelectQueryBlock) query);
        }
        
        System.out.println(sqlSelect);
        return true;
    }
    
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
                addViolation(new Violation(SQL9003));
                return false;
            }
        }
    
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectQueryBlock sqlSelectQueryBlock) {
       return validateSelectQueryBlock(sqlSelectQueryBlock);
    }
    
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        System.out.println(sqlExprTableSource);
        return true;
    }
    
    @Override
    public boolean visit(SQLOrderBy sqlOrderBy) {
        System.out.println(sqlOrderBy);
        return true;
    }
    
    @Override
    public boolean visit(SQLSelectOrderByItem sqlSelectOrderByItem) {
        System.out.println(sqlSelectOrderByItem);
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
        System.out.println(sqlColumnDefinition);
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnDefinition.Identity identity) {
        System.out.println(identity);
        return true;
    }
    
    @Override
    public boolean visit(SQLDataType sqlDataType) {
        System.out.println(sqlDataType);
        return true;
    }
    
    @Override
    public boolean visit(SQLCharacterDataType sqlCharacterDataType) {
        System.out.println(sqlCharacterDataType);
        return true;
    }
    
    @Override
    public boolean visit(SQLDeleteStatement sqlDeleteStatement) {
        System.out.println(sqlDeleteStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLCurrentOfCursorExpr sqlCurrentOfCursorExpr) {
        return true;
    }
    
    @Override
    public boolean visit(SQLInsertStatement sqlInsertStatement) {
        System.out.println(sqlInsertStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLInsertStatement.ValuesClause valuesClause) {
        System.out.println(valuesClause);
        return true;
    }
    
    @Override
    public boolean visit(SQLUpdateSetItem sqlUpdateSetItem) {
        System.out.println(sqlUpdateSetItem);
        return true;
    }
    
    @Override
    public boolean visit(SQLUpdateStatement sqlUpdateStatement) {
        System.out.println(sqlUpdateStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement sqlCreateViewStatement) {
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement.Column column) {
        System.out.println(column);
        return true;
    }
    
    @Override
    public boolean visit(SQLNotNullConstraint sqlNotNullConstraint) {
        System.out.println(sqlNotNullConstraint);
        return true;
    }
    
    @Override
    public boolean visit(SQLMethodInvokeExpr sqlMethodInvokeExpr) {
        System.out.println(sqlMethodInvokeExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLUnionQuery sqlUnionQuery) {
        System.out.println(sqlUnionQuery);
        return true;
    }
    
    @Override
    public boolean visit(SQLSetStatement sqlSetStatement) {
        System.out.println(sqlSetStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLAssignItem sqlAssignItem) {
        System.out.println(sqlAssignItem);
        return true;
    }
    
    @Override
    public boolean visit(SQLCallStatement sqlCallStatement) {
        System.out.println(sqlCallStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLJoinTableSource sqlJoinTableSource) {
        System.out.println(sqlJoinTableSource);
        return true;
    }
    
    @Override
    public boolean visit(SQLSomeExpr sqlSomeExpr) {
        System.out.println(sqlSomeExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLAnyExpr sqlAnyExpr) {
        System.out.println(sqlAnyExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLAllExpr sqlAllExpr) {
        System.out.println(sqlAllExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLInSubQueryExpr sqlInSubQueryExpr) {
        if (getSqlValidatorProperties().getMySqlValidItems().isEnableSqlInSubQuery()) {
            return true;
        }
        
        System.out.println(sqlInSubQueryExpr);
        addViolation(new Violation(SQL9000));
        return false;
    }
    
    @Override
    public boolean visit(SQLListExpr sqlListExpr) {
        System.out.println(sqlListExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLSubqueryTableSource sqlSubqueryTableSource) {
        System.out.println(sqlSubqueryTableSource);
        final SQLSelectQuery query = sqlSubqueryTableSource.getSelect().getQuery();
        if (query instanceof MySqlSelectQueryBlock) {
            return validateSelectQueryBlock((MySqlSelectQueryBlock) query);
        }
        sqlSubqueryTableSource.getSelect();
        return true;
    }
    
    @Override
    public boolean visit(SQLTruncateStatement sqlTruncateStatement) {
        System.out.println(sqlTruncateStatement);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDefaultExpr sqlDefaultExpr) {
        System.out.println(sqlDefaultExpr);
        return true;
    }
    
    @Override
    public boolean visit(SQLCommentStatement sqlCommentStatement) {
        System.out.println(sqlCommentStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLUseStatement sqlUseStatement) {
        System.out.println(sqlUseStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddColumn sqlAlterTableAddColumn) {
        System.out.println(sqlAlterTableAddColumn);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropColumnItem sqlAlterTableDropColumnItem) {
        System.out.println(sqlAlterTableDropColumnItem);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropIndex sqlAlterTableDropIndex) {
        System.out.println(sqlAlterTableDropIndex);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropIndexStatement sqlDropIndexStatement) {
        System.out.println(sqlDropIndexStatement);
       return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLDropViewStatement sqlDropViewStatement) {
        System.out.println(sqlDropViewStatement);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLSavePointStatement sqlSavePointStatement) {
        System.out.println(sqlSavePointStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLRollbackStatement sqlRollbackStatement) {
        System.out.println(sqlRollbackStatement);
        return true;
    }
    
    @Override
    public boolean visit(SQLReleaseSavePointStatement sqlReleaseSavePointStatement) {
        System.out.println();
        return true;
    }
    
    @Override
    public boolean visit(SQLCommentHint sqlCommentHint) {
        System.out.println();
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateDatabaseStatement sqlCreateDatabaseStatement) {
        System.out.println(sqlCreateDatabaseStatement);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLOver sqlOver) {
        System.out.println(sqlOver);
        return true;
    }
    
    @Override
    public boolean visit(SQLKeep sqlKeep) {
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnPrimaryKey sqlColumnPrimaryKey) {
        System.out.println(sqlColumnPrimaryKey);
        return true;
    }
    
    @Override
    public boolean visit(SQLColumnUniqueKey sqlColumnUniqueKey) {
        System.out.println(sqlColumnUniqueKey);
        return true;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause sqlWithSubqueryClause) {
        System.out.println(sqlWithSubqueryClause);
        return true;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause.Entry entry) {
        System.out.println(entry);
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableAlterColumn sqlAlterTableAlterColumn) {
    
        System.out.println(sqlAlterTableAlterColumn);
        return true;
    }
    
    @Override
    public boolean visit(SQLCheck sqlCheck) {
        System.out.println(sqlCheck);
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropForeignKey sqlAlterTableDropForeignKey) {
    
        System.out.println(sqlAlterTableDropForeignKey);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPrimaryKey sqlAlterTableDropPrimaryKey) {
        System.out.println(sqlAlterTableDropPrimaryKey);
        return enabledDdlStatement();
    }
    
    
    @Override
    public boolean visit(SQLAlterTableDisableKeys sqlAlterTableDisableKeys) {
        System.out.println(sqlAlterTableDisableKeys);
        return enabledDdlStatement();
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableKeys sqlAlterTableEnableKeys) {
        System.out.println(sqlAlterTableEnableKeys);
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
        System.out.println(sqlColumnCheck);
        return true;
    }
    
    @Override
    public boolean visit(SQLExprHint sqlExprHint) {
        System.out.println(sqlExprHint);
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropConstraint sqlAlterTableDropConstraint) {
        System.out.println(sqlAlterTableDropConstraint);
        return true;
    }
    
    @Override
    public boolean visit(SQLUnique sqlUnique) {
        System.out.println(sqlUnique);
        return true;
    }
    
    @Override
    public boolean visit(SQLPrimaryKeyImpl sqlPrimaryKey) {
        System.out.println(sqlPrimaryKey);
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateIndexStatement sqlCreateIndexStatement) {
        return true;
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
        return true;
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
        return true;
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
        return true;
    }
    
    @Override
    public boolean visit(SQLCreateFunctionStatement sqlCreateFunctionStatement) {
        return true;
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
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableConvertCharSet sqlAlterTableConvertCharSet) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableReOrganizePartition sqlAlterTableReOrganizePartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableCoalescePartition sqlAlterTableCoalescePartition) {
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableTruncatePartition sqlAlterTableTruncatePartition) {
        return true;
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
        return true;
    }
    
    @Override
    public boolean visit(SQLDropServerStatement sqlDropServerStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropSynonymStatement sqlDropSynonymStatement) {
        return true;
    }
    
    @Override
    public boolean visit(SQLRecordDataType sqlRecordDataType) {
        return true;
    }
    
    @Override
    public boolean visit(SQLDropTypeStatement sqlDropTypeStatement) {
        return true;
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
        return true;
    }
    
    @Override
    public boolean visit(SQLAlterTableRenameIndex sqlAlterTableRenameIndex) {
        return true;
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
