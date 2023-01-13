package com.yjb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yjb
 * @date 2021/3/4 20:00
 */

/**
 * 求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)，几个数相
 * 加有键盘控制。
 * 程序分析：关键是计算出每一项的值。
 */
public class Test_sunLoop {
    public static void main(String[] args) throws IOException {
        /*int s = 0;
        String output = "";
        BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入a的值");
        String input = sb.readLine();
        for(int i = 1;i<=Integer.parseInt(input);i++) {
            output += input;
            int a = Integer.parseInt(output);
            s += a;
        }
        System.out.println(s);
    */
        int s = 0;
        int n;
        int t = 0;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input = stdin.readLine();
        n = Integer.parseInt(input);
        for (int i = 1; i <= n ; i++) {
            t = t * 10 + n;
            s = s + t;
//            System.out.println(t);
        }
        System.out.println(s);
    }
}
