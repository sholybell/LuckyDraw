package com.holybell.interview.fibonacci;

import com.holybell.interview.util.FibonaciUtil;

import java.util.List;

public class Fibonacci {

    /**
     * 将一个正整数表示若干个不联系的菲波拉契数表达式（不包含第一个斐波那契数，即1）
     *
     * @param inputNum 任意正整数
     * @return 斐波那契表达式
     */
    public String fibonaciExpress(int inputNum) {

        if (inputNum <= 0) {
            throw new RuntimeException("请输入一个正整数!");
        }

        List<Integer> fibonaciList = FibonaciUtil.getFibonaciList(inputNum);

        // 使用贪心算法计算斐波那契表达式
        StringBuilder answer = new StringBuilder();
        int _tempValue = inputNum;  // 临时变量，记录使用贪心算法的中间数字
        for (int i = fibonaciList.size() - 1; i >= 1; i--) {    // 从最大的数字开始逆推
            if (fibonaciList.get(i) <= _tempValue) {
                answer.append("1");
                _tempValue = _tempValue - fibonaciList.get(i);
            } else {
                answer.append("0");
            }
        }
        return answer.toString();
    }

}
