import java.util.*;


public class LCS {
  static int[][] arr = new int[2048][2048];
  static char[] A, B;

  static int LCS() {
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

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < 1; tc++) {
      A = s.next().toCharArray();
      B = s.next().toCharArray();
      LCS();
      for (int x = 0; x <= A.length; x++) {
        for (int y = 0; y <= B.length; y++) {
          System.out.print(arr[x][y]+" ");
        }
        System.out.println();
      }

      // System.out.println(LCS());
    }
  }
}
