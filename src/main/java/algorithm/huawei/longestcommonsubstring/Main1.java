package algorithm.huawei.longestcommonsubstring;


/**
 *
 * 最长公共子串 (连续的,不是子序列)
 *
 */

import java.util.*;
public class Main1{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s1 = sc.next();
            String s2 = sc.next();
            s1 = " " + s1;
            s2 = " " + s2;
            int[][] dp = new int[s1.length()][s2.length()];
            int res = 0;
            for(int i = 1; i < dp.length; i++){
                for(int j = 1; j < dp[0].length; j++){
                    if(s1.charAt(i) == s2.charAt(j)){
                        dp[i][j] = dp[i-1][j-1] + 1;
                        res = dp[i][j] > res ? dp[i][j] : res;
                    }
                }
            }
            System.out.println(res);
        }
    }
}