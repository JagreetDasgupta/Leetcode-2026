class Solution {
    public int rangeSum(int[] nums, int n, int left, int right) {
        int totalSubarrays = n * (n + 1) / 2;
        int[] subarraySums = new int[totalSubarrays];

        int index = 0;
        for (int startIndex = 0; startIndex < n; startIndex++) {
            int currentSum = 0;

            for (int endIndex = startIndex; endIndex < n; endIndex++) {
                currentSum += nums[endIndex];
                subarraySums[index] = currentSum;
                index++;
            }
        }

        Arrays.sort(subarraySums);

        int result = 0;
        final int MODULO = (int) 1e9 + 7;

        for (int i = left - 1; i < right; i++) {
            result = (result + subarraySums[i]) % MODULO;
        }

        return result;
    }
}