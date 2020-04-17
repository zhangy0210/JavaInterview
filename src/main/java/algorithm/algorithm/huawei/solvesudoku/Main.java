package algorithm.algorithm.huawei.solvesudoku;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int[][] matrix = new int[9][9];
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    matrix[i][j] = sc.nextInt();
                }
            }
            backtrack(matrix, 0, 0);
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(i != 8)
                        System.out.print(matrix[i][j] + " ");
                    else
                        System.out.println(matrix[i][j]);
                }
            }
        }
    }

    private static boolean backtrack(int[][] matrix, int i, int j){
        if(j == 9)
            return backtrack(matrix, i + 1, j);
        if(i == 9)
            return true;
        if(matrix[i][j] != 0)
            return backtrack(matrix, i, j + 1);
        for(int n = 1; n <= 9; n++){
            if(!isValid(matrix, i, j, n))
                continue;
            matrix[i][j] = n;
            if(backtrack(matrix, i, j + 1))
                return true;
            matrix[i][j] = 0;
        }
        return false;
    }

    private static boolean isValid(int[][] matrix, int i, int j, int n){
        for(int k = 0; k < 9; k++){
            if(matrix[i][k] == n || matrix[k][j] == n || matrix[(i/3)*3 + k/3][(j/3)*3 + k%3] == n)
                return false;
        }
        return true;
    }
}