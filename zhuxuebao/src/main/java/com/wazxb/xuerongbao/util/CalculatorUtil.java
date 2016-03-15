package com.wazxb.xuerongbao.util;

/**
 * Created by zhengxin on 16/3/10.
 */
public class CalculatorUtil {

    public static double getDay(int intPrincipal, float floatRate, int intLoanPeriod) {
        double result = intPrincipal * Math.pow(1 + (floatRate * 12 / 365), intLoanPeriod);
        return result;
    }

    public static double getMonth(int intPrincipal, float floatRate, int intLoanPeriod) {
        double result = intPrincipal * (floatRate * Math.pow(1 + floatRate, intLoanPeriod)) / (Math.pow(1 + floatRate, intLoanPeriod) - 1);
        return result;
    }

    public static double getMonthPrincipal(int intPrincipal, float floatRate, int intLoanPeriod) {
        double result = intPrincipal * floatRate / (Math.pow(1 + floatRate, intLoanPeriod) - 1);
        return result;
    }
}
