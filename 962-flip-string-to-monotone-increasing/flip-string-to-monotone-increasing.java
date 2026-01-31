class Solution {
  public int minFlipsMonoIncr(String s) {

    int dp = 0;
    int count1 = 0;

    for (final char c : s.toCharArray())
      if (c == '0')

        dp = Math.min(dp + 1, count1);
      else
        ++count1;

    return dp;
  }
}