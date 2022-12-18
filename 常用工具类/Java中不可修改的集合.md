不可修改的集合是其他集合的“只读”包装器。它们不支持任何修改操作，例如添加、删除和清除，但它们的底层集合是可以更改的。额外保证 Collection 对象中没有任何变化的集合被称为 [不可变](https://www.techiedelight.com/zh/immutable-set-java/).

值得注意的是，设置决赛不会使其无法修改。我们仍然可以添加元素或从中删除元素。只有对集合的引用是最终的。


## 1.使用 `Collections.unmodifiableSet()` 方法

Collections [unmodifiableSet(Set s)](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableSet-java.util.Set-) 返回指定集合的不可修改的“只读”视图。任何直接修改返回集或使用迭代器的尝试都将导致 `UnsupportedOperationException`.但是，对原始集合所做的任何更改都将反映在不可修改的集合中。

请注意，Collections 还提供了一个 `unmodifiableSortedSet(SortedSet s)` 返回指定排序集的不可修改视图的方法。

```java
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class Main
{
	// Unmodifiable set in Java
	public static void main(String[] args)
	{
		Set<String> mutableSet = new HashSet<>(Arrays.asList("C", "C++", "Java"));

		// Using Java Collections
		Set<String> unmodifiableSet = Collections
										.unmodifiableSet(mutableSet);

		try {
			// any attempt to modify the set will result in
			// an UnsupportedOperationException
			unmodifiableSet.add("C#");
		}
		catch (UnsupportedOperationException ex) {
			System.out.println("java.lang.UnsupportedOperationException");
		}

		// if we modify the original set, the changes will be reflected
		// back in the unmodifiableSet

		mutableSet.remove("C"); 	// remove `C`
		mutableSet.add("Go");   	// add `Go`

		System.out.println(unmodifiableSet);
	}
}
```


**输出:**  
```
java.lang.UnsupportedOperationException  
[Java, C++, Go]
```




## 2.使用 `Collections.unmodifiableCollection()` 方法

`Collection` 接口提供了另一种方法， [unmodifiableCollection(Set s)](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)，它返回指定集合的不可修改视图。这类似于 `unmodifiableSet()` 除了它返回一个 `Collection` 代替 `Set`.

```java
import java.util.*;

class Main
{
	// Unmodifiable set in Java
	public static void main(String[] args)
	{
		Set<String> mutableSet = new HashSet<>(
										Arrays.asList("C", "C++", "Java"));

		// Using Java Collections
		Collection<String> unmodifiableSet = Collections
										.unmodifiableCollection(mutableSet);

		try {
			// any attempt to modify the collection will result in
			// an UnsupportedOperationException
			unmodifiableSet.add("C#");
		}
		catch (UnsupportedOperationException ex) {
			System.out.println("java.lang.UnsupportedOperationException");
		}

		// if we modify the original set, the changes will be reflected
		// back in the unmodifiableSet

		mutableSet.remove("C"); 	// remove `C`
		mutableSet.add("Go");   	// add `Go`

		System.out.println(unmodifiableSet);
	}
}

```


**输出:**  
```
java.lang.UnsupportedOperationException  
[Java, C++, Go]
```




## 3. 使用 Apache Commons 集合

Apache Commons 集合 `SetUtils` 类提供 [unmodifiableSet(Set s)](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/SetUtils.html#unmodifiableSet(java.util.Set)) 返回由给定集合支持的不可修改集合。如果给定的集合为空，它会抛出一个 `NullPointerException`.该集合将抛出一个 `UnsupportedOperationException` 如果对其进行了任何修改操作。但是，对原始集合所做的任何更改都将反映在返回的集合中。

请注意 Apache Commons Collections `SetUtils` 类还提供 `unmodifiableSortedSet(SortedSet s)` 返回由给定排序集支持的不可修改排序集的方法。




其他的还有guava。



文章来源：
[Java中不可修改的集合](https://www.techiedelight.com/zh/unmodifiable-set-java/)