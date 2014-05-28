import java.util.*;
import java.lang.String;

public class CLCSSlow {
  static int[][] arr = new int[2048][2048];
  // static char[] A, B;

  static int LCS(char[] A, char[] B) {
    int m = A.length, n = B.length;
    int i, j;
    for (i = 0; i <= m; i++) arr[i][0] = 0;
    for (j = 0; j <= n; j++) arr[0][j] = 0;
    
    for (i = 1; i <= m; i++) {
      for (j = 1; j <= n; j++) {
        arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
        if (A[i-1] == B[j-1]) arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
      }
    }
    
    return arr[m][n];
  }

  public static String rotate (String s, int k){
    String a = s.substring(k, s.length());
    String b = s.substring(0, k);
    return a.concat(b);

  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    String A, B;
    for (int tc = 0; tc < T; tc++) {
      A = s.next();
      B = s.next();
      int max_LCS = 0;
      for (int i = 0; i < A.length(); i++){
        int temp = LCS(rotate(A, i).toCharArray(), B.toCharArray());
        if (temp > max_LCS)
          max_LCS = temp;
        if (temp == 22)
          System.out.println (rotate(A,i));
      }
      System.out.println(max_LCS);
    }
  }
}
