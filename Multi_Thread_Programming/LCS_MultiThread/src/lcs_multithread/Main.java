package lcs_multithread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    
    private static int value1, value2;
    
    public static void main(String[] args) {
        String a = "", b = "";
        try {
            Scanner in1 = new Scanner(new File("/Users/knight12738/Downloads/LCS_MultiThread/src/lcs_multithread/String1.txt"));
            a = in1.nextLine();
            System.out.println("First string was received.");
            Scanner in2 = new Scanner(new File("/Users/knight12738/Downloads/LCS_MultiThread/src/lcs_multithread/String2.txt"));
            b = in2.nextLine();
            System.out.println("Second string was received.");
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
        System.out.println("Processing.");
        System.out.println("Answer : " + lcs(a, b, a.length(), b.length()));
    }
    
    public static int lcs(String X, String Y, int i, int j) {
        if(i == 0 || j == 0) return 0;
        if(X.charAt(i - 1) == Y.charAt(j - 1)) return lcs(X, Y, i - 1, j - 1) + 1;
        else {
            LCS thread1 = new LCS(X, Y, i - 1, j);
            LCS thread2 = new LCS(X, Y, i - 1, j);
            Thread fn1 = new Thread(thread1);
            Thread fn2 = new Thread(thread2);
            fn2.start(); fn1.start();
            try {
                fn1.join(); fn2.join();
            } catch(InterruptedException e) {
                System.out.println("Interruption of Thread.");
            }
            return Math.max(thread1.getAnswer(), thread2.getAnswer());
        }
    }
    
}
