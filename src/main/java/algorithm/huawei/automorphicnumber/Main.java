package algorithm.huawei.automorphicnumber;


/**
 * 求自守数
 */

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int N = sc.nextInt();
            int res = 0;
            for(int i = 0; i <= N; i++){
                if(String.valueOf(i * i).substring(String.valueOf(i * i).length() - String.valueOf(i).length()).equals(String.valueOf(i)))
                    res++;
            }
            System.out.println(res);
        }
        sc.close();
    }
}