import java.util.*;

class Solution {
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;

        int[] dp = new int[n];

        int[] parent = new int[n];

        Arrays.fill(parent, -1);

        int bestEnd = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (groups[j] == groups[i]) {
                    continue;
                }

                if (words[j].length() != words[i].length()) {
                    continue;
                }

                if (!isHammingDistanceOne(words[j], words[i])) {
                    continue;
                }

                if (dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > dp[bestEnd]) {
                bestEnd = i;
            }
        }

        List<String> result = new ArrayList<>();
        int current = bestEnd;

        while (current != -1) {
            result.add(words[current]);
            current = parent[current];
        }

        Collections.reverse(result);

        return result;
    }

    private boolean isHammingDistanceOne(String first, String second) {
        int mismatchCount = 0;

        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                mismatchCount++;

                if (mismatchCount > 1) {
                    return false;
                }
            }
        }

        return mismatchCount == 1;
    }
}