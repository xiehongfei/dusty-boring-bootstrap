/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 13:51
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.mybatis.sql.validater.visitor;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.dusty.boring.mybatis.sql.validater.visitor.SqlValidateVisitor;

/**
 * <pre>
 *
 *       <Sql验证访问器适配器>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月17日 13:51
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月17日 13:51
 * </pre>
 */
public abstract class SqlValidateVisitorAdapter implements SqlValidateVisitor {
    
    
    @Override
    public void endVisit(SQLAllColumnExpr sqlAllColumnExpr) {
    
    }
    
    @Override
    public void endVisit(SQLBetweenExpr sqlBetweenExpr) {
    
    }
    
    @Override
    public void endVisit(SQLBinaryOpExpr sqlBinaryOpExpr) {
    
    }
    
    @Override
    public void endVisit(SQLCaseExpr sqlCaseExpr) {
    
    }
    
    @Override
    public void endVisit(SQLCaseExpr.Item item) {
    
    }
    
    @Override
    public void endVisit(SQLCaseStatement sqlCaseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCaseStatement.Item item) {
    
    }
    
    @Override
    public void endVisit(SQLCharExpr sqlCharExpr) {
    
    }
    
    @Override
    public void endVisit(SQLIdentifierExpr sqlIdentifierExpr) {
    
    }
    
    @Override
    public void endVisit(SQLInListExpr sqlInListExpr) {
    
    }
    
    @Override
    public void endVisit(SQLIntegerExpr sqlIntegerExpr) {
    
    }
    
    @Override
    public void endVisit(SQLExistsExpr sqlExistsExpr) {
    
    }
    
    @Override
    public void endVisit(SQLNCharExpr sqlnCharExpr) {
    
    }
    
    @Override
    public void endVisit(SQLNotExpr sqlNotExpr) {
    
    }
    
    @Override
    public void endVisit(SQLNullExpr sqlNullExpr) {
    
    }
    
    @Override
    public void endVisit(SQLNumberExpr sqlNumberExpr) {
    
    }
    
    @Override
    public void endVisit(SQLPropertyExpr sqlPropertyExpr) {
    
    }
    
    @Override
    public void endVisit(SQLSelectGroupByClause sqlSelectGroupByClause) {
    
    }
    
    @Override
    public void endVisit(SQLSelectItem sqlSelectItem) {
    
    }
    
    @Override
    public void endVisit(SQLSelectStatement sqlSelectStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCastExpr sqlCastExpr) {
    
    }
    
    @Override
    public void endVisit(SQLAggregateExpr sqlAggregateExpr) {
    
    }
    
    @Override
    public void endVisit(SQLVariantRefExpr sqlVariantRefExpr) {
    
    }
    
    @Override
    public void endVisit(SQLQueryExpr sqlQueryExpr) {
    
    }
    
    @Override
    public void endVisit(SQLUnaryExpr sqlUnaryExpr) {
    
    }
    
    @Override
    public void endVisit(SQLHexExpr sqlHexExpr) {
    
    }
    
    @Override
    public void endVisit(SQLSelect sqlSelect) {
    
    }
    
    @Override
    public void endVisit(SQLSelectQueryBlock sqlSelectQueryBlock) {
    
    }
    
    @Override
    public void endVisit(SQLExprTableSource sqlExprTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLOrderBy sqlOrderBy) {
    
    }
    
    @Override
    public void endVisit(SQLSelectOrderByItem sqlSelectOrderByItem) {
    
    }
    
    @Override
    public void endVisit(SQLDropTableStatement sqlDropTableStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCreateTableStatement sqlCreateTableStatement) {
    
    }
    
    @Override
    public void endVisit(SQLColumnDefinition sqlColumnDefinition) {
    
    }
    
    @Override
    public void endVisit(SQLColumnDefinition.Identity identity) {
    
    }
    
    @Override
    public void endVisit(SQLDataType sqlDataType) {
    
    }
    
    @Override
    public void endVisit(SQLCharacterDataType sqlCharacterDataType) {
    
    }
    
    @Override
    public void endVisit(SQLDeleteStatement sqlDeleteStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCurrentOfCursorExpr sqlCurrentOfCursorExpr) {
    
    }
    
    @Override
    public void endVisit(SQLInsertStatement sqlInsertStatement) {
    
    }
    
    @Override
    public void endVisit(SQLInsertStatement.ValuesClause valuesClause) {
    
    }
    
    @Override
    public void endVisit(SQLUpdateSetItem sqlUpdateSetItem) {
    
    }
    
    @Override
    public void endVisit(SQLUpdateStatement sqlUpdateStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCreateViewStatement sqlCreateViewStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCreateViewStatement.Column column) {
    
    }
    
