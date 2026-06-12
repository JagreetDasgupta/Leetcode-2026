class Solution {
public:
    int minSwaps(vector<int>& nums) {
        int totalOnes = accumulate(nums.begin(), nums.end(), 0);
        int arraySize = nums.size();
      
        int onesInWindow = accumulate(nums.begin(), nums.begin() + totalOnes, 0);
        int maxOnesInWindow = onesInWindow;
      

        for (int i = totalOnes; i < arraySize + totalOnes; ++i) {

            onesInWindow += nums[i % arraySize] - nums[(i - totalOnes + arraySize) % arraySize];
          
            maxOnesInWindow = max(maxOnesInWindow, onesInWindow);
        }
      

        return totalOnes - maxOnesInWindow;
    }
};