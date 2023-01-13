package com.yjb.test;

/**
 * @author yjb
 * @date 2021/3/22 16:50
 */

/**
 * 题目：打印出如下图案（菱形）
     *
     ***
     ******
     ********
     ******
     ***
     *
 * 1.程序分析：先把图形分成两部分来看待，前四行一个规律，后三行一个规律，利用双重 for循环，第一层控制行，第二层控制列。
 */
public class Test_19 {
   /* public static void main(String [] args)
    {
        int i=0;
        int j=0;
        for(i=1;i<=4;i++)
        { for(j=1;j<=2*i-1;j++)
            System.out.print("*");
            System.out.println("");
        }
        for(i=4;i>=1;i--)
        { for(j=1;j<=2*i-3;j++)
            System.out.print("*");
            System.out.println("");
        }
    }*/
   public static void main(String [] args)
   {
       int i=0;
       int j=0;
       for(i=1;i<=4;i++)
       {
           for(int k=1; k<=4-i;k++)
               System.out.print(" ");
           for(j=1;j<=2*i-1;j++)
               System.out.print("*");
           System.out.println("");
       }
       for(i=4;i>=1;i--)
       {
           for(int k=1; k<=5-i;k++)
               System.out.print(" ");
           for(j=1;j<=2*i-3;j++)
               System.out.print("*");
           System.out.println("");
       }
   }
}
