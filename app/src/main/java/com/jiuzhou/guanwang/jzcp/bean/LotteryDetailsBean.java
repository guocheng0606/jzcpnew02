package com.jiuzhou.guanwang.jzcp.bean;

/**
 */

public class LotteryDetailsBean {

  private String winName;
  private String winCount;
  private String winMoney;

  public LotteryDetailsBean() {

  }

  public LotteryDetailsBean(String winName, String winCount, String winMoney) {
    this.winName = winName;
    this.winCount = winCount;
    this.winMoney = winMoney;
  }

  public String getWinName() {
    return winName;
  }

  public void setWinName(String winName) {
    this.winName = winName;
  }

  public String getWinCount() {
    return winCount;
  }

  public void setWinCount(String winCount) {
    this.winCount = winCount;
  }

  public String getWinMoney() {
    return winMoney;
  }

  public void setWinMoney(String winMoney) {
    this.winMoney = winMoney;
  }
}
