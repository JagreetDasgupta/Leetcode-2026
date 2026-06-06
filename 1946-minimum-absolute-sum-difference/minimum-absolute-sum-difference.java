class Solution {
    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        final int MOD = (int) 1e9 + 7;

        int[] sortedNums1 = nums1.clone();
        Arrays.sort(sortedNums1);
        int n = nums1.length;

        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            totalSum = (totalSum + Math.abs(nums1[i] - nums2[i])) % MOD;
        }

        int maxReduction = 0;
        for (int i = 0; i < n; i++) {
            int originalDiff = Math.abs(nums1[i] - nums2[i]);
            int minPossibleDiff = Integer.MAX_VALUE;

            int idx = findFirstGe(sortedNums1, nums2[i]);

            if (idx != -1) {
                minPossibleDiff = Math.min(minPossibleDiff, Math.abs(sortedNums1[idx] - nums2[i]));
            }

            if (idx == -1) {
                minPossibleDiff = Math.min(minPossibleDiff, Math.abs(sortedNums1[n - 1] - nums2[i]));
            } else if (idx > 0) {
                minPossibleDiff = Math.min(minPossibleDiff, Math.abs(sortedNums1[idx - 1] - nums2[i]));
            }

            maxReduction = Math.max(maxReduction, originalDiff - minPossibleDiff);
        }

        return (totalSum - maxReduction + MOD) % MOD;
    }

    private int findFirstGe(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int firstTrueIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                firstTrueIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return firstTrueIndex;
    }
}