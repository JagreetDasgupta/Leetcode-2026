import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minSwaps(int[] nums, int[] forbidden) {
        int n = nums.length;
        
        Map<Integer, Integer> countNums = new HashMap<>();
        Map<Integer, Integer> countForbidden = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            countNums.put(nums[i], countNums.getOrDefault(nums[i], 0) + 1);
            countForbidden.put(forbidden[i], countForbidden.getOrDefault(forbidden[i], 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : countNums.entrySet()) {
            int val = entry.getKey();
            int freqInNums = entry.getValue();
            int freqInForb = countForbidden.getOrDefault(val, 0);
            
            if (freqInNums + freqInForb > n) {
                return -1;
            }
        }
        
        int conflictCount = 0;
        int maxConflictFreq = 0;
        Map<Integer, Integer> conflictFreqMap = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            if (nums[i] == forbidden[i]) {
                conflictCount++;
                int freq = conflictFreqMap.getOrDefault(nums[i], 0) + 1;
                conflictFreqMap.put(nums[i], freq);
                maxConflictFreq = Math.max(maxConflictFreq, freq);
            }
        }
        
        return Math.max(maxConflictFreq, (conflictCount + 1) / 2);
    }
}
