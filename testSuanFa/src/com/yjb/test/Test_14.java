package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/22 10:41
 */

import java.util.Scanner;

/**
 * 题目：输入某年某月某日，判断这一天是这一年的第几天？
 * 1.程序分析：以3月5日为例，应该先把前两个月的加起来，然后再加上5天即本年的第几天，特殊情况，闰年且输入月份大于3时需考虑多加一天。
 */
public class Test_14 {
    public static void main(String[] args) {
        int day=0;
        int month=0;
        int year=0;
        int sum=0;
        int leap;
        System.out.print("请输入年,月,日\n");
        Scanner input = new Scanner(System.in);
        year=input.nextInt();
        month=input.nextInt();
        day=input.nextInt();
        switch(month) /*先计算某月以前月份的总天数*/
        {
            case 1:
                sum=0;break;
            case 2:
                sum=31;break;
            case 3:
                sum=59;break;
            case 4:
                sum=90;break;
            case 5:
                sum=120;break;
            case 6:
                sum=151;break;
            case 7:
                sum=181;break;
            case 8:
                sum=212;break;
            case 9:
                sum=243;break;
            case 10:
                sum=273;break;
            case 11:
                sum=304;break;
            case 12:
                sum=334;break;
            default:
                System.out.println("data error");break;
        }
        sum=sum+day; /*再加上某天的天数*/
        if(year%400==0||(year%4==0&&year%100!=0))/*判断是不是闰年*/
            leap=1;
        else
            leap=0;
        if(leap==1 && month>2)/*如果是闰年且月份大于2,总天数应该加一天*/
            sum++;
        System.out.println("It is the the day:"+sum);
    }
}
