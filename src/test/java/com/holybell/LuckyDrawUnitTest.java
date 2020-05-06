package com.holybell;

import com.holybell.luckydraw.exception.LuckyDrawException;
import com.holybell.luckydraw.strategy.LotteryStrategy;
import com.holybell.luckydraw.strategy.impl.RandomLotteryStrategy;
import com.holybell.luckydraw.strategy.impl.WeightLotteryStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LuckyDrawUnitTest {

    // --------------------------------------------  测试是否出现同一员工中奖超过1次   start

    /**
     * 测试随机策略是否会出现一个员工多次中奖
     */
    @Test
    public void testAtMostWonOnce1() {
        // 随机策略
        testAtMostWonOnce(new RandomLotteryStrategy());
    }

    /**
     * 测试权重策略是否会出现一个员工多次中奖
     */
    @Test
    public void testAtMostWonOnce2() {
        // 权重策略
        testAtMostWonOnce(new WeightLotteryStrategy(new double[]{0.1, 0.2, 0.3, 0.4}));
    }

    /**
     * 测试同一个员工是否中奖超过1次
     *
     * @param lotteryStrategy 抽奖策略
     */
    private void testAtMostWonOnce(LotteryStrategy lotteryStrategy) {
        LuckyDraw luckyDraw = new LuckyDraw(lotteryStrategy);
        List<List<Integer>> resultList = luckyDraw.lottery(10, new int[]{1, 2, 3, 4});

        Set<Integer> wonSet = new HashSet<>();  // 缓存中奖员工

        for (List<Integer> list : resultList) {
            for (Integer employeeNo : list) {
                assertTrue(!wonSet.contains(employeeNo), "出现同一个员工中将多次的情况!");
                wonSet.add(employeeNo);
            }
        }
    }

    // --------------------------------------------  测试是否出现同一员工中奖超过1次   end

    // --------------------------------------------  测试是否校验奖品数量大于员工数量的情况   start

    /**
     * 测试是否校验奖品数量大于员工数量的情况
     */
    @Test
    public void testPrizeNumOverEmployeeNum() {
        assertThrows(LuckyDrawException.class, () -> {
            LuckyDraw luckyDraw = new LuckyDraw(new RandomLotteryStrategy());
            luckyDraw.lottery(10, new int[]{1, 2, 3, 5});
//            luckyDraw.lottery(10, new int[]{1, 2, 3, 4});
        }, "奖品数量大于员工数量!");
    }

    // --------------------------------------------  测试是否校验奖品数量大于员工数量的情况   end

    // --------------------------------------------  测试每个奖品对同一个员工的中奖概率   start

    /**
     * 测试随机策略公平性
     */
    @Test
    public void testFairness1() {
        testFairness(new RandomLotteryStrategy());
    }

    /**
     * 测试权重策略公平性
     */
    @Test
    public void testFairness2() {
        testFairness(new WeightLotteryStrategy(new double[]{0.1, 0.2, 0.3, 0.4}));
    }

    /**
     * 测试每个奖项每个员工的中奖概率
     *
     * @param lotteryStrategy 抽奖策略
     */
    private void testFairness(LotteryStrategy lotteryStrategy) {

        Map<Integer, Map<Integer, Integer>> resultMap = new HashMap<>(); // key 员工编号 value （奖项 - 获奖次数）

        int employeeNum = 10;                               // 员工数量
        int[] prizeConfiguration = new int[]{1, 2, 3, 4};   // 奖项配置
        LuckyDraw luckyDraw = new LuckyDraw(lotteryStrategy);

        int testCount = 100000;                             // 测试次数

        for (int i = 0; i < testCount; i++) {   // 进行1W次测试
            List<List<Integer>> resultList = luckyDraw.lottery(employeeNum, prizeConfiguration);
            // 统计本次抽奖结果
            for (int j = 0; j < resultList.size(); j++) {
                for (int z = 0; z < resultList.get(j).size(); z++) {
                    int employeeNo = resultList.get(j).get(z);
                    Map<Integer, Integer> prizeMap = resultMap.getOrDefault(employeeNo, new HashMap<>());     // 获取当前员工已经中奖的记录
                    prizeMap.put(j, prizeMap.getOrDefault(j, 0) + 1);   // 当前员工的获奖数量+1
                    resultMap.put(employeeNo, prizeMap);
                }
            }
        }

        // 本次测试所有奖品的个数
        int totalPrizeNum = 0;
        for (int i = 0; i < prizeConfiguration.length; i++) {
            totalPrizeNum += prizeConfiguration[i] * testCount;
        }

        // 根据抽奖配置，每个人平均中将概率应为:一等奖中奖概率0.01，二等奖0.02，三等奖0.03，四等奖0.04

        for (int i = 0; i < employeeNum; i++) {
            BigDecimal _1stRatio = BigDecimal.valueOf(resultMap.get(i).get(0)).divide(BigDecimal.valueOf(totalPrizeNum)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal _2ndRatio = BigDecimal.valueOf(resultMap.get(i).get(1)).divide(BigDecimal.valueOf(totalPrizeNum)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal _3rdRatio = BigDecimal.valueOf(resultMap.get(i).get(2)).divide(BigDecimal.valueOf(totalPrizeNum)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal _4thRatio = BigDecimal.valueOf(resultMap.get(i).get(3)).divide(BigDecimal.valueOf(totalPrizeNum)).setScale(2, RoundingMode.HALF_UP);
            assertTrue(_1stRatio.compareTo(BigDecimal.valueOf(0.01)) == 0, "一等奖中奖概率异常!");
            assertTrue(_2ndRatio.compareTo(BigDecimal.valueOf(0.02)) == 0, "二等奖中奖概率异常!");
            assertTrue(_3rdRatio.compareTo(BigDecimal.valueOf(0.03)) == 0, "三等奖中奖概率异常!");
            assertTrue(_4thRatio.compareTo(BigDecimal.valueOf(0.04)) == 0, "四等奖中奖概率异常!");
        }
    }

    // --------------------------------------------  测试每个奖品对同一个员工的中奖概率   start

}
