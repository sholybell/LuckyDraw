package com.holybell.luckydraw.exception;

/**
 * 幸运抽奖异常类
 */
public class LuckyDrawException extends RuntimeException {

    public LuckyDrawException() {

    }

    public LuckyDrawException(String message) {
        super(message);
    }
}
