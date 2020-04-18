package algorithm.leetcode.containerwithmostwater;



public class Solution {
    /**
     * 暴力解法,全部遍历
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int max = 0;
        for(int j = 1; j < height.length; j++){
            for(int i = 0; i < j; i++){
                max = Math.max(max, (j-i)*(Math.min(height[i], height[j])));
            }
        }
        return max;
    }

    /**
     * 双指针解法
     * 将小的边舍弃,选择大的边继续比较
     * @param height
     * @return
     */
    public int maxArea1(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while(left < right){
            max = Math.max(max, (right - left) * (Math.min(height[left], height[right])));
            if(height[left] < height[right])
                left++;
            else
                right--;
        }
        return max;
    }

    /**
     * 再优化一下
     * 跳过不需要的边
     * @param height
     * @return
     */
    public int maxArea2(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while(left < right){
            int minEdge = Math.min(height[left], height[right]);
            max = Math.max(max, (right - left) * minEdge);
            while(left < right && height[left] <= minEdge)
                left++;
            while(left < right && height[right] <= minEdge)
                right--;
        }
        return max;
    }
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        s.maxArea(height);
    }
}
