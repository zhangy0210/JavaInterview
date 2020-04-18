package algorithm.huawei.longestcontinuousbit1;


/**
 * 最长连续1的长度
 */

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int N = sc.nextInt();
            int res = 0;
            int count = 0;
            while(N > 0){
                if((N & 1) == 1){
                    count++;
                    res = Math.max(res, count);
                }else{
                    count = 0;
                }
                N >>= 1;
            }
            System.out.println(res);
        }
    }
}