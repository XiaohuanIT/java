package com.parallel_sorter;

import net.sf.cglib.util.ParallelSorter;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:40
 */
public class TestParallelSorter {
  public static void main(String[] args) {
    Integer[][] value = {
            {4, 3, 9, 0},
            {5, 1, 6, 0}
    };
    ParallelSorter.create(value).mergeSort(0);
    for(Integer[] row : value){
      int former = -1;
      for(int val : row){
        System.out.println(former < val);
        former = val;
      }
    }
  }
}
