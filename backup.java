import java.util.*;


public class LCS {
  static int[][] arr = new int[4096][2048];
  static char[] A, B; 
  static int [][] p = new int [2048][4096]; 
  static int m;  // Real size of A 
  static int n;  // Real size of B
  
  static int LCS() {
     return 0;
  }

  static void printArr(){
	  for (int i = 0; i < (m*2 + 1); ++i){
		  for (int j = 0; j < n+1; ++j){
			 System.out.print(arr[i][j]);
			 System.out.print(" "); 
		  }
		  System.out.println(" "); 
	  }
	  System.out.println(" "); 
  }
  
  static void printP(){
	  for (int i = 0; i < p.length; ++i){
		  for (int j = 0; j < p[i].length; ++j){
			 System.out.print(p[i][j]);
			 System.out.print(" "); 
		  }
		  System.out.println(""); 
	  }	  
	  System.out.println(" "); 
  }  
  
  // Initialize p[0] and p[m]
  static int initCLCS(){
	  
	  // ----- DP Table Calculation ----- //
	  int i, j;
	  for (i = 0; i <= m; i++) arr[i][0] = 0;
	  for (j = 0; j <= n; j++) arr[0][j] = 0;  
	  for (i = 1; i <= m; i++) {
	    for (j = 1; j <= n; j++) {
	      arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
	      if (A[i-1] == B[j-1]){ 
	      	arr[i][j] = Math.max(arr[i][j], arr[i-1][j-1]+1);
	      }
	    }
	  }	  
	  //printArr(); 
	  // ----- Path Backtracking ----- //
      // Fill in the block in [m+1:2m], total (m) element
	  for (i = m+1; i < (2*m + 1); ++i)
	      p[0][i] = n; 
	  
	  // Calculate p in the range [0:m], total (m + 1) element
	  // Fill in the value when there is upward move, namely --m 
      int itm = m; int itn = n; 
      p[0][itm] = itn; // The beginning element
	  while (itm != 0 && itn != 0){		
		  
		  if (arr[itm-1][itn] == arr[itm][itn]){   // Move upward
			  itm--; 
			  p[0][itm] = itn; 
		  }
		  else if (arr[itm][itn -1] == arr[itm][itn]){   // Move left
			  itn--; 
		  }
		  else {   // Move diagonally
			  itn--;
			  itm--;
			  p[0][itm] = itn; 
		  }
	  }
	  
	  // Copy p[0] to p[m-1]
	  for (i = 0; i < m+1; ++i){
		  p[m][m+i] = p[0][i]; 
	  }
	  
	  return arr[m][n]; 
	  
  }
  
  // If (i,j) in valid region return TRUE
  static boolean compareU(int u, int i, int j){
	  if (j > p[u][i])
		  return false;
	  return true;
  }
  // If (i,j) in valid region return TRUE
  static boolean compareL(int l, int i, int j){
	  if (p[l][i-1] > j)
		  return false; 
	  return true; 
  }
  
