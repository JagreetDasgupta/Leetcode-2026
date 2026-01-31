class Solution {
  public int numberOfWays(int n, int x, int y) {
    final int maxStages = Math.min(n, x);
    long[][] factAndInvFact = getFactAndInvFact(Math.max(n, x));
    long[] fact = factAndInvFact[0];
    long[] invFact = factAndInvFact[1];
    int[][] stirling = getStirling(n, maxStages);
    int ans = 0;

    for (int k = 1; k <= maxStages; ++k) {
      long events = nCk(x, k, fact, invFact);
      events = events * stirling[n][k] % MOD;
      events = events * fact[k] % MOD;
      events = events * modPow(y, k) % MOD;
      ans = (int) ((ans + events) % MOD);
    }

    return ans;
  }

  private static final int MOD = 1_000_000_007;

  private long[][] getFactAndInvFact(int n) {
    long[] fact = new long[n + 1];
    long[] invFact = new long[n + 1];
    long[] inv = new long[n + 1];
    fact[0] = invFact[0] = 1;
    inv[0] = inv[1] = 1;
    for (int i = 1; i <= n; ++i) {
      if (i >= 2)
        inv[i] = MOD - MOD / i * inv[MOD % i] % MOD;
      fact[i] = fact[i - 1] * i % MOD;
      invFact[i] = invFact[i - 1] * inv[i] % MOD;
    }
    return new long[][] {fact, invFact};
  }

  private int nCk(int n, int k, long[] fact, long[] invFact) {
    return (int) (fact[n] * invFact[k] % MOD * invFact[n - k] % MOD);
  }


  private int[][] getStirling(int n, int k) {
    int[][] stirling = new int[n + 1][k + 1];
    stirling[0][0] = 1;
    for (int i = 1; i <= n; ++i) {
      stirling[i][1] = 1;
      for (int j = 2; j <= Math.min(i, k); ++j)
        stirling[i][j] = (int) (((long) j * stirling[i - 1][j] + stirling[i - 1][j - 1]) % MOD);
    }
    return stirling;
  }

  private long modPow(long x, long n) {
    if (n == 0)
      return 1;
    if (n % 2 == 1)
      return x * modPow(x, n - 1) % MOD;
    return modPow(x * x % MOD, n / 2);
  }
}