package org.xiaolanjing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShippingTemplate {
    private Long id;
    private String name;
    private BigDecimal firstWeight;
    private BigDecimal firstPrice;
    private BigDecimal continuePrice;
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getFirstWeight() { return firstWeight; }
    public void setFirstWeight(BigDecimal firstWeight) { this.firstWeight = firstWeight; }
    public BigDecimal getFirstPrice() { return firstPrice; }
    public void setFirstPrice(BigDecimal firstPrice) { this.firstPrice = firstPrice; }
    public BigDecimal getContinuePrice() { return continuePrice; }
    public void setContinuePrice(BigDecimal continuePrice) { this.continuePrice = continuePrice; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
