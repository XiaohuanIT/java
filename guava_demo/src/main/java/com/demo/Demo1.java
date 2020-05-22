package com.demo;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


/**
 * @Author: xiaohuan
 * @Date: 2020/5/6 21:18
 */
public class Demo1 {
	public static void main(String[] args){
		List<String> names = Lists.newArrayList("John", "Adam", "Jane");
		// [John, Adam, Jane]
		System.out.println(names.toString());

		// Reverse a List
		List<String> reversed = Lists.reverse(names);
		// [Jane, Adam, John]
		System.out.println(reversed.toString());

		// Generate Character List from a String: break a String apart into a list of Characters
		List<Character> chars = Lists.charactersOf("John");
		// 4
		System.out.println(chars.size());
		// [J, o, h, n]
		System.out.println(chars.toString());

		whenPartitionList_thenPartitioned();
		whenRemoveDuplicatesFromList_thenRemoved();
		whenRemoveNullFromList_thenRemoved();
		whenCreateImmutableList_thenCreated();
		dikaer();
		dikaer1();
		setToList();
	}

	/**
	 * Partition a List
	 */
	public static void whenPartitionList_thenPartitioned(){
		List<String> names = Lists.newArrayList("John","Jane","Adam","Tom","Viki","Tyler");

		List<List<String>> result = Lists.partition(names, 2);

		// 3
		System.out.println(result.size());
		// [John, Jane]
		System.out.println(result.get(0).toString());
		// [Adam, Tom]
		System.out.println(result.get(1).toString());
		// [Viki, Tyler]
		System.out.println(result.get(2).toString());
	}

	/**
	 * Remove Duplicates From List
	 */
	public static void whenRemoveDuplicatesFromList_thenRemoved() {
		List<Character> chars = Lists.newArrayList('h','e','l','l','o');
		assertEquals(5, chars.size());

		List<Character> result = ImmutableSet.copyOf(chars).asList();
		// [h, e, l, o]
		System.out.println(result.toString());
	}

	/**
	 * Remove Null Values from List
	 */
	public static void whenRemoveNullFromList_thenRemoved() {
		List<String> names = Lists.newArrayList("John", null, "Adam", null, "Jane");
		Iterables.removeIf(names, Predicates.isNull());

		// 3
		System.out.println(names.size());
		// [John, Adam, Jane]
		System.out.println(names.toString());
	}

	/**
	 * Convert a List to an ImmutableList
	 */
	public static void whenCreateImmutableList_thenCreated() {
		List<String> names = Lists.newArrayList("John", "Adam", "Jane");

		names.add("Tom");
		assertEquals(4, names.size());

		ImmutableList<String> immutable = ImmutableList.copyOf(names);
		// [John, Adam, Jane, Tom]
		System.out.println(immutable.toString());
	}

	/**
	 * 计算多个List的笛卡尔乘积
	 * 运行结果
	 * [1, a]
	 * [1, b]
	 * [1, c]
	 * [2, a]
	 * [2, b]
	 * [2, c]
	 */
	public static void dikaer(){
		List<List<String>> result = Lists.cartesianProduct(
				Lists.newArrayList("1", "2"),
				Lists.newArrayList("a", "b", "c")
		);
		for (List<String> item : result) {
			System.out.println(item);
		}
	}

	/**
	 * 计算多个List的笛卡尔乘积
	 * [1, a, mmm]
	 * [1, a, nnn]
	 * [1, b, mmm]
	 * [1, b, nnn]
	 * [1, c, mmm]
	 * [1, c, nnn]
	 * [2, a, mmm]
	 * [2, a, nnn]
	 * [2, b, mmm]
	 * [2, b, nnn]
	 * [2, c, mmm]
	 * [2, c, nnn]
	 */
	public static void dikaer1(){
		List<List<String>> result = Lists.cartesianProduct(
				Lists.newArrayList("1", "2"),
				Lists.newArrayList("a", "b", "c"),
				Lists.newArrayList("mmm", "nnn")
		);
		for (List<String> item : result) {
			System.out.println(item);
		}
	}

	/**
	 * 将其他 Collection 类型的集合转换成 ArrayList
	 */
	public static void setToList(){
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		List<String> list = Lists.newArrayList(set);
		// [a, b, c]
		System.out.println(list);
	}
}
