package com.yjb.test;

import java.util.Scanner;

/**
 * @author yjb
 * @date 2021/3/18 14:24
 */

/**
 * 题目：企业发放的奖金根据利润提成。利润(I)低于或等于10万元时，奖金可提10%；利润高于10万元，低于20万元时，低于10万
 * 元的部分按10%提成，高于10万元的部分，可可提成7.5%；20万到40万之间时，高于20万元的部分，可提成5%；40万到60万之间时高
 * 于40万元的部分，可提成3%；60万到100万之间时，高于60万元的部分，可提成1.5%，高于100万元时，超过100万元的部分按1%提成，从
 * 键盘输入当月利润I，求应发放奖金总数？
 */
public class Test_12 {
    public static void main(String[] args) {
        double sum;
        System.out.println("请输入当月的利润");
        Scanner sc  = new Scanner(System.in);
        double lirun = sc.nextDouble();
        if(lirun <= 100000) {
            sum = lirun * 0.1;
        } else if (lirun <= 200000){
            sum = 10000 + lirun * 0.075;
        } else if (lirun <= 400000){
            sum = 17500 + lirun * 0.05;
        } else if (lirun <= 1000000) {
            sum=lirun*0.015;
        } else{
            sum=lirun*0.01;
        }
        System.out.println("应发的奖金为：" + sum);
    }
}
