package com.xiaohuan.special;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sql中where condition抽象类
 * 在二叉树中的对应，index是节点在二叉树中的索引
 * 在SqlHandler中有生成索引 以及根据索引查找的方法
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WhereInBinaryTreeNode implements Serializable{

	// "where中允许使用的express与operator", example = "dtm>="
	private String express;

	@JSONField(serialize=false)
	private String operator;

	@JSONField(serialize=false)
	private String value;

	// "对应的value类型", example = "bigint"
	private String valueType;

	@JSONField(serialize=false)
	private SQLExpr sqlExpr;
}
