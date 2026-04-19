class Solution {
public:
    bool isActiveAtTime(const string& s, const vector<int>& order, int time, long long k) {
        int n = (int)s.size();
        vector<char> starred(n, false);

        for (int i = 0; i <= time; ++i) {
            starred[order[i]] = true;
        }

        long long noStarSubstrings = 0;
        long long currentRun = 0;

        for (int i = 0; i < n; ++i) {
            if (starred[i]) {
                noStarSubstrings += currentRun * (currentRun + 1) / 2;
                currentRun = 0;
            } else {
                ++currentRun;
            }
        }

        noStarSubstrings += currentRun * (currentRun + 1) / 2;

        long long totalSubstrings = 1LL * n * (n + 1) / 2;
        long long validSubstrings = totalSubstrings - noStarSubstrings;

        return validSubstrings >= k;
    }

    int minTime(string s, vector<int>& order, int k) {
        int n = (int)s.size();
        long long totalSubstrings = 1LL * n * (n + 1) / 2;

        if (totalSubstrings < k) return -1;

        int left = 0, right = n - 1, answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (isActiveAtTime(s, order, mid, k)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }
};