    @Override
    public void endVisit(SQLNotNullConstraint sqlNotNullConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLMethodInvokeExpr sqlMethodInvokeExpr) {
    
    }
    
    @Override
    public void endVisit(SQLUnionQuery sqlUnionQuery) {
    
    }
    
    @Override
    public void endVisit(SQLSetStatement sqlSetStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAssignItem sqlAssignItem) {
    
    }
    
    @Override
    public void endVisit(SQLCallStatement sqlCallStatement) {
    
    }
    
    @Override
    public void endVisit(SQLJoinTableSource sqlJoinTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLSomeExpr sqlSomeExpr) {
    
    }
    
    @Override
    public void endVisit(SQLAnyExpr sqlAnyExpr) {
    
    }
    
    @Override
    public void endVisit(SQLAllExpr sqlAllExpr) {
    
    }
    
    @Override
    public void endVisit(SQLInSubQueryExpr sqlInSubQueryExpr) {
    
    }
    
    @Override
    public void endVisit(SQLListExpr sqlListExpr) {
    
    }
    
    @Override
    public void endVisit(SQLSubqueryTableSource sqlSubqueryTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLTruncateStatement sqlTruncateStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDefaultExpr sqlDefaultExpr) {
    
    }
    
    @Override
    public void endVisit(SQLCommentStatement sqlCommentStatement) {
    
    }
    
