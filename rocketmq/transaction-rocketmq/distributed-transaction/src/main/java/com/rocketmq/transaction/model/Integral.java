package com.rocketmq.transaction.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 订单实体
 *
 * @author veromca
 */
@Table(name = "rocketmq_integral")
public class Integral implements Serializable {
    private static final long serialVersionUID = -5293042728053902685L;

    @Id
    private Integer integralId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_integral")
    private Integer totalIntegral;

    public Integer getIntegralId() {
        return integralId;
    }

    public void setIntegralId(Integer integralId) {
        this.integralId = integralId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }
}
