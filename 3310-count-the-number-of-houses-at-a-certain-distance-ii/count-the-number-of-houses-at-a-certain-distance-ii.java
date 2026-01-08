class Solution {
  public long[] countOfPairs(int n, int x, int y) {
    if (x > y) {
      final int temp = x;
      x = y;
      y = temp;
    }

    final int ringLen = y - x + 1;
    final int leftLineLen = x - 1;
    final int rightLineLen = n - y;

    long[] ans = new long[n];
    ans = addVectors(ans, bothInRing(n, ringLen));
    ans = addVectors(ans, bothInTheSameLine(n, leftLineLen));
    ans = addVectors(ans, bothInTheSameLine(n, rightLineLen));
    ans = addVectors(ans, lineToRing(n, leftLineLen, ringLen));
    ans = addVectors(ans, lineToRing(n, rightLineLen, ringLen));
    ans = addVectors(ans, lineToLine(n, x, y, leftLineLen, rightLineLen));
    for (int i = 0; i < ans.length; ++i)
      ans[i] *= 2;
    return ans;
  }


  private long[] bothInRing(int n, int ringLen) {
    long[] res = new long[n];
    for (int k = 1; k <= (ringLen - 1) / 2; ++k)
      res[k - 1] += ringLen;
    if (ringLen % 2 == 0)
      res[ringLen / 2 - 1] += ringLen / 2;
    return res;
  }


  private long[] bothInTheSameLine(int n, int lineLen) {
    long[] res = new long[n];
    for (int k = 1; k <= lineLen; ++k)
      res[k - 1] += lineLen - k;
    return res;
  }


  private long[] lineToRing(int n, int lineLen, int ringLen) {
    long[] res = new long[n];
    for (int k = 1; k <= lineLen + ringLen; ++k) {

      final int maxInRingLen = Math.min(k - 1, ringLen / 2);
      final int minInRingLen = Math.max(0, k - lineLen);
      if (minInRingLen <= maxInRingLen) {

        res[k - 1] += (maxInRingLen - minInRingLen + 1) * 2;
        if (minInRingLen == 0)
          res[k - 1] -= 1;
        if (maxInRingLen * 2 == ringLen)

          res[k - 1] -= 1;
      }
    }
    return res;
  }

  private long[] lineToLine(int n, int x, int y, int leftLineLen, int rightLineLen) {
    long[] res = new long[n];
    for (int k = 1; k <= leftLineLen + rightLineLen + 2; ++k) {

      final int maxInLeft = Math.min(leftLineLen, k - 1 - (x < y ? 1 : 0));
      final int minInLeft = Math.max(1, k - rightLineLen - (x < y ? 1 : 0));
      if (minInLeft <= maxInLeft)
        res[k - 1] += maxInLeft - minInLeft + 1;
    }
    return res;
  }

  private long[] addVectors(long[] a, long[] b) {
    for (int i = 0; i < a.length; ++i)
      a[i] += b[i];
    return a;
  }
}