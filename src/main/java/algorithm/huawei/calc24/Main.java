package algorithm.huawei.calc24;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int[] nums = new int[4];
            for(int i = 0; i < 4; i++)
                nums[i] = sc.nextInt();
            boolean[] visited = new boolean[4];
            double res = 0.0;
            System.out.println(backtrack(nums, visited, res));
        }

    }

    private static boolean backtrack(int[] nums, boolean[] visited, double res){
        for(int i = 0; i < 4; i++){
            if(!visited[i]){
                visited[i] = true;
                if(backtrack(nums, visited, res + nums[i]) || backtrack(nums, visited, res - nums[i]) || backtrack(nums, visited, res * nums[i]) || backtrack(nums, visited, res / nums[i]) )
                    return true;
                visited[i] = false;
            }

        }
        return res == 24 ? true : false;
    }
}