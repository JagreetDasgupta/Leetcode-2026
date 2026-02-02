class Solution {
    public String baseNeg2(int n) {
        if (n == 0) {
            return "0";
        }
      
        int signMultiplier = 1;
      
        StringBuilder result = new StringBuilder();
      
        while (n != 0) {
            if (n % 2 != 0) {
                result.append(1);
                n -= signMultiplier;
            } else {
                result.append(0);
            }
          
            signMultiplier *= -1;
          
            n /= 2;
        }
      
        return result.reverse().toString();
    }
}
