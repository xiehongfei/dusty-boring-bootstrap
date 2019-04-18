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
import com.dusty.boring.mybatis.sql.autoconfig.SqlValidatorProperties;
import com.dusty.boring.mybatis.sql.validater.SqlValidateUtils;
import com.dusty.boring.mybatis.sql.validater.provider.AbstractSqlValidateProvider;
import com.dusty.boring.mybatis.sql.validater.provider.MySqlValidateProvider;
import com.google.common.collect.Lists;

import java.util.List;

import static com.dusty.boring.mybatis.sql.common.pool.MyBatisConstPool.DbTypeEnum;
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
    
//        sqlBetweenExpr.getBeginExpr();
//        sqlBetweenExpr.getEndExpr();
        return true;
    }
    
    @Override
    public boolean visit(SQLBinaryOpExpr sqlBinaryOpExpr) {
        
        return false;
    }
    
    @Override
    public boolean visit(SQLCaseExpr sqlCaseExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCaseExpr.Item item) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCaseStatement sqlCaseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCaseStatement.Item item) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCastExpr sqlCastExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCharExpr sqlCharExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExistsExpr sqlExistsExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIdentifierExpr sqlIdentifierExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLInListExpr sqlInListExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIntegerExpr sqlIntegerExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNCharExpr sqlnCharExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNotExpr sqlNotExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNullExpr sqlNullExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNumberExpr sqlNumberExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPropertyExpr sqlPropertyExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelectGroupByClause sqlSelectGroupByClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelectItem sqlSelectItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelectStatement sqlSelectStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAggregateExpr x) {
    
        
        return false;
    }
    
    @Override
    public boolean visit(SQLVariantRefExpr sqlVariantRefExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLQueryExpr sqlQueryExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUnaryExpr sqlUnaryExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLHexExpr sqlHexExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelect sqlSelect) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelectQueryBlock sqlSelectQueryBlock) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExprTableSource sqlExprTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLOrderBy sqlOrderBy) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSelectOrderByItem sqlSelectOrderByItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropTableStatement sqlDropTableStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateTableStatement sqlCreateTableStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnDefinition sqlColumnDefinition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnDefinition.Identity identity) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDataType sqlDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCharacterDataType sqlCharacterDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDeleteStatement sqlDeleteStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCurrentOfCursorExpr sqlCurrentOfCursorExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLInsertStatement sqlInsertStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLInsertStatement.ValuesClause valuesClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUpdateSetItem sqlUpdateSetItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUpdateStatement sqlUpdateStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement sqlCreateViewStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateViewStatement.Column column) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNotNullConstraint sqlNotNullConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLMethodInvokeExpr sqlMethodInvokeExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUnionQuery sqlUnionQuery) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSetStatement sqlSetStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAssignItem sqlAssignItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCallStatement sqlCallStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLJoinTableSource sqlJoinTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSomeExpr sqlSomeExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAnyExpr sqlAnyExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAllExpr sqlAllExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLInSubQueryExpr sqlInSubQueryExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLListExpr sqlListExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSubqueryTableSource sqlSubqueryTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLTruncateStatement sqlTruncateStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDefaultExpr sqlDefaultExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCommentStatement sqlCommentStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUseStatement sqlUseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddColumn sqlAlterTableAddColumn) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropColumnItem sqlAlterTableDropColumnItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropIndex sqlAlterTableDropIndex) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropIndexStatement sqlDropIndexStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropViewStatement sqlDropViewStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSavePointStatement sqlSavePointStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLRollbackStatement sqlRollbackStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLReleaseSavePointStatement sqlReleaseSavePointStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCommentHint sqlCommentHint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateDatabaseStatement sqlCreateDatabaseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLOver sqlOver) {
        return false;
    }
    
    @Override
    public boolean visit(SQLKeep sqlKeep) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnPrimaryKey sqlColumnPrimaryKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnUniqueKey sqlColumnUniqueKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause sqlWithSubqueryClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLWithSubqueryClause.Entry entry) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAlterColumn sqlAlterTableAlterColumn) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCheck sqlCheck) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropForeignKey sqlAlterTableDropForeignKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPrimaryKey sqlAlterTableDropPrimaryKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableKeys sqlAlterTableDisableKeys) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableKeys sqlAlterTableEnableKeys) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableStatement sqlAlterTableStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableConstraint sqlAlterTableDisableConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableConstraint sqlAlterTableEnableConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnCheck sqlColumnCheck) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExprHint sqlExprHint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropConstraint sqlAlterTableDropConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUnique sqlUnique) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPrimaryKeyImpl sqlPrimaryKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateIndexStatement sqlCreateIndexStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRenameColumn sqlAlterTableRenameColumn) {
        return false;
    }
    
    @Override
    public boolean visit(SQLColumnReference sqlColumnReference) {
        return false;
    }
    
    @Override
    public boolean visit(SQLForeignKeyImpl sqlForeignKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropSequenceStatement sqlDropSequenceStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropTriggerStatement sqlDropTriggerStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropUserStatement sqlDropUserStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExplainStatement sqlExplainStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLGrantStatement sqlGrantStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropDatabaseStatement sqlDropDatabaseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddIndex sqlAlterTableAddIndex) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddConstraint sqlAlterTableAddConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateTriggerStatement sqlCreateTriggerStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropFunctionStatement sqlDropFunctionStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropTableSpaceStatement sqlDropTableSpaceStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropProcedureStatement sqlDropProcedureStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLBooleanExpr sqlBooleanExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLUnionQueryTableSource sqlUnionQueryTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLTimestampExpr sqlTimestampExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLRevokeStatement sqlRevokeStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLBinaryExpr sqlBinaryExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRename sqlAlterTableRename) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterViewRenameStatement sqlAlterViewRenameStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLShowTablesStatement sqlShowTablesStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAddPartition sqlAlterTableAddPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropPartition sqlAlterTableDropPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRenamePartition sqlAlterTableRenamePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableSetComment sqlAlterTableSetComment) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableSetLifecycle sqlAlterTableSetLifecycle) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableEnableLifecycle sqlAlterTableEnableLifecycle) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDisableLifecycle sqlAlterTableDisableLifecycle) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableTouch sqlAlterTableTouch) {
        return false;
    }
    
    @Override
    public boolean visit(SQLArrayExpr sqlArrayExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLOpenStatement sqlOpenStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLFetchStatement sqlFetchStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCloseStatement sqlCloseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLGroupingSetExpr sqlGroupingSetExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIfStatement sqlIfStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIfStatement.ElseIf elseIf) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIfStatement.Else anElse) {
        return false;
    }
    
    @Override
    public boolean visit(SQLLoopStatement sqlLoopStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLParameter sqlParameter) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateProcedureStatement sqlCreateProcedureStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateFunctionStatement sqlCreateFunctionStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLBlockStatement sqlBlockStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDropKey sqlAlterTableDropKey) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDeclareItem sqlDeclareItem) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPartitionValue sqlPartitionValue) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPartition sqlPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPartitionByRange sqlPartitionByRange) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPartitionByHash sqlPartitionByHash) {
        return false;
    }
    
    @Override
    public boolean visit(SQLPartitionByList sqlPartitionByList) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSubPartition sqlSubPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSubPartitionByHash sqlSubPartitionByHash) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSubPartitionByList sqlSubPartitionByList) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterDatabaseStatement sqlAlterDatabaseStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableConvertCharSet sqlAlterTableConvertCharSet) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableReOrganizePartition sqlAlterTableReOrganizePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableCoalescePartition sqlAlterTableCoalescePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableTruncatePartition sqlAlterTableTruncatePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableDiscardPartition sqlAlterTableDiscardPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableImportPartition sqlAlterTableImportPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableAnalyzePartition sqlAlterTableAnalyzePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableCheckPartition sqlAlterTableCheckPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableOptimizePartition sqlAlterTableOptimizePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRebuildPartition sqlAlterTableRebuildPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRepairPartition sqlAlterTableRepairPartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLSequenceExpr sqlSequenceExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLMergeStatement sqlMergeStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLMergeStatement.MergeUpdateClause mergeUpdateClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLMergeStatement.MergeInsertClause mergeInsertClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLErrorLoggingClause sqlErrorLoggingClause) {
        return false;
    }
    
    @Override
    public boolean visit(SQLNullConstraint sqlNullConstraint) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateSequenceStatement sqlCreateSequenceStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDateExpr sqlDateExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLLimit sqlLimit) {
        return false;
    }
    
    @Override
    public boolean visit(SQLStartTransactionStatement sqlStartTransactionStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDescribeStatement sqlDescribeStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLWhileStatement sqlWhileStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDeclareStatement sqlDeclareStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLReturnStatement sqlReturnStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLArgument sqlArgument) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCommitStatement sqlCommitStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLFlashbackExpr sqlFlashbackExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateMaterializedViewStatement sqlCreateMaterializedViewStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLBinaryOpExprGroup sqlBinaryOpExprGroup) {
        return false;
    }
    
    @Override
    public boolean visit(SQLScriptCommitStatement sqlScriptCommitStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLReplaceStatement sqlReplaceStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLCreateUserStatement sqlCreateUserStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterFunctionStatement sqlAlterFunctionStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTypeStatement sqlAlterTypeStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLIntervalExpr sqlIntervalExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLLateralViewTableSource sqlLateralViewTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLShowErrorsStatement sqlShowErrorsStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterCharacter sqlAlterCharacter) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExprStatement sqlExprStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterProcedureStatement sqlAlterProcedureStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterViewStatement sqlAlterViewStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropEventStatement sqlDropEventStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropLogFileGroupStatement sqlDropLogFileGroupStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropServerStatement sqlDropServerStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropSynonymStatement sqlDropSynonymStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLRecordDataType sqlRecordDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropTypeStatement sqlDropTypeStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLExternalRecordFormat sqlExternalRecordFormat) {
        return false;
    }
    
    @Override
    public boolean visit(SQLArrayDataType sqlArrayDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLMapDataType sqlMapDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLStructDataType sqlStructDataType) {
        return false;
    }
    
    @Override
    public boolean visit(SQLStructDataType.Field field) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDropMaterializedViewStatement sqlDropMaterializedViewStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableRenameIndex sqlAlterTableRenameIndex) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterSequenceStatement sqlAlterSequenceStatement) {
        return false;
    }
    
    @Override
    public boolean visit(SQLAlterTableExchangePartition sqlAlterTableExchangePartition) {
        return false;
    }
    
    @Override
    public boolean visit(SQLValuesExpr sqlValuesExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLValuesTableSource sqlValuesTableSource) {
        return false;
    }
    
    @Override
    public boolean visit(SQLContainsExpr sqlContainsExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLRealExpr sqlRealExpr) {
        return false;
    }
    
    @Override
    public boolean visit(SQLWindow sqlWindow) {
        return false;
    }
    
    @Override
    public boolean visit(SQLDumpStatement sqlDumpStatement) {
        return false;
    }
}
