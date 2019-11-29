package net.cafecoder.warp.web.dto;

import java.io.Serializable;

public class WarpDTO implements Serializable {
  private String referrer;
  private Integer number;

  public String getReferrer() {
    return referrer;
  }

  public void setReferrer(String referrer) {
    this.referrer = referrer;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }
}
