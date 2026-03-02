class Solution {
  public boolean[] canMakePalindromeQueries(String s, int[][] queries) {
    final int n = s.length();

    final int[] mirroredDiffs = getMirroredDiffs(s);
    final int[][] counts = getCounts(s);
    boolean[] ans = new boolean[queries.length];

    for (int i = 0; i < queries.length; i++) {
      int[] query = queries[i];
      final int a = query[0];
      final int b = query[1] + 1;
      final int c = query[2];
      final int d = query[3] + 1;
      final int ra = n - a; 
      final int rb = n - b; 
      final int rc = n - c; 
      final int rd = n - d; 
      if ((Math.min(a, rd) > 0 && mirroredDiffs[Math.min(a, rd)] > 0) ||
          (n / 2 > Math.max(b, rc) && mirroredDiffs[n / 2] - mirroredDiffs[Math.max(b, rc)] > 0) ||
          (rd > b && mirroredDiffs[rd] - mirroredDiffs[b] > 0) ||
          (a > rc && mirroredDiffs[a] - mirroredDiffs[rc] > 0)) {
        ans[i] = false;
      } else {

        int[] leftRangeCount = subtractArrays(counts[b], counts[a]);
        int[] rightRangeCount = subtractArrays(counts[d], counts[c]);
        if (a > rd)
          rightRangeCount =
              subtractArrays(rightRangeCount, subtractArrays(counts[Math.min(a, rc)], counts[rd]));
        if (rc > b)
          rightRangeCount =
              subtractArrays(rightRangeCount, subtractArrays(counts[rc], counts[Math.max(b, rd)]));
        if (c > rb)
          leftRangeCount =
              subtractArrays(leftRangeCount, subtractArrays(counts[Math.min(c, ra)], counts[rb]));
        if (ra > d)
          leftRangeCount =
              subtractArrays(leftRangeCount, subtractArrays(counts[ra], counts[Math.max(d, rb)]));
        ans[i] = Arrays.stream(leftRangeCount).allMatch(freq -> freq >= 0) &&
                 Arrays.stream(rightRangeCount).allMatch(freq -> freq >= 0) &&
                 Arrays.equals(leftRangeCount, rightRangeCount);
      }
    }

    return ans;
  }

  private int[] getMirroredDiffs(final String s) {
    int[] diffs = new int[s.length() / 2 + 1];
    for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
      diffs[i + 1] = diffs[i] + (s.charAt(i) != s.charAt(j) ? 1 : 0);
    }
    return diffs;
  }

  private int[][] getCounts(final String s) {
    int[][] counts = new int[s.length() + 1][26];
    int[] count = new int[26];
    for (int i = 0; i < s.length(); ++i) {
      ++count[s.charAt(i) - 'a'];
      System.arraycopy(count, 0, counts[i + 1], 0, 26);
    }
    return counts;
  }

  private int[] subtractArrays(int[] a, int[] b) {
    int[] res = new int[a.length];
    for (int i = 0; i < a.length; ++i)
      res[i] = a[i] - b[i];
    return res;
  }
}