package lcs_multithread;
public class LCS implements Runnable{
    
    private int length;
    
    public LCS(String X, String Y, int i, int j) {
        length = Process(X, Y, i, j);
    }
    
    private int Process(String X, String Y, int i, int j) {
        if(i == 0 || j == 0) return 0;
        if(X.charAt(i - 1) == Y.charAt(j - 1)) return Process(X, Y, i - 1, j - 1) + 1;
        else {
            return Math.max(Process(X, Y, i - 1, j), Process(X, Y, i, j - 1));
        }
    }
    
    public int getAnswer() {
        return length;
    }

    @Override
    public void run() {}
    
}
