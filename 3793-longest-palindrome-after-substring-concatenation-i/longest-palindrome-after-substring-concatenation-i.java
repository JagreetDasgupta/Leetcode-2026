class Solution {
  public int longestPalindrome(String s, String t) {
    final int m = s.length();
    final int n = t.length();
    int[] suffix = getPalindromeLengths(s, true);
    int[] prefix = getPalindromeLengths(t, false);
    int ans = Math.max(Arrays.stream(suffix).max().getAsInt(), 
                       Arrays.stream(prefix).max().getAsInt());

    int[][] dp = new int[m][n];

    for (int i = 0; i < m; ++i)
      for (int j = n - 1; j >= 0; --j)
        if (s.charAt(i) == t.charAt(j)) {
          dp[i][j] = 2 + (i > 0 && j < n - 1 ? dp[i - 1][j + 1] : 0);
          final int extend = Math.max(i + 1 < m ? suffix[i + 1] : 0, j > 0 ? prefix[j - 1] : 0);
          ans = Math.max(ans, dp[i][j] + extend);
        }

    return ans;
  }

  private int[] getPalindromeLengths(String s, boolean isSuffix) {
    final int n = s.length();
    boolean[][] dp = new boolean[n][n];
    int[] lengths = new int[n];
    for (int i = n - 1; i >= 0; --i)
      for (int j = i; j < n; ++j)
        if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])) {
          dp[i][j] = true;
          final int index = isSuffix ? i : j;
          lengths[index] = Math.max(lengths[index], j - i + 1);
        }
    return lengths;
  }
}