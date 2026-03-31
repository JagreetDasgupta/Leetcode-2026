class Solution {

  public int sumImbalanceNumbers(int[] nums) {
    final int n = nums.length;
    int ans = 0;

    int[] left = new int[n];
    int[] right = new int[n];
    int[] numToIndex = new int[n + 2];

    Arrays.fill(numToIndex, -1);
    for (int i = 0; i < n; ++i) {
      left[i] = Math.max(numToIndex[nums[i]], numToIndex[nums[i] + 1]);
      numToIndex[nums[i]] = i;
    }

    Arrays.fill(numToIndex, n);
    for (int i = n - 1; i >= 0; --i) {
      right[i] = numToIndex[nums[i] + 1];
      numToIndex[nums[i]] = i;
    }
    for (int i = 0; i < n; ++i)
      ans += (i - left[i]) * (right[i] - i);
    return ans - n * (n + 1) / 2;
  }
}