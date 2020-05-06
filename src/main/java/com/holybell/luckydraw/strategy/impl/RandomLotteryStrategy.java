package com.holybell.luckydraw.strategy.impl;

import com.holybell.luckydraw.strategy.LotteryStrategy;

import java.util.*;

/**
 * 随机抽奖策略
 * <p>
 * 抽奖逻辑：先确定奖项，然后随机抽取该奖项的获奖员工
 */
public class RandomLotteryStrategy implements LotteryStrategy {

    /**
     * 执行抽奖逻辑
     *
     * @param employeeNumber     员工数量，下标从0开始
     * @param prizeConfiguration 奖项配置，如[1,2,3]表示一等奖1人，二等奖2人，三等奖3人
     */
    @Override
    public List<List<Integer>> doLottery(int employeeNumber, int[] prizeConfiguration) {

        Set<Integer> employeeWhoHadWonLotterySet = new HashSet<>();                         // 已经中奖的员工
        List<List<Integer>> result = new ArrayList<>(prizeConfiguration.length);            // 抽奖结果

        for (int i = 0; i < prizeConfiguration.length; i++) {
            List<Integer> priceResult = new ArrayList<>(prizeConfiguration[i]);             // 当前奖项获奖人员
            for (int j = 0; j < prizeConfiguration[i]; ) {                                  // 每个奖项的个数
                int employeeNum = new Random().nextInt(employeeNumber);
                if (!employeeWhoHadWonLotterySet.contains(employeeNum)) {                   // 未中奖的员工才能获取本次奖项，并且记录当前员工已经获奖避免二次中奖
                    priceResult.add(employeeNum);
                    employeeWhoHadWonLotterySet.add(employeeNum);
                    j++;
                }
            }
            result.add(priceResult);
        }
        return result;
    }
}
