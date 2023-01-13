package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/2 14:30
 */

import java.util.Scanner;

/**
 * 题目：输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数。
 * 1.程序分析：利用while语句,条件为输入的字符不为 '\n '.
 */
public class Test_tjzifushu {
    public static void main(String[] args) {
        System.out.println("请输入字符串：");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        String E1="[\u4e00-\u9fa5]";
        String E2="[a-zA-Z]";
        int countH=0;
        int countE=0;
        char[] arrChar=str.toCharArray();
        String[] arrStr = new String[arrChar.length];
        for (int i = 0; i < arrChar.length; i++) {
            arrStr[i] = String.valueOf(arrChar[i]);
        }
        for (String i:arrStr) {
            if(i.matches(E1)){
                countH++;
            }
            if(i.matches(E2)){
                countE++;
            }
        }
        System.out.println("汉字的个数"+countH);
        System.out.println("字母的个数"+countE);
    }
}
