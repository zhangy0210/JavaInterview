package algorithm.leetcode.findKthLargest;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;


/**
 *
 * 最小的K个数
 */
class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = new int[]{62577,-220,-8737,-22,-6,59956,5363,-16699,0,-10603,64,-24528,-4818,96,5747,2638,-223,37663,-390,35778,-4977,-3834,-56074,7,-76,601,-1712,-48874,31,3,-9417,-33152,775,9396,60947,-1919,683,-37092,-524,-8,1458,80,-8,1,7,-355,9,397,-30,-21019,-565,8762,-4,531,-211,-23702,3,3399,-67,64542,39546,52500,-6263,4,-16,-1,861,5134,8,63701,40202,43349,-4283,-3,-22721,-6,42754,-726,118,51,539,790,-9972,41752,0,31,-23957,-714,-446,4,-61087,84,-140,6,53,-48496,9,-15357,402,5541,4,53936,6,3,37591,7,30,-7197,-26607,202,140,-4,-7410,2031,-715,4,-60981,365,-23620,-41,4,-2482,-59,5,-911,52,50068,38,61,664,0,-868,8681,-8,8,29,412};
//        int[] nums = new int[]{4, 5, 1, 6, 2, 7, 3};
        int[] res = s.getLeastNumbers(nums, 131);
        Arrays.sort(res);

        Arrays.sort(nums);
        for(int i = 0; i < 131; i++){
            System.out.println(res[i] + " == " + nums[i]);
            if(res[i] != nums[i])
                System.out.println("DIFFERENT");
        }
    }
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k == 0)
            return new int[0];
        if(k >= arr.length)
            return arr;
        partitionArray(arr, 0, arr.length - 1, k);
        int[] res = new int[k];
        for(int i = 0; i < k; i++){
            res[i] = arr[i];
        }
        return res;
    }

    private void partitionArray(int[] nums, int left, int right, int k){
        int mid = partition(nums, left, right);
        if(mid == k)
            return;
        else if(mid > k){
            partitionArray(nums, left, mid - 1, k);
        }else{
            partitionArray(nums, mid+1, right, k);
        }
    }

    private int partition(int[] nums, int left, int right){
        int i = left;
        int j = right+1;
        int pivot = nums[left];
        while(true){
            while(nums[++i] < pivot){
                if(i == right)
                    break;
            }
            while(nums[--j] > pivot){
                if(j == left)
                    break;
            }
            if(i >= j)
                break;
            swap(nums, i, j);
        }
        swap(nums, left, j);
        return j;
    }


    private void swap(int[] nums, int left, int right){
        int temp = nums[right];
        nums[right] = nums[left];
        nums[left] = temp;
    }
}