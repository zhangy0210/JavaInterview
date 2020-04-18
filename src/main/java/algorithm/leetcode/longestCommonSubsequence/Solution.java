package algorithm.leetcode.longestCommonSubsequence;


/**
 * 1143. 最长公共子序列(不是子串,是不连续的)
 *
 */
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        String s1 = " " + text1;
        String s2 = " " + text2;
        int[][] dp = new int[s1.length()][s2.length()];
        for(int i = 1; i < dp.length; i++){
            for(int j = 1; j < dp[0].length; j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }
}