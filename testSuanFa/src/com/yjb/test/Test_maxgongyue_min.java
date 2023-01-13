package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/2 14:15
 */

/**
 * 题目：输入两个正整数m和n，求其最大公约数和最小公倍数。
 * 1.程序分析：利用辗除法。
 */
public class Test_maxgongyue_min {
    /*public static void main(String args[])
    {
        commonDivisor(5,100);
    }
    static int commonDivisor(int M, int N)
    {
        if(N<0||M<0)
        {
            System.out.println("ERROR!");
            return -1;
        }
        if(N==0)
        {
            System.out.println("the biggest common divisor is :"+M);
            return M;
        }
        return commonDivisor(N,M%N);
    }*/
//    public class CandC
//    {
        //下面的方法是求出最大公约数
        public static int gcd(int m, int n)
        {
            while (true)
            {
                if ((m = m % n) == 0)
                    return n;
                if ((n = n % m) == 0)
                    return m;
            }
        }
        public static void main(String args[]) throws Exception
        {
        //取得输入值
        //Scanner chin = new Scanner(System.in);
        //int a = chin.nextInt(), b = chin.nextInt();
        int a=23; int b=32;
        int c = gcd(a, b);
        System.out.println("最小公倍数：" + a * b / c + "\n最大公约数：" + c);
        }
}
