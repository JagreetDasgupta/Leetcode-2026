class Solution {
  public int sumOfPower(int[] nums, int k) {
    final int MOD = 1_000_000_007;
    int[] dp = new int[k + 1];
    dp[0] = 1;

    for (final int num : nums)
      for (int i = k; i >= 0; --i)
        if (i < num)

          dp[i] = (int) ((dp[i] * 2L) % MOD);
        else

          dp[i] = (int) ((dp[i] * 2L + dp[i - num]) % MOD);

    return dp[k];
  }
}