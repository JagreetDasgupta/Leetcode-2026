class Solution {
    public NestedInteger deserialize(String s) {
        if ("".equals(s) || "[]".equals(s)) {
            return new NestedInteger();
        }
      
        if (s.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(s));
        }
      
        NestedInteger result = new NestedInteger();
      
        int bracketDepth = 0;
      

        for (int currentIndex = 1, startIndex = 1; currentIndex < s.length(); currentIndex++) {
            if (bracketDepth == 0 && (s.charAt(currentIndex) == ',' || currentIndex == s.length() - 1)) {
                result.add(deserialize(s.substring(startIndex, currentIndex)));
                startIndex = currentIndex + 1;
            } 
            else if (s.charAt(currentIndex) == '[') {
                bracketDepth++;
            } 
            else if (s.charAt(currentIndex) == ']') {
                bracketDepth--;
            }
        }
      
        return result;
    }
}