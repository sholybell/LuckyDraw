package com.holybell.luckydraw.strategy;

import java.util.List;

public interface LotteryStrategy {

    /**
     * 执行抽奖逻辑接口
     *
     * @param employeeNumber     员工数量，下标从0开始
     * @param prizeConfiguration 奖项配置，如[1,2,3]表示一等奖1人，二等奖2人，三等奖3人
     * @return 获奖员工列表
     */
    List<List<Integer>> doLottery(int employeeNumber, int[] prizeConfiguration);

}
