class Solution {
  public int[] decode(int[] encoded) {

    final int n = encoded.length + 1;
    int nXors = 0;
    for (int i = 1; i <= n; i++)
      nXors ^= i;


    int runningXors = 0;
    int xors = 0; 

    for (final int encode : encoded) {
      runningXors ^= encode;
      xors ^= runningXors;
    }

    int[] ans = new int[encoded.length + 1];
    ans[0] = xors ^ nXors;

    for (int i = 0; i < encoded.length; i++)
      ans[i + 1] = ans[i] ^ encoded[i];

    return ans;
  }
}