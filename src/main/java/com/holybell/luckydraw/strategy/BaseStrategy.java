package com.holybell.luckydraw.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽取共用的代码模块到父类
 */
public class BaseStrategy {

    /**
     * 随机打散员工编号
     *
     * @param employeeNumber 员工数量
     */
    public List<Integer> shuffle(int employeeNumber) {
        List<Integer> employeeNumList = new ArrayList<>();
        for (int i = 0; i < employeeNumber; i++) {
            employeeNumList.add(i);
        }
        Collections.shuffle(employeeNumList);
        return employeeNumList;
    }

}
