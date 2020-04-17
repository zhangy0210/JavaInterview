package algorithm.huawei.pick7;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int N = sc.nextInt();
            int res = 0;
            for(int i = 1; i <= N; i++){
                if(hasRelationWith7(i))
                    res++;
            }
            System.out.println(res);
        }
    }

    private static boolean hasRelationWith7(int n){
        char[] s = String.valueOf(n).toCharArray();
        for(int i = 0; i < s.length; i++){
            if(s[i] - '0' == 7)
                return true;
        }
        return n % 7 == 0;
    }
}