class Solution {
    public int minNumber(int[] nums1, int[] nums2) {
        int minResult = 100;
      
        for (int num1 : nums1) {
            for (int num2 : nums2) {
                if (num1 == num2) {
                    minResult = Math.min(minResult, num1);
                } else {
                  
                    int firstCombination = num1 * 10 + num2; 
                    int secondCombination = num2 * 10 + num1;  
                    minResult = Math.min(minResult, Math.min(firstCombination, secondCombination));
                }
            }
        }
      
        return minResult;
    }
}