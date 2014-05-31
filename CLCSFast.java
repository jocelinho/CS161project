import java.util.*;
import java.lang.String;

public class CLCSFast {
  static int[][] arr = new int[2048][2048];
  static int max_CLCS = 0;

  public static String rotate (String s, int k){
    String a = s.substring(k, s.length());
    String b = s.substring(0, k);
    return a.concat(b);

  }

  public static void SingleShortestPathNoBoundary(char[] A, char[] B, int[][] path, int[][] path_s) {
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

    if (arr[m][n] > max_CLCS)
      max_CLCS = arr[m][n];

    findPathNoBoundary(m, n, 0, path, path_s);
    findPathNoBoundary(m, n, m, path, path_s);
  }

  public static void findPathNoBoundary(int m, int n, int k, int[][] path, int[][] path_s) {
    int i = m, j = n;
    while (i > 0 && j > 0) {
      path_s[k][i+k] = j;
      if (path[k][i+k] < 0) 
        path[k][i+k] = j;         
      if (arr[i][j] == arr[i-1][j]) 
        i--;
      else if (arr[i][j] == arr[i][j-1])
        j--; 
      else {
        i--;
        j--;
      }
    }

    while (i > 0) {
      path_s[k][i+k] = 0;
      path[k][i+k] = 0;
      i--;
    }

    while (j > 0) {
      if (path[k][i+k] < 0) 
        path[k][i+k] = j;
      path_s[k][i+k] = j;
      j--;
    }

    for (i = 0; i < k; i++) 
      {
        path[k][i] = 0;
        path_s[k][i] = 0;
      }
    if (path[k][k] < 0) 
      path[k][k] = 0;
    path_s[k][k] = 0;
    
    for (i = k+m+1; i < 2*m+1; i++) 
      {
        path[k][i] = n; 
        path_s[k][i] = n;
      }

  }

  public static void findPath(char[] C,  char[] B, int k, int[][] path, int[][] path_s, int l, int u) {
    int m = C.length, n = B.length;
    int i = m, j = n;
    while (i > 0 && j > 0) {

      path_s[k][i+k] = j;
      if (path[k][i+k] < 0) 
        path[k][i+k] = j;         
      if (j <= path[l][i+k-1] && arr[i][j] == arr[i-1][j]) 
        i--;
      else if ( j-1 <= path[l][i+k-1] && j-1 >= path[u][i+k-1] && C[i-1] == B[j-1] )
        {
          i--;
          j--;
        }
      else
        j--;
    }

    while (i > 0) {
      path_s[k][i+k] = 0;
      path[k][i+k] = 0;
      i--;
    }

    while (j > 0) {
      if (path[k][i+k] < 0) 
        path[k][i+k] = j;
      path_s[k][i+k] = j;
      j--;
    }

    for (i = 0; i < k; i++) 
      {
        path[k][i] = 0;
        path_s[k][i] = 0;
      }
    if (path[k][k] < 0) 
      path[k][k] = 0;
    path_s[k][k] = 0;
    
    for (i = k+m+1; i < 2*m+1; i++) 
      {
        path[k][i] = n; 
        path_s[k][i] = n;
      }

  }

  public static void SingleShortestPath(char[] A, char[] B, int[][] path, int[][] path_s, int l, int u, int mid) {

    int m = A.length, n = B.length;
    int i, j;
    String C = "";
    for(i = 0; i<m; i++)
      C = C.concat(Character.toString(A[i]));
    C = rotate (C, mid);
    
      for (i = 1; i <= m; i++) {
        for (j = Math.max(path_s[u][mid+i],1); j <= path[l][mid+i]; j++) {
          if ((j-1) < path_s[u][mid+i])
            arr[i][j] = arr[i-1][j];
          else if (j > path[l][mid+i-1])
            arr[i][j] = arr[i][j-1];
          else
            arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
          if (C.charAt(i-1) == B[j-1] && ((j-1) >= path_s[u][mid+i-1] && (j-1) <= path[l][mid+i-1])) 
          {
            arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
          }
        }
      }

    if (arr[m][n] > max_CLCS)
      max_CLCS = arr[m][n];
    findPath(C.toCharArray(), B, mid, path, path_s, l, u);

  }

  public static void FindShortestPath (char[] A, char[] B, int[][] path, int[][] path_s, int l, int u) {
    if ((u-l) <= 1) 
      return;
    int mid = (l+u)/2;
    SingleShortestPath (A, B, path, path_s, l, u, mid);
    FindShortestPath (A, B, path, path_s, l, mid);
    FindShortestPath (A, B, path, path_s, mid, u);
  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    String A, B;
    for (int tc = 0; tc < T; tc++) {
      max_CLCS = 0;
      A = s.next();
      B = s.next();
      int m = A.length(), n = B.length();
      int path[][] = new int[m+1][2*m+1];
      int path_s[][] = new int[m+1][2*m+1];
      for (int i = 0; i < m+1; i++) {
        for (int j = 0; j < 2*m+1; j++) {
          path[i][j] = -1;
          path_s[i][j] = -1;
        }
      }
      SingleShortestPathNoBoundary(A.toCharArray(), B.toCharArray(), path, path_s);

      FindShortestPath(A.toCharArray(), B.toCharArray(), path, path_s, 0, m);

      System.out.println(max_CLCS);
    }
  }
}
