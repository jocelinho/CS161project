import java.util.*;
import java.lang.String;

public class CLCSFast {
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

  public static void SingleShortestPathNoBoundary(char[] A, char[] B, int[][] path) {
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

    // for (int x = 0; x <= m; x++) {
    //   for (int y = 0; y <= n; y++) {
    //     System.out.print(arr[x][y]+" ");
    //   }
    //   System.out.println();
    // }

    findPath(m, n, 0, path);
    findPath(m, n, m, path);
  }

  public static void findPath(int m, int n, int k, int[][] path) {
    int i = m, j = n;
    while (!(i == 0 && j == 0)) {
      if (path[k][i+k] < 0) path[k][i+k] = j;
      if (i > 0 && arr[i][j] == arr[i-1][j]) 
        i--;
      else if (j > 0 && arr[i][j] == arr[i][j-1])
        j--;
      else {
        i--;
        j--;
      }
    }
    for (i = 0; i < k; i++) path[k][i] = 0;
    if (path[k][k] < 0) path[k][k] = 0;
    for (i = k+m; i < 2*m+1; i++) path[k][i] = n; 
  }

  public static void SingleShortestPath(char[] A, char[] B, int[][] path, int l, int u) {
    if ((u-l) <= 1) return;
    int mid = (l+u)/2;

    int m = A.length, n = B.length;
    int i, j;
    // for (i = 0; i <= m; i++) arr[i][0] = 0;
    // for (j = 0; j <= n; j++) arr[0][j] = 0;
    
    for (i = 1; i <= m; i++) {
      for (j = path[l][mid+i-1]+1; j <= path[u][mid+i]; j++) {
        arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
        if (A[i-1] == B[j-1]) arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
      }
    }

    for (int x = 0; x <= m; x++) {
      for (int y = 0; y <= n; y++) {
        System.out.print(arr[x][y]+" ");
      }
      System.out.println();
    }

  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    String A, B;
    for (int tc = 0; tc < 1; tc++) {
      A = s.next();
      B = s.next();
      int m = A.length(), n = B.length();
      int path[][] = new int[m+1][2*m+1];
      for (int i = 0; i < m+1; i++) {
        for (int j = 0; j < 2*m+1; j++) {
          path[i][j] = -1;
        }
      }
      SingleShortestPathNoBoundary(A.toCharArray(), B.toCharArray(), path);
      for (int i = 0; i < 2*m+1; i++) System.out.print(path[0][i] + " ");
      System.out.println();
      for (int i = 0; i < 2*m+1; i++) System.out.print(path[m][i] + " ");
      System.out.println();

      SingleShortestPath(A.toCharArray(), B.toCharArray(), path, 0, m);
      // int max_LCS = 0;
      // for (int i = 0; i < A.length(); i++){
      //   int temp = LCS(rotate(A, i).toCharArray(), B.toCharArray());
      //   if (temp > max_LCS)
      //     max_LCS = temp;
      //   // System.out.println (rotate(A,i));
      // }
      // System.out.println(max_LCS);
    }
  }
}