  static int singleshortestpath(int mid,int l,int u){
	  
	  //System.out.print("mid = ");
	  //System.out.println(mid); 
	  // ----- DP Table Calculation ----- //
	  // Initialize the (mid)_th row and first column of DP Table
	  int i, j;
	  for (i = 0; i <= m; i++) {     // Move down, check lower bound
		  if (p[l][i] > 1) break;
		  arr[i][0] = 0;
	  }
	  for (j = 0; j <= n; j++) {     // Move right, check upper bound
		  if (j > p[u][mid]) break;
		  arr[mid][j] = 0;
	  }
	  
	  // Elements start from (mid + 1)_th row 
	  for (i = 1 + mid; i <= m + mid; i++) {
	    for (j = 1; j <= n; j++) {
		  // Check element out of bound (column) -> next row
	      //printArr(); 
		  //if (j > p[u][i])
	      if (!compareU(u,i,j))
			 break; 
	      // Check element out of bound (row) -> next column
          //if (p[l][i-1] >= j + 1)
	      if (!compareL(l,i,j))
	         continue; 
          
          int ai1_j, ai_j1, ai1_j1; 
          // Upper element -> Compare with upper bound
	      //if (j > p[u][i-1])
          if (!compareU(u,i-1,j))
	    	  ai1_j = -2; 
	      else 
	    	 ai1_j = arr[i-1][j];  
	      // Left element -> Compare with lower bound
	      //if (p[l][i] >= j)
          if (!compareL(l,i,j-1))
	    	  ai_j1 = -2; 
	      else 
	    	  ai_j1 = arr[i][j-1];
	      
	      arr[i][j] = Math.max(ai1_j, ai_j1);
	      if (A[i-1] == B[j-1]){
	    	// Diagonal Element -> Compare with both bound
	    	//if ((j-1 > p[u][i-1]) && (p[l][i-1] >= j))
	    	if ((!compareU(u,i-1,j-1)) || (!compareL(l,i-1,j-1)))
	    		ai1_j1 = -2;
	    	else
	    		ai1_j1 = arr[i-1][j-1];
	      	arr[i][j] = Math.max(arr[i][j], ai1_j1+1);
	      }
	    }
	  }	  
	  //printArr(); 
	  //System.out.println("Finish DP Table"); 
	  // ----- Path Backtracking ----- //
      // Fill in the block in [0:mid-1],[mid+m+1:2m], total (m) element
	  for (i = 0; i < mid+1; ++i)
		  p[mid][i] = 0; 
	  for (i = mid+m+1; i < (2*m + 1); ++i)
	      p[mid][i] = n; 
	  
	  // Calculate p in the range [0:m], total (m + 1) element
	  // Fill in the value when there is upward move, namely --m 
      int itm = m + mid; int itn = n; 
      p[mid][itm] = itn; // The beginning element
      
	  while (itm != mid && itn != 0){		
		  // 1. Don't need to check whether element (itm, itn) is within the bound
		  // 2. Diagonal moves are always OK
		  
		  int am1_n, am_n1;
		  // Upper element -> Compare with upper bound
		  //if (itn > p[u][itm-1])
		  if (!compareU(u,itm-1,itn))
			  am1_n = -2;
		  else
			  am1_n = arr[itm-1][itn]; 
		  // Left element -> Compare with lower bound
		  //if (p[l][itm] >= itn)
		  if (!compareL(l,itm, itn-1))
			  am_n1 = -2;
		  else
			  am_n1 = arr[itm][itn -1]; 
		  
		  if (am1_n == arr[itm][itn]){   // Move upward
			  itm--; 
			  p[mid][itm] = itn; 
		  }
		  else if (am_n1 == arr[itm][itn]){   // Move left
			  itn--; 
		  }
		  else {   // Move diagonally
			  itn--;
			  itm--;
			  p[mid][itm] = itn; 
		  }
	  }
	  
	  return arr[m+mid][n]; 
  }
  
  static int findshortestpath(int l, int u){
	  
	  if (l-u <= 1)
		  return 0; 
	  int mid = (l+u)/2; 
	  int s = singleshortestpath(mid, l, u);
	  return Math.max(s, Math.max(findshortestpath(l,mid), findshortestpath(mid,u))); 
	  
  }
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    //System.out.println("hello"); 
    for (int tc = 0; tc < T; tc++) {
      
      // Initialize all members
      String tempA = s.next();
      A = (tempA+tempA).toCharArray(); 
      B = s.next().toCharArray();
      m = tempA.length(); n = B.length;     // Save the real size      
      //p = new int [m+1][A.length+1];   // (m+1) * (2m + 1)
      
      //System.out.println(" " + m +" "+ n); 
      
      int inik = initCLCS(); 
      
      System.out.println(Math.max(findshortestpath(m,0), inik));
      
      //printP(); 

      
    }
  }
}

