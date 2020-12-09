package me.ehlxr.test;

import java.math.BigDecimal;

public interface IChargeCounter {
    /**
     * 按点击计费
     *
     * @param campaignid
     * @param groupid
     * @param cost
     */
    void chargeForThisResult(String campaignid, String groupid, BigDecimal cost);

    /**
     * 投放次数控制
     *
     * @param groupid
     * @param count
     * @param type
     */
    void counterControlForThisSumResult(String groupid, int count, String type);
}