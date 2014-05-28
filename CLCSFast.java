import java.util.*;
import java.lang.String;

public class CLCSFast {
  static int[][] arr = new int[2048][2048];
  static int max_CLCS = 0;
  // static char[] A, B;

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

    if (arr[m][n] > max_CLCS)
      max_CLCS = arr[m][n];

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

  public static void SingleShortestPath(char[] A, char[] B, int[][] path, int l, int u, int mid) {

    int m = A.length, n = B.length;
    int i, j;
    String C = "";
    for(i = 0; i<m; i++)
      C = C.concat(Character.toString(A[i]));
    C = rotate (C, mid);
    if (mid == 16)
    {
      System.out.println(C);
      for (int p = 0; p < 2*m+1; p++) 
        System.out.print(path[l][p] + " ");
      System.out.println();
      for (int p = 0; p < 2*m+1; p++) 
        System.out.print(path[u][p] + " ");
      System.out.println();
    }
    // for (i = 0; i <= m; i++) arr[i][0] = 0;
    // for (j = 0; j <= n; j++) arr[0][j] = 0;
    
    if (mid == 16)
    {
      System.out.print("    ");
      for (i = 1; i <= n ; i++)
        System.out.print ("0 ");
      System.out.println ();
      for (i = 1; i <= m; i++) {
        // System.out.println ((path[u][mid+i-1]+1) + " " + path[l][mid+i]);
        System.out.print (C.toCharArray()[i-1] + " 0 ");
        for (j = 1; j < path[u][mid+i-1]+1 ; j++)
          System.out.print ("* ");
        for (j = path[u][mid+i-1]+1; j <= path[l][mid+i]; j++) {
        // for (j = 1; j <= n; j++) { 
          if ((j-1) < path[u][mid+i-1]+1)
            arr[i][j] = arr[i-1][j];
          else if (j > path[l][mid+i-1])
            arr[i][j] = arr[i][j-1];
          else
            arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
          if (C.toCharArray()[i-1] == B[j-1] && ((j-1) >= path[u][mid+i-2]+1 && (j-1) <= path[l][mid+i-1] 
            || ((j-1) == 0 || (i-1) == 0)))
            {
              arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
              // System.out.println ("i= "+i +"; j= "+ j);  
              // System.out.println (C.toCharArray()[i-1] + ";" + B[j-1]);
            }
          System.out.print (arr[i][j]+" ");
          // System.out.println (C.toCharArray()[i-1]+" "+B[j-1]);
        }
        for (j = path[l][mid+i]+1 ; j <= n ; j++)
          System.out.print ("* ");
        System.out.println();
      }
    }
    else
    {
      for (i = 1; i <= m; i++) {
        for (j = path[u][mid+i-1]+1; j <= path[l][mid+i]; j++) {
          if ((j-1) < path[u][mid+i-1]+1)
            arr[i][j] = arr[i-1][j];
          else if (j > path[l][mid+i-1])
            arr[i][j] = arr[i][j-1];
          else
            arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
          if (C.toCharArray()[i-1] == B[j-1] && ((j-1) >= path[u][mid+i-2]+1 && (j-1) <= path[l][mid+i-1] 
            || ((j-1) == 0 || (i-1) == 0))) 
          {
            arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
          }
        }
      }
    }


    if (arr[m][n] > max_CLCS)
      max_CLCS = arr[m][n];

    findPath(m, n, mid, path);

    // if (mid == 5){
    //   for (int x = 0; x <= m; x++) {
    //     for (int y = 0; y <= n; y++) {
    //       System.out.print(arr[x][y]+" ");
    //     }
    //     System.out.println();
    //   }
    // }

  }

  public static void FindShortestPath (char[] A, char[] B, int[][] path, int l, int u) {
    if ((u-l) <= 1) 
      return;
    int mid = (l+u)/2;
    SingleShortestPath (A, B, path, l, u, mid);
    FindShortestPath (A, B, path, l, mid);
    FindShortestPath (A, B, path, mid, u);
  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    String A, B;
    for (int tc = 0; tc < 3; tc++) {
      max_CLCS = 0;
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
      // for (int i = 0; i < 2*m+1; i++) System.out.print(path[0][i] + " ");
      // System.out.println();
      // for (int i = 0; i < 2*m+1; i++) System.out.print(path[m][i] + " ");
      // System.out.println();

      FindShortestPath(A.toCharArray(), B.toCharArray(), path, 0, m);

      //  for (int i = 0; i < 2*m+1; i++) System.out.print(path[0][i] + " ");
      // System.out.println();
      // for (int i = 0; i < 2*m+1; i++) System.out.print(path[m][i] + " ");
      // System.out.println();
      // int max_LCS = 0;
      // for (int i = 0; i < A.length(); i++){
      //   int temp = LCS(rotate(A, i).toCharArray(), B.toCharArray());
      //   if (temp > max_LCS)
      //     max_LCS = temp;
      //   // System.out.println (rotate(A,i));
      // }
      System.out.println(max_CLCS);
    }
  }
}
