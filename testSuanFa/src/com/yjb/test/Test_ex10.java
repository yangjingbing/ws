package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/9 11:55
 */

/**
 * 题目：一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在 第10次落地时，共经过多少米？第10次反弹
 * 多高？
 */
public class Test_ex10 {
    public static void main(String[] args) {
        double s = 0;
        double t = 100;
        for(int i = 1;i<=10;i++) {
            s += t;
            t = t/2;
        }
        System.out.println(s);
        System.out.println(t);
    }
}
