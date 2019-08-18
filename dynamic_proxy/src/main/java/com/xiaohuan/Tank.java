package com.xiaohuan;

import com.xiaohuan.Moveable;

import java.util.Random;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-28 10:50
 */
public class Tank implements Moveable {
  @Override
  public void move() {
    System.out.println("Tank moving...");
    try {
      Thread.sleep(new Random().nextInt(10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
