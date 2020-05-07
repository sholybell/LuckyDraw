package com.holybell.luckydraw.strategy.impl;

import com.holybell.luckydraw.exception.LuckyDrawException;
import com.holybell.luckydraw.strategy.BaseStrategy;
import com.holybell.luckydraw.strategy.LotteryStrategy;

import java.util.*;

/**
 * 权重抽奖策略
 * <p>
 * 抽奖逻辑：先随机抽取获奖员工，然后根据权重抽取奖项，如果奖项已经没有了，则将该奖品的权重设置为0，避免影响下一次抽奖结果
 */
public class WeightLotteryStrategy extends BaseStrategy implements LotteryStrategy {

    private double[] prizeWeight;

    public WeightLotteryStrategy(double[] prizeWeight) {
        this.prizeWeight = prizeWeight;
    }

    @Override
    public List<List<Integer>> doLottery(int employeeNumber, int[] prizeConfiguration) {

        if (prizeConfiguration.length != prizeWeight.length) {
            throw new LuckyDrawException("奖项权重个数和奖项个数不一致!");
        }

        double[] _prizeWeight = Arrays.copyOf(prizeWeight, prizeWeight.length);  // 使用一个临时变量保存奖项权重，避免反复测试的时候，权重重置为0导致的影响

        List<List<Integer>> resultList = new ArrayList<>();             // 最终抽奖结果集合
        Map<Integer, Integer> prizeThatHadBeenWonMap = new HashMap<>();  // 记录每个奖项被抽中的个数

        int prizeCount = 0; // 奖项总个数

        // 事先生成抽奖结果List集合
        for (int prizeNum : prizeConfiguration) {
            resultList.add(new ArrayList<>(prizeNum));
            prizeCount += prizeNum;                                         // 累加奖品总数
        }

        List<Integer> employeeNolist = shuffle(employeeNumber);             // 随机打散员工编号

        while (prizeCount != 0) {
            int employeeNo = employeeNolist.remove(0);                // 获取列表中第一个员工编号
            int prizeNo = getPrize(_prizeWeight);                           // 根据权重抽取奖品
            if (prizeThatHadBeenWonMap.getOrDefault(prizeNo, 0) < prizeConfiguration[prizeNo]) {    // 当前奖项仍有剩余
                prizeThatHadBeenWonMap.put(prizeNo, prizeThatHadBeenWonMap.getOrDefault(prizeNo, 0) + 1);
                resultList.get(prizeNo).add(employeeNo);                    // 记录抽奖结果
                prizeCount--;

                if (prizeThatHadBeenWonMap.getOrDefault(prizeNo, 0) == prizeConfiguration[prizeNo]) {
                    _prizeWeight[prizeNo] = 0.0D;                            // 该奖项抽取完毕，将权重置为0
                }
            }
        }
        return resultList;
    }

    /**
     * 根据权重抽取奖品
     *
     * @param prizeWeight 奖项权重
     * @return 几等奖
     */
    private int getPrize(double[] prizeWeight) {
        int random = -1;

        // 计算总权重
        double sumWeight = 0D;
        for (Double weight : prizeWeight) {
            sumWeight += weight;
        }

        // 产生随机数
        double randomNumber;
        randomNumber = Math.random();

        // 根据随机数在所有奖品分布的区域并确定所抽奖品
        double d1 = 0;
        double d2 = 0;

        for (int i = 0; i < prizeWeight.length; i++) {
            d2 += Double.parseDouble(String.valueOf(prizeWeight[i])) / sumWeight;
            if (i == 0) {
                d1 = 0;
            } else {
                d1 += Double.parseDouble(String.valueOf(prizeWeight[i - 1])) / sumWeight;
            }
            if (randomNumber >= d1 && randomNumber <= d2) {
                random = i;
                break;
            }
        }
        return random;
    }
}
