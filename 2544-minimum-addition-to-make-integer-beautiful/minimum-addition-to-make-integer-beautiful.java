class Solution {
  public long makeIntegerBeautiful(long n, int target) {
    long ans = 0;
    long power = 1;
    while (sum(n) > target) {
      ans += power * (10 - n % 10);
      n = n / 10 + 1;
      power *= 10;
    }
    return ans;
  }

  private int sum(long n) {
    int res = 0;
    while (n > 0) {
      res += n % 10;
      n /= 10;
    }
    return res;
  }
}