    @Override
    public void endVisit(SQLUseStatement sqlUseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAddColumn sqlAlterTableAddColumn) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropColumnItem sqlAlterTableDropColumnItem) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropIndex sqlAlterTableDropIndex) {
    
    }
    
    @Override
    public void endVisit(SQLDropIndexStatement sqlDropIndexStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropViewStatement sqlDropViewStatement) {
    
    }
    
    @Override
    public void endVisit(SQLSavePointStatement sqlSavePointStatement) {
    
    }
    
    @Override
    public void endVisit(SQLRollbackStatement sqlRollbackStatement) {
    
    }
    
    @Override
    public void endVisit(SQLReleaseSavePointStatement sqlReleaseSavePointStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCommentHint sqlCommentHint) {
    
    }
    
    @Override
    public void endVisit(SQLCreateDatabaseStatement sqlCreateDatabaseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLOver sqlOver) {
    
    }
    
    @Override
    public void endVisit(SQLKeep sqlKeep) {
    
    }
    
    @Override
    public void endVisit(SQLColumnPrimaryKey sqlColumnPrimaryKey) {
    
    }
    
    @Override
    public void endVisit(SQLColumnUniqueKey sqlColumnUniqueKey) {
    
    }
    
    @Override
    public void endVisit(SQLWithSubqueryClause sqlWithSubqueryClause) {
    
    }
    
    @Override
    public void endVisit(SQLWithSubqueryClause.Entry entry) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAlterColumn sqlAlterTableAlterColumn) {
    
    }
    
    @Override
    public void endVisit(SQLCheck sqlCheck) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropForeignKey sqlAlterTableDropForeignKey) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropPrimaryKey sqlAlterTableDropPrimaryKey) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDisableKeys sqlAlterTableDisableKeys) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableEnableKeys sqlAlterTableEnableKeys) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableStatement sqlAlterTableStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDisableConstraint sqlAlterTableDisableConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableEnableConstraint sqlAlterTableEnableConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLColumnCheck sqlColumnCheck) {
    
    }
    
    @Override
    public void endVisit(SQLExprHint sqlExprHint) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropConstraint sqlAlterTableDropConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLUnique sqlUnique) {
    
    }
    
    @Override
    public void endVisit(SQLPrimaryKeyImpl sqlPrimaryKey) {
    
    }
    
    @Override
    public void endVisit(SQLCreateIndexStatement sqlCreateIndexStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRenameColumn sqlAlterTableRenameColumn) {
    
    }
    
    @Override
    public void endVisit(SQLColumnReference sqlColumnReference) {
    
    }
    
    @Override
    public void endVisit(SQLForeignKeyImpl sqlForeignKey) {
    
    }
    
    @Override
    public void endVisit(SQLDropSequenceStatement sqlDropSequenceStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropTriggerStatement sqlDropTriggerStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropUserStatement sqlDropUserStatement) {
    
    }
    
    @Override
    public void endVisit(SQLExplainStatement sqlExplainStatement) {
    
    }
    
    @Override
    public void endVisit(SQLGrantStatement sqlGrantStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropDatabaseStatement sqlDropDatabaseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAddIndex sqlAlterTableAddIndex) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAddConstraint sqlAlterTableAddConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLCreateTriggerStatement sqlCreateTriggerStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropFunctionStatement sqlDropFunctionStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropTableSpaceStatement sqlDropTableSpaceStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropProcedureStatement sqlDropProcedureStatement) {
    
    }
    
    @Override
    public void endVisit(SQLBooleanExpr sqlBooleanExpr) {
    
    }
    
    @Override
    public void endVisit(SQLUnionQueryTableSource sqlUnionQueryTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLTimestampExpr sqlTimestampExpr) {
    
    }
    
    @Override
    public void endVisit(SQLRevokeStatement sqlRevokeStatement) {
    
    }
    
    @Override
    public void endVisit(SQLBinaryExpr sqlBinaryExpr) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRename sqlAlterTableRename) {
    
    }
    
    @Override
    public void endVisit(SQLAlterViewRenameStatement sqlAlterViewRenameStatement) {
    
    }
    
    @Override
    public void endVisit(SQLShowTablesStatement sqlShowTablesStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAddPartition sqlAlterTableAddPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropPartition sqlAlterTableDropPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRenamePartition sqlAlterTableRenamePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableSetComment sqlAlterTableSetComment) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableSetLifecycle sqlAlterTableSetLifecycle) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableEnableLifecycle sqlAlterTableEnableLifecycle) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDisableLifecycle sqlAlterTableDisableLifecycle) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableTouch sqlAlterTableTouch) {
    
    }
    
    @Override
    public void endVisit(SQLArrayExpr sqlArrayExpr) {
    
    }
    
    @Override
    public void endVisit(SQLOpenStatement sqlOpenStatement) {
    
    }
    
    @Override
    public void endVisit(SQLFetchStatement sqlFetchStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCloseStatement sqlCloseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLGroupingSetExpr sqlGroupingSetExpr) {
    
    }
    
    @Override
    public void endVisit(SQLIfStatement sqlIfStatement) {
    
    }
    
    @Override
    public void endVisit(SQLIfStatement.ElseIf elseIf) {
    
    }
    
    @Override
    public void endVisit(SQLIfStatement.Else anElse) {
    
    }
    
    @Override
    public void endVisit(SQLLoopStatement sqlLoopStatement) {
    
    }
    
    @Override
    public void endVisit(SQLParameter sqlParameter) {
    
    }
    
    @Override
    public void endVisit(SQLCreateProcedureStatement sqlCreateProcedureStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCreateFunctionStatement sqlCreateFunctionStatement) {
    
    }
    
    @Override
    public void endVisit(SQLBlockStatement sqlBlockStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDropKey sqlAlterTableDropKey) {
    
    }
    
    @Override
    public void endVisit(SQLDeclareItem sqlDeclareItem) {
    
    }
    
    @Override
    public void endVisit(SQLPartitionValue sqlPartitionValue) {
    
    }
    
    @Override
    public void endVisit(SQLPartition sqlPartition) {
    
    }
    
    @Override
    public void endVisit(SQLPartitionByRange sqlPartitionByRange) {
    
    }
    
    @Override
    public void endVisit(SQLPartitionByHash sqlPartitionByHash) {
    
    }
    
    @Override
    public void endVisit(SQLPartitionByList sqlPartitionByList) {
    
    }
    
    @Override
    public void endVisit(SQLSubPartition sqlSubPartition) {
    
    }
    
    @Override
    public void endVisit(SQLSubPartitionByHash sqlSubPartitionByHash) {
    
    }
    
    @Override
    public void endVisit(SQLSubPartitionByList sqlSubPartitionByList) {
    
    }
    
    @Override
    public void endVisit(SQLAlterDatabaseStatement sqlAlterDatabaseStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableConvertCharSet sqlAlterTableConvertCharSet) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableReOrganizePartition sqlAlterTableReOrganizePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableCoalescePartition sqlAlterTableCoalescePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableTruncatePartition sqlAlterTableTruncatePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableDiscardPartition sqlAlterTableDiscardPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableImportPartition sqlAlterTableImportPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableAnalyzePartition sqlAlterTableAnalyzePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableCheckPartition sqlAlterTableCheckPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableOptimizePartition sqlAlterTableOptimizePartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRebuildPartition sqlAlterTableRebuildPartition) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRepairPartition sqlAlterTableRepairPartition) {
    
    }
    
    @Override
    public void endVisit(SQLSequenceExpr sqlSequenceExpr) {
    
    }
    
    @Override
    public void endVisit(SQLMergeStatement sqlMergeStatement) {
    
    }
    
    @Override
    public void endVisit(SQLMergeStatement.MergeUpdateClause mergeUpdateClause) {
    
    }
    
    @Override
    public void endVisit(SQLMergeStatement.MergeInsertClause mergeInsertClause) {
    
    }
    
    @Override
    public void endVisit(SQLErrorLoggingClause sqlErrorLoggingClause) {
    
    }
    
    @Override
    public void endVisit(SQLNullConstraint sqlNullConstraint) {
    
    }
    
    @Override
    public void endVisit(SQLCreateSequenceStatement sqlCreateSequenceStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDateExpr sqlDateExpr) {
    
    }
    
    @Override
    public void endVisit(SQLLimit sqlLimit) {
    
    }
    
    @Override
    public void endVisit(SQLStartTransactionStatement sqlStartTransactionStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDescribeStatement sqlDescribeStatement) {
    
    }
    
    @Override
    public void endVisit(SQLWhileStatement sqlWhileStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDeclareStatement sqlDeclareStatement) {
    
    }
    
    @Override
    public void endVisit(SQLReturnStatement sqlReturnStatement) {
    
    }
    
    @Override
    public void endVisit(SQLArgument sqlArgument) {
    
    }
    
    @Override
    public void endVisit(SQLCommitStatement sqlCommitStatement) {
    
    }
    
    @Override
    public void endVisit(SQLFlashbackExpr sqlFlashbackExpr) {
    
    }
    
    @Override
    public void endVisit(SQLCreateMaterializedViewStatement sqlCreateMaterializedViewStatement) {
    
    }
    
    @Override
    public void endVisit(SQLBinaryOpExprGroup sqlBinaryOpExprGroup) {
    
    }
    
    @Override
    public void endVisit(SQLScriptCommitStatement sqlScriptCommitStatement) {
    
    }
    
    @Override
    public void endVisit(SQLReplaceStatement sqlReplaceStatement) {
    
    }
    
    @Override
    public void endVisit(SQLCreateUserStatement sqlCreateUserStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterFunctionStatement sqlAlterFunctionStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTypeStatement sqlAlterTypeStatement) {
    
    }
    
    @Override
    public void endVisit(SQLIntervalExpr sqlIntervalExpr) {
    
    }
    
    @Override
    public void endVisit(SQLLateralViewTableSource sqlLateralViewTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLShowErrorsStatement sqlShowErrorsStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterCharacter sqlAlterCharacter) {
    
    }
    
    @Override
    public void endVisit(SQLExprStatement sqlExprStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterProcedureStatement sqlAlterProcedureStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterViewStatement sqlAlterViewStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropEventStatement sqlDropEventStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropLogFileGroupStatement sqlDropLogFileGroupStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropServerStatement sqlDropServerStatement) {
    
    }
    
    @Override
    public void endVisit(SQLDropSynonymStatement sqlDropSynonymStatement) {
    
    }
    
    @Override
    public void endVisit(SQLRecordDataType sqlRecordDataType) {
    
    }
    
    @Override
    public void endVisit(SQLDropTypeStatement sqlDropTypeStatement) {
    
    }
    
    @Override
    public void endVisit(SQLExternalRecordFormat sqlExternalRecordFormat) {
    
    }
    
    @Override
    public void endVisit(SQLArrayDataType sqlArrayDataType) {
    
    }
    
    @Override
    public void endVisit(SQLMapDataType sqlMapDataType) {
    
    }
    
    @Override
    public void endVisit(SQLStructDataType sqlStructDataType) {
    
    }
    
    @Override
    public void endVisit(SQLStructDataType.Field field) {
    
    }
    
    @Override
    public void endVisit(SQLDropMaterializedViewStatement sqlDropMaterializedViewStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableRenameIndex sqlAlterTableRenameIndex) {
    
    }
    
    @Override
    public void endVisit(SQLAlterSequenceStatement sqlAlterSequenceStatement) {
    
    }
    
    @Override
    public void endVisit(SQLAlterTableExchangePartition sqlAlterTableExchangePartition) {
    
    }
    
    @Override
    public void endVisit(SQLValuesExpr sqlValuesExpr) {
    
    }
    
    @Override
    public void endVisit(SQLValuesTableSource sqlValuesTableSource) {
    
    }
    
    @Override
    public void endVisit(SQLContainsExpr sqlContainsExpr) {
    
    }
    
    @Override
    public void endVisit(SQLRealExpr sqlRealExpr) {
    
    }
    
    @Override
    public void endVisit(SQLWindow sqlWindow) {
    
    }
    
    @Override
    public void endVisit(SQLDumpStatement sqlDumpStatement) {
    
    }
}
