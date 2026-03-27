class Solution {
  public int longestSubsequence(int[] nums) {
    final int mx = Arrays.stream(nums).max().getAsInt();

    int[][] dp = new int[mx + 1][mx + 1];

    for (final int num : nums) {
      for (int prev = 1; prev <= mx; ++prev) {
        final int diff = Math.abs(num - prev);
        dp[num][diff] = Math.max(dp[num][diff], dp[prev][diff] + 1);
      }
      for (int j = mx - 1; j >= 0; --j)
        dp[num][j] = Math.max(dp[num][j], dp[num][j + 1]);
    }

    return Arrays.stream(dp).mapToInt(row -> row[0]).max().getAsInt();
  }
}