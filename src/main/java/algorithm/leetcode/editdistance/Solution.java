package algorithm.leetcode.editdistance;


/**
 *
 * 72. 编辑距离
 */
class Solution {
    public int minDistance(String word1, String word2) {
        word1 = " " + word1;
        word2 = " " + word2;
        int M = word1.length();
        int N = word2.length();
        int[][] dp = new int[M][N];
        for(int i = 1; i < M; i++)
            dp[i][0] = dp[i-1][0] + 1;
        for(int i = 1; i < N; i++)
            dp[0][i] = dp[0][i-1] + 1;
        for(int i = 1; i < M; i++){
            for(int j = 1; j < N; j++){
                if(word1.charAt(i) == word2.charAt(j))
                    dp[i][j] = dp[i-1][j-1];
                else
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
            }
        }
        return dp[M - 1][N - 1];
    }
}
