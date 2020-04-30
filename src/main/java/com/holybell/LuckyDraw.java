package com.holybell;

import com.holybell.luckydraw.exception.LuckyDrawException;
import com.holybell.luckydraw.strategy.LotteryStrategy;

import java.util.List;

public class LuckyDraw {

    private LotteryStrategy lotteryStrategy;

    public LuckyDraw() {

    }

    public LuckyDraw(LotteryStrategy lotteryStrategy) {
        this.lotteryStrategy = lotteryStrategy;
    }

    public LotteryStrategy getLotteryStrategy() {
        return lotteryStrategy;
    }

    public void setLotteryStrategy(LotteryStrategy lotteryStrategy) {
        this.lotteryStrategy = lotteryStrategy;
    }

    /**
     * 执行抽奖
     * 要求：
     * 1.员工数量 >= 奖项数量
     * 2.每个员工至多获得一个奖项
     * 3.保证公平性，每个员工都有相同的概率获得相同的奖项
     *
     * @param employeeNumber     员工数量，下标从0开始
     * @param prizeConfiguration 奖项配置，如[1,2,3]表示一等奖1人，二等奖2人，三等奖3人
     * @return 获奖员工列表
     */
    public List<List<Integer>> lottery(int employeeNumber, int[] prizeConfiguration) {

        // 校验员工数量合法性
        if (employeeNumber <= 0) {
            throw new LuckyDrawException("员工数量应为大于0的整数!");
        }

        // 校验奖项配置合法性
        if (prizeConfiguration.length == 0) {
            throw new LuckyDrawException("奖项个数应为大于0的整数!");
        }

        // 校验奖项个数和员工人数配置合法性
        int priceNum = 0;
        for (int num : prizeConfiguration) {
            priceNum += num;
        }
        if (priceNum > employeeNumber) {
            throw new LuckyDrawException("奖项个数应该小于等于员工人数!");
        }

        // 校验是否有抽奖策略
        if (lotteryStrategy == null) {
            throw new LuckyDrawException("请设置抽奖策略!");
        }

        return lotteryStrategy.doLottery(employeeNumber, prizeConfiguration);
    }
}
