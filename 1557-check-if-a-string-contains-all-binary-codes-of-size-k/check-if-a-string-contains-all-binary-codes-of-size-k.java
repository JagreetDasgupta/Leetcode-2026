class Solution {
    public boolean hasAllCodes(String s, int k) {
        int stringLength = s.length();
        int totalPossibleCodes = 1 << k;
        if (stringLength - k + 1 < totalPossibleCodes) {
            return false;
        }
        Set<String> uniqueSubstrings = new HashSet<>();
        for (int i = 0; i <= stringLength - k; i++) {
            String currentSubstring = s.substring(i, i + k);
            uniqueSubstrings.add(currentSubstring);
        }
        return uniqueSubstrings.size() == totalPossibleCodes;
    }
}