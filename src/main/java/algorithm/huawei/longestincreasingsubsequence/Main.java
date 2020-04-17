package algorithm.huawei.longestincreasingsubsequence;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int N = sc.nextInt();
            int[] nums = new int[N];
            for(int i = 0; i < N; i++){
                nums[i] = sc.nextInt();
            }
            int[] dp = new int[N];
            for(int each : dp)
                each = 1;
            for(int i = 1; i < N; i++){
                for(int j = 0; j < i; j++){
                    if(nums[j] < nums[i]){
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            System.out.println(dp[N - 1]);
        }
    }
}