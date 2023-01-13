package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/22 11:01
 */

import java.util.Scanner;

/**
 * 题目：输入三个整数x,y,z，请把这三个数由小到大输出。
 * 1.程序分析：我们想办法把最小的数放到x上，先将x与y进行比较，如果x>  y则将x与y的值进行交换，然后再用x与z进行比较，如果x>  z则
 * 将x与z的值进行交换，这样能使x最小。
 */
public class Test_15_compare {
    public static void main(String[] args) {
        int i=0,j=0,k=0,x=0;
        System.out.println("请输入三个整数：");
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        j = sc.nextInt();
        k = sc.nextInt();

        if( i > j) {
            x = i;
            i = j;
            j = x;
        }
        if (i > k) {
            x = i;
            i = k;
            k = x;
        }
        if (j > k) {
            x = j;
            j = k;
            k = x;
        }
        System.out.println(i + " " + j + " " + k);
    }
}
