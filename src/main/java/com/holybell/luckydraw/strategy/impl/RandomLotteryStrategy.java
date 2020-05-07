package com.holybell.luckydraw.strategy.impl;

import com.holybell.luckydraw.strategy.BaseStrategy;
import com.holybell.luckydraw.strategy.LotteryStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机抽奖策略
 * <p>
 * 抽奖逻辑：先确定奖项，然后随机抽取该奖项的获奖员工
 */
public class RandomLotteryStrategy extends BaseStrategy implements LotteryStrategy {

    /**
     * 执行抽奖逻辑
     *
     * @param employeeNumber     员工数量，下标从0开始
     * @param prizeConfiguration 奖项配置，如[1,2,3]表示一等奖1人，二等奖2人，三等奖3人
     */
    @Override
    public List<List<Integer>> doLottery(int employeeNumber, int[] prizeConfiguration) {

        List<Integer> employeeNumList = shuffle(employeeNumber);                            // 随机打散员工编号
        List<List<Integer>> result = new ArrayList<>(prizeConfiguration.length);            // 抽奖结果

        for (int i = 0; i < prizeConfiguration.length; i++) {
            List<Integer> prizeResult = new ArrayList<>(prizeConfiguration[i]);             // 当前奖项获奖人员
            for (int j = 0; j < prizeConfiguration[i]; j++) {                               // 每个奖项的个数
                int employeeNum = employeeNumList.remove(0);                          // 获取第一个员工编号
                prizeResult.add(employeeNum);
            }
            result.add(prizeResult);
        }
        return result;
    }
}
