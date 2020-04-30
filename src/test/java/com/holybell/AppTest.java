package com.holybell;

import com.holybell.luckydraw.exception.LuckyDrawException;
import com.holybell.luckydraw.strategy.impl.RandomLotteryStrategy;
import com.holybell.luckydraw.strategy.impl.WeightLotteryStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AppTest {

    /**
     * 测试随机策略是否会出现一个员工多次中奖
     */
    @Test
    public void testAtMostOne1() {
        // 随机策略
        LuckyDraw luckyDraw = new LuckyDraw(new RandomLotteryStrategy());
        List<List<Integer>> resultList = luckyDraw.lottery(10, new int[]{1, 2, 3, 4});

        Set<Integer> wonSet = new HashSet<>();  // 缓存中奖员工

        for (List<Integer> list : resultList) {
            for (Integer employeeNo : list) {
                Assert.assertTrue("出现同一名员工多次中奖情况!", !wonSet.contains(employeeNo));
                wonSet.add(employeeNo);
            }
        }
    }

    /**
     * 测试权重策略是否会出现一个员工多次中奖
     */
    @Test
    public void testAtMostOne2() {
        // 权重策略
        LuckyDraw luckyDraw = new LuckyDraw(new WeightLotteryStrategy(new double[]{0.1, 0.2, 0.3, 0.4}));
        List<List<Integer>> resultList = luckyDraw.lottery(10, new int[]{1, 2, 3, 4});

        Set<Integer> wonSet = new HashSet<>();  // 缓存中奖员工

        for (List<Integer> list : resultList) {
            for (Integer employeeNo : list) {
                Assert.assertTrue("出现同一名员工多次中奖情况!", !wonSet.contains(employeeNo));
                wonSet.add(employeeNo);
            }
        }
    }

    /**
     * 测试奖品数量大于员工数量
     */
    @Test
    public void testPrizeNumOverEmployeeNum() {
        LuckyDrawException exception = null;
        try {
            LuckyDraw luckyDraw = new LuckyDraw(new RandomLotteryStrategy());
            luckyDraw.lottery(10, new int[]{1, 2, 3, 5});
        } catch (LuckyDrawException ex) {
            exception = ex;
        }
        Assert.assertNull("奖项个数超过员工人数!", exception);
    }


}
