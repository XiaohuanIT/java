package com.bean_copy;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 20:59
 */
public class Other {
  private String username;
  private String password;
  private int age;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Other(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "Other: " + username + ", " + password + ", " + age;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
