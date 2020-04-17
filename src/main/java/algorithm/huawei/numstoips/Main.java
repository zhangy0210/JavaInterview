package algorithm.huawei.numstoips;

/**
 * 十进制IP与常见IP的互相转换
 */

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
//        while(sc.hasNext()){
//            String s = sc.nextLine();
//            String[] strs = s.split("\\.");
////            Integer num = sc.nextInt();
//            int res = 0;
//            for(int i = 0; i < strs.length; i++){
//                res += (Integer.valueOf(strs[strs.length - i - 1]) << (8 * i));
//            }
//            System.out.println(res);
//        }
        List<Integer> list = new ArrayList<>();
        int num = sc.nextInt();
        for(int i = 0; i < 4; i++){
            list.add((num >> (8 * i)) & (255));
        }
    }
}