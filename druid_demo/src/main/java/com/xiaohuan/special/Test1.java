package com.xiaohuan.special;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.util.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/17 14:57
 */
public class Test1 {
	public static void main(String[] args) {
		String registerId = "kol_radar_home_list_prod_1114";


	}


	public void parseSQL() throws Exception {
		String sql = "";
		String dbType = "";
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
		SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) stmtList.get(0);
		if (CollectionUtils.isEmpty(stmtList) || stmtList.size() > 1) {
			throw new Exception("sql statment <> 1");
		}

		SQLSelectQuery sqlSelectQuery = sqlSelectStatement.getSelect().getQuery();
		SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;

		//select
		List<String> selectExpresses = sqlSelectQueryBlock.getSelectList().stream()
				.map(sqlSelectItem -> {
					String alias = sqlSelectItem.getAlias();
					if (StringUtils.isEmpty(alias)) {
						return sqlSelectItem.getExpr().toString();
					} else {
						return alias.replaceAll("`", "");
					}
				}).collect(Collectors.toList());

		//where
		SQLExpr where = sqlSelectQueryBlock.getWhere();
		List<WhereInBinaryTreeNode> whereInBinaryTreeNodes = null;
        if (where instanceof SQLBinaryOpExpr) {
			SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr) where;

			List<WhereInBinaryTreeNode> l = Lists.newArrayList();
			this.recusiveWhere(sqlBinaryOpExpr, l);
			whereInBinaryTreeNodes = l.stream().filter(whereInBinaryTreeNode -> AVAILABLE_OPERATORS.contains(whereInBinaryTreeNode.getOperator())).collect(Collectors.toList());
//            whereInBinaryTreeNodes = l;
		}

		//having
		SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
		List<WhereInBinaryTreeNode> havingInBinaryTreeNodes = null;
        if (sqlSelectGroupByClause != null && sqlSelectGroupByClause.getHaving() != null) {
			SQLExpr having = sqlSelectGroupByClause.getHaving();
			if (having instanceof SQLBinaryOpExpr) {
				SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr) having;

				havingInBinaryTreeNodes = Lists.newArrayList();
				this.recusiveWhere(sqlBinaryOpExpr, havingInBinaryTreeNodes);
			}
		}

        System.out.println(sql);
		System.out.println(dbType);
		System.out.println(sqlSelectStatement);
		System.out.println(selectExpresses);
		System.out.println(whereInBinaryTreeNodes);
		System.out.println(havingInBinaryTreeNodes);
	}

	/**
	 * recusive loop the where-binary-tree, flatten it into a map as a parameter
	 * @param sqlBinaryOpExpr original Druid express binary-tree
	 * @param whereInBinaryTreeNodes node Object
	 */
	private void recusiveWhere(SQLBinaryOpExpr sqlBinaryOpExpr, List<WhereInBinaryTreeNode> whereInBinaryTreeNodes) {
		SQLExpr           left            = sqlBinaryOpExpr.getLeft();
		SQLBinaryOperator operator        = sqlBinaryOpExpr.getOperator();
		SQLExpr           right           = sqlBinaryOpExpr.getRight();

		if (left instanceof SQLBinaryOpExpr) {
			this.recusiveWhere((SQLBinaryOpExpr) left, whereInBinaryTreeNodes);
		}

		if (right instanceof SQLBinaryOpExpr) {
			this.recusiveWhere((SQLBinaryOpExpr) right, whereInBinaryTreeNodes);
		} else if (right instanceof SQLInListExpr) {
			SQLInListExpr sqlInListExpr = (SQLInListExpr) right;

			WhereInBinaryTreeNode whereInBinaryTreeNode = WhereInBinaryTreeNode.builder()
					.express(sqlInListExpr.getExpr() + " in ")
					.operator("in")
					.valueType("")
					.sqlExpr(right)
					.value(sqlInListExpr.getTargetList().toString().replaceAll("\\[", "").replaceAll("]", ""))
					.build();

			whereInBinaryTreeNodes.add(whereInBinaryTreeNode);

		} else {
			WhereInBinaryTreeNode whereInBinaryTreeNode = WhereInBinaryTreeNode.builder()
					.express(left.toString() + operator.name_lcase)
					.operator(operator.name_lcase)
					.valueType(right.computeDataType() == null ? "" : right.computeDataType().getName())
					.sqlExpr(sqlBinaryOpExpr)
					.value(right.toString())
					.build();

			whereInBinaryTreeNodes.add(whereInBinaryTreeNode);
		}
	}
}
