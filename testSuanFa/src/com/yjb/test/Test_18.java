package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/22 16:45
 */

import java.util.ArrayList;

/**
 * 题目：两个乒乓球队进行比赛，各出三人。甲队为a,b,c三人，乙队为x,y,z三人。已抽签决定比赛名单。有人向队员打听比赛的名
 * 单。a说他不和x比，c说他不和x,z比，请编程序找出三队赛手的名单。
 * 1.程序分析：判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除， 则表明此数不是素数，反之是素数。
 */
public class Test_18 {
        String a, b, c;
        public static void main (String[]args){
            String[] op = {"x", "y", "z"};
            ArrayList<Test_18> arrayList = new ArrayList<Test_18>();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 3; k++) {
                        Test_18 a = new Test_18(op[i], op[j], op[k]);
                        if (!a.a.equals(a.b) && !a.b.equals(a.c) && !a.a.equals("x")
                                && !a.c.equals("x") && !a.c.equals("z")) {
                            arrayList.add(a);
                        }
                    }
            for (Object a : arrayList) {
                System.out.println(a);
            }
        }
    public Test_18(String a, String b, String c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public String toString () {
// TODO Auto-generated method stub
        return "a的对手是" + a + "," + "b的对手是" + b + "," + "c的对手是" + c + "\n";
    }
    }

