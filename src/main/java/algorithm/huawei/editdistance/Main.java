package algorithm.huawei.editdistance;


/**
 * 题目要求计算相似度,实际上也是编辑距离
 */
import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s1 = " " + sc.next();
            String s2 = " " + sc.next();
            int M = s1.length();
            int N = s2.length();
            int[][] dp = new int[M][N];
            for(int i = 1; i < M; i++)
                dp[i][0] = dp[i-1][0] + 1;
            for(int i = 1; i < N; i++)
                dp[0][i] = dp[0][i-1] + 1;
            for(int i = 1; i < M; i++){
                for(int j = 1; j < N; j++){
                    if(s1.charAt(i) == s2.charAt(j)){
                        dp[i][j] = dp[i-1][j-1];
                    }else{
                        dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                    }
                }
            }
            int res = dp[M - 1][N - 1];
            System.out.println("1/" + String.valueOf(res + 1));
        }
    }
}
