package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/23 16:11
 */

/**
 * 题目：有一分数序列：2/1，3/2，5/3，8/5，13/8，21/13...求出这个数列的前20项之和。
 * 1.程序分析：请抓住分子与分母的变化规律。
 */
public class Test_20 {
    public static void main(String[] args) {
        float fz = 1l;
        float fm = 1l;
        float temp;
        float sum = 0l;
        for (int i = 0; i < 20; i++) {
            temp = fm;
            fm = fz;
            fz = fz + temp;
            sum += fz / fm;
        }
        System.out.println(sum);
    }
}
