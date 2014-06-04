import java.util.*;
import java.lang.String;

public class CLCSFast1 {
  static int[][] arr = new int[2048][2048];
  static int max_CLCS = 0;


  static void printP(int[][] path, int mid,int m) {
    for (int i = 0; i < 2*m+1; i ++)
      System.out.print(path[mid][i]);
    System.out.println();
  }
  
    public static void InitialCLCS(char[] A, char[] B, int[][] path, int[][] path_s) {
    int m = A.length/2, n = B.length;
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
    
    i = m; j = n;
    while (i > 0 && j > 0) {  
      path_s[0][i] = j;     
      if (arr[i][j] == arr[i-1][j]) {
        i--;
        path[0][i] = j;
      }
      else if (arr[i][j] == arr[i][j-1])
        j--; 
      else {
        i--;
        j--;
        path[0][i] = j;
      }
    }

    for (i = m; i < 2*m+1; i++) {
      path[0][i] = n;
      path_s[0][i] = n;
    }
    for (i = 0; i < m+1; i++) {
      path[m][i+m] = path[0][i];
      path_s[m][i+m] = path_s[0][i];
    }

    // printP(path_s, 0, m);
    // printP(path_s, m, m);
    
  }


  public static void findPath(char[] A,  char[] B, int mid, int[][] path, int[][] path_s, int l, int u) {
    int m = A.length/2, n = B.length;
    int i = m, j = n;

    while (i > 0 && j > 0) {
      path_s[mid][i+mid] = j;
      if (arr[i-1][j] == arr[i][j] && j <= path[l][mid+i-1]) {
        i--;
        path[mid][i+mid] = j;
      }
      else if (arr[i][j-1] == arr[i][j] && (j-1) >= path_s[u][mid+i]) 
        j--;

      else {
        i--;
        j--;
        path[mid][i+mid] = j;
      }
    }

    for (i = mid+m; i < 2*m+1; i++) {
      path[mid][i] = n;
      path_s[mid][i] = n;
    }

    // printP(path, mid, m);
    // printP(path_s, mid, m);

  }


  public static void SingleShortestPath(char[] A, char[] B, int[][] path, int[][] path_s, int l, int u, int mid) {
    int m = A.length/2, n = B.length;
    int i, j;
    for (i = 1; i <= m; i++) {
        for (j = Math.max(path_s[u][mid+i],1); j <= path[l][mid+i]; j++) {
          int ai_1, aj_1, aij_1;
          if ((j-1) < path_s[u][mid+i])
            aj_1 = -1;
          else
            aj_1 = arr[i][j-1];
          if (j > path[l][mid+i-1])
            ai_1 = -1;
          else
            ai_1 = arr[i-1][j];
      
          arr[i][j] = Math.max(ai_1, aj_1);

          if (A[i-1+mid] == B[j-1] && ((j-1) >= path_s[u][mid+i-1] && (j-1) <= path[l][mid+i-1])) 
            arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
        }
    }

    //  for (int h = 0; h <= m; h ++) {
    //   for(int k = 0; k <= n; k ++ )
    //     System.out.print(arr[h][k]);
    //   System.out.println();
    // }
    if (arr[m][n] > max_CLCS)
      max_CLCS = arr[m][n];

    findPath(A, B, mid, path, path_s, l, u);


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
      String temp = s.next();
      A = temp + temp;
      B = s.next();
      int m = A.length()/2, n = B.length();
      int path[][] = new int[m+1][2*m+1];
      int path_s[][] = new int[m+1][2*m+1];

      InitialCLCS(A.toCharArray(), B.toCharArray(), path, path_s);
      // SingleShortestPath(A.toCharArray(), B.toCharArray(), path, path_s, 0, m, m/2);
      FindShortestPath(A.toCharArray(), B.toCharArray(), path, path_s, 0, m);

      System.out.println(max_CLCS);
    }
  }
}
