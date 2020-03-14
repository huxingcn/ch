package com.click.house.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
题目描述：
大家知道，给出正整数n，则1到n这n个数可以构成n！种排列，把这些排列按照从小到大的顺序（字典顺序）列出，如n=3时，列出1 2 3，1 3 2，2 1 3，2 3 1，3 1 2，3 2 1六个排列。

任务描述：
给出某个排列，求出这个排列的下k个排列，如果遇到最后一个排列，则下1排列为第1个排列，即排列1 2 3…n。
比如：n = 3，k=2 给出排列2 3 1，则它的下1个排列为3 1 2，下2个排列为3 2 1，因此答案为3 2 1。
输入
第一行是一个正整数m，表示测试数据的个数，下面是m组测试数据，每组测试数据第一行是2个正整数n( 1 <= n < 1024 )和k(1<=k<=64)，第二行有n个正整数，是1，2 … n的一个排列。
输出
对于每组输入数据，输出一行，n个数，中间用空格隔开，表示输入排列的下k个排列。
样例输入
3
3 1
2 3 1
3 1
3 2 1
10 2
1 2 3 4 5 6 7 8 9 10
样例输出
3 1 2
1 2 3
1 2 3 4 5 6 7 9 8 10
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  huxing
 * @version  [版本号, 2020年3月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class demo
{
    
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        List<Map<String, Object>> list = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int max = scan.nextInt();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 1; i <= max; i++)
        {
            String value = new Scanner(System.in).nextLine();
            map.put("n", value.split(" ")[0]);
            map.put("k", value.split(" ")[1]);
            String value2 = new Scanner(System.in).nextLine();
            map.put("list", crateList(value2.split(" ").length, value2.split(" ")));
            map.put("listStr", Arrays.toString(value2.split(" ")));
            list.add(map);
            map = new HashMap<String, Object>();
        }
        System.out.println("------输出结果---------");
        for (Map<String, Object> obj : list)
        {
            total = 1;
            Integer n = Integer.valueOf(obj.get("n").toString());//n长度
            Integer k = Integer.valueOf(obj.get("k").toString());//给定k位置
            List<Integer> mq = crateList(n, null);//第一个排列
            List<Integer> tmq = (List<Integer>)obj.get("list");//给定的排列
            String mqString = (String)obj.get("listStr");//默认输出初始排列
            mf(tmq, mq, n, 0);//根据初始排列，判断给定的队列在全排列中的的位置
            if (total + k > getfac_s(n)) //判断给定排列位置+k 是否超过全排列
                System.out.println(mqString);//如果1 2 3 全排列 数量是6 给定的排列在第五位置，k=2，那么找不到5+2在全排列中的排列，就给出第一个排列
            else
                System.out.println(getPermutation(n, total + k));//如1,2,3 给定的排列在第四，k=1那么就可以取1,2,3全排列第五个排列
        }
    }
    
    static int total = 1;
    
    static List<Integer> crateList(int n, String[] objList)
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
        {
            if (null != objList)
                list.add(Integer.valueOf(objList[i]));
            else
                list.add(i + 1);
            
        }
        return list;
    }
    
    /**
     * 计算指定数列在全队列中的位置
     * <一句话功能简述>
     * <功能详细描述>
     * @param list
     * @param t
     * @param n
     * @param index
     * @see [类、类#方法、类#成员]
     */
    static void mf(List<Integer> list, List<Integer> t, int n, int index)
    {
        for (int i = 1; i <= t.size(); i++)
        {
            if (t.get(i - 1) == list.get(index) && n > 0)
            {
                t.remove(i - 1);
                //                System.out.println((i - 1) * getfac_s(n - 1));
                total += (i - 1) * getfac_s(n - 1);
                mf(list, t, n - 1, index + 1);
            }
        }
    }
    
    static int getfac_s(int n)
    {
        //计算s=n!+(n+1)!+(n+2)!+...+n!
        int i;
        int s = 1, sum = 0;
        for (i = 1; i <= n; i++)
        {
            s = s * i;
            if (i >= n)
            {
                sum += s;
            }
        }
        return sum;
    }
    
    /*
     * 通过f(n,k)=n_list[k/(n−1)!]+f(n−1,k%(n−1)!)
     * 计算指定位置的排列
     */
    public static String getPermutation(int n, int k)
    {
        boolean[] choosed = new boolean[n];//choosed[i]存储的是i+1是否被使用
        int[] factorial = new int[n - 1];//factorial[i]存储的是i+1的阶乘
        int temp = 1;
        for (int i = 1; i < n; i++)
        {
            temp *= i;
            factorial[i - 1] = temp;
        }
        StringBuilder sb = new StringBuilder();
        k--;//这里不能少，毕竟我们是从0开始数，而给的k是从1开始。
        for (int i = n - 2; i >= 0; i--)
        {
            int count = 0;
            int index = -1;
            while (count < k / factorial[i] + 1)
            {
                index++;
                if (!choosed[index])
                {
                    count++;
                }
            }
            choosed[index] = true;
            sb.append((index + 1) + " ");
            k %= factorial[i];
        }
        for (int i = 0; i < choosed.length; i++)
        {
            if (!choosed[i])
            {
                sb.append(i + 1);
                break;
            }
        }
        return sb.toString();
    }
    
}
