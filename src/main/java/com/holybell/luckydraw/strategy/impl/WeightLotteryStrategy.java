package com.holybell.luckydraw.strategy.impl;

import com.holybell.luckydraw.exception.LuckyDrawException;
import com.holybell.luckydraw.strategy.LotteryStrategy;

import java.util.*;

/**
 * 本策略先确定抽奖的员工，然后根据权重获取奖项
 */
public class WeightLotteryStrategy implements LotteryStrategy {

    private double[] prizeWeight;

    public WeightLotteryStrategy(double[] prizeWeight) {
        this.prizeWeight = prizeWeight;
    }


    @Override
    public List<List<Integer>> doLottery(int employeeNumber, int[] prizeConfiguration) {

        if (prizeConfiguration.length != prizeWeight.length) {
            throw new LuckyDrawException("奖项权重个数和奖项个数不一致!");
        }

        List<List<Integer>> resultList = new ArrayList<>();             // 最终抽奖结果集合

        Set<Integer> employeeWhoHadWonLotterySet = new HashSet<>();     // 缓存已经中奖的员工
        Map<Integer, Integer> prizeThatHadBeenWonMap = new HashMap<>();  // 记录每个奖项被抽中的个数

        int prizeCount = 0; // 奖项总个数

        // 事先生成抽奖结果List集合
        for (int prizeNum : prizeConfiguration) {
            resultList.add(new ArrayList<>(prizeNum));
            prizeCount += prizeNum;
        }

        while (prizeCount != 0) {

            int employeeNo = new Random().nextInt(employeeNumber);  // 员工编号
            if (!employeeWhoHadWonLotterySet.contains(employeeNo)) {  // 当前员工并未中奖过
                int prizeNo = getPrize();
                if (prizeThatHadBeenWonMap.getOrDefault(prizeNo, 0) < prizeConfiguration[prizeNo]) {    // 当前奖项仍有剩余
                    employeeWhoHadWonLotterySet.add(employeeNo);
                    prizeThatHadBeenWonMap.put(prizeNo, prizeThatHadBeenWonMap.getOrDefault(prizeNo, 0) + 1);
                    resultList.get(prizeNo).add(employeeNo);        // 记录抽奖结果
                    prizeCount--;
                }
            }
        }
        return resultList;
    }

    private int getPrize() {
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
