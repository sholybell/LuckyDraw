package com.holybell;

import com.alibaba.fastjson.JSONObject;
import com.holybell.interview.fibonacci.Fibonacci;
import com.holybell.interview.util.FibonaciUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FibonaciTest {


    /**
     * 入参为正整数
     */
    @Test
    public void testInputNumer() {
        Assertions.assertThrows(Exception.class, () -> {
            Fibonacci fibonacci = new Fibonacci();
            fibonacci.fibonaciExpress(-1);
        }, "校验正整数!");
    }

    /* * 测试获取斐波那契数列
     */
    @Test
    public void testGetFibonaciList() {
        List<Integer> fibonaciList = FibonaciUtil.getFibonaciList(11);
        System.out.println(JSONObject.toJSONString(fibonaciList));
    }

    /**
     * 测试获取斐波那契表达式
     */
    @Test
    public void testFibonaci() {
        Fibonacci fibonacci = new Fibonacci();
        String answer = fibonacci.fibonaciExpress(20);
        System.out.println(answer);
        Assertions.assertTrue(answer.equals("101010"));
    }
}
