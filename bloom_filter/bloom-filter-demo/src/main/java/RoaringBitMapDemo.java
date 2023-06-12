import org.roaringbitmap.RoaringBitmap;

public class RoaringBitMapDemo {

    public static void main(String[] args) {
        RoaringBitmap roaringBitmap1 = RoaringBitmap.bitmapOf(1, 2, 3, 1000);
        // 第三个数值，索引从0开始
        int thirdValue = roaringBitmap1.select(3);
        // 2这个值的排序，排序索引从1开始，如果不在是0
        int indexOfTwo = roaringBitmap1.rank(2);
        boolean containsFlag1 = roaringBitmap1.contains(1000);
        boolean containsFlag2 = roaringBitmap1.contains(7);

        System.out.println("roaringBitmap1 is: " + roaringBitmap1);
        System.out.println("thirdValue is: " + thirdValue);
        System.out.println("indexOfTwo is: " + indexOfTwo);
        System.out.println("containsFlag1 is: " + containsFlag1);
        System.out.println("containsFlag2 is: " + containsFlag2);
        System.out.println();

        // 做并集
        RoaringBitmap roaringBitmap2 = new RoaringBitmap();
        roaringBitmap2.add(4000L, 4005L);
        RoaringBitmap roaringBitmap1Or2 = RoaringBitmap.or(roaringBitmap1, roaringBitmap2);
        roaringBitmap1.or(roaringBitmap2);

        System.out.println("roaringBitmap1 is: " + roaringBitmap1);
        System.out.println("roaringBitmap2 is: " + roaringBitmap2);
        System.out.println("roaringBitmap1Or2 is: " + roaringBitmap1Or2);

        boolean equals = roaringBitmap1Or2.equals(roaringBitmap1);
        System.out.println("roaringBitmap1 is equals roaringBitmap1Or2: " + equals);

        // 获取位图中元素个数
        long cardinality = roaringBitmap1.getLongCardinality();
        System.out.println("cardinality is: " + cardinality);
    }
}

