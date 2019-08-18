package com.bean_copy;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:01
 */
public class Myth {
  private String username;
  private String password;
  private String remark;

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

  @Override
  public String toString() {
    return "Myth: " + username + ", " + password + ", " + remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getRemark() {
    return remark;
  }
}
