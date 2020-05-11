package com.holybell.interview.util;

import java.util.ArrayList;
import java.util.List;

public class FibonaciUtil {

    /**
     * 根据当前输入的正整数计算小于等于它的斐波那契集合
     *
     * @param inputNum 正整数
     * @return 斐波那契数列
     */
    public static List<Integer> getFibonaciList(int inputNum) {
        // 求得小于等于这个正整数的斐波那契数列
        List<Integer> fibonaciList = new ArrayList<>();
        if (inputNum == 1) {
            fibonaciList.add(1);
        } else if (inputNum == 2) {
            fibonaciList.add(1);
            fibonaciList.add(1);
        } else {
            fibonaciList.add(1);
            fibonaciList.add(1);

            int nextNumber = 0;
            for (int i = 2; nextNumber < inputNum; i++) {
                // 计算下一个斐波那契数字
                nextNumber = fibonaciList.get(i - 1) + fibonaciList.get(i - 2);
                if (nextNumber < inputNum) {
                    fibonaciList.add(nextNumber);
                }
            }
        }
        return fibonaciList;
    }
}
