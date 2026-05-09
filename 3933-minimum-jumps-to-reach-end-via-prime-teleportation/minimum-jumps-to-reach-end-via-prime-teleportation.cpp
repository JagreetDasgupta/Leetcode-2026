#include <algorithm>
#include <queue>
#include <unordered_map>
#include <vector>
using namespace std;

class Solution {
public:
    int minJumps(vector<int>& nums) {
        int n = nums.size();
        if (n == 1) {
            return 0;
        }

        int maxValue = *max_element(nums.begin(), nums.end());
        vector<int> spf = buildSmallestPrimeFactor(maxValue);
        unordered_map<int, vector<int>> edges;

        for (int i = 0; i < n; i++) {
            int value = nums[i];

            while (value > 1) {
                int prime = spf[value];
                edges[prime].push_back(i);

                while (value % prime == 0) {
                    value /= prime;
                }
            }
        }

        vector<bool> seen(n, false);
        queue<int> q;
        seen[0] = true;
        q.push(0);
        int jumps = 0;

        while (!q.empty()) {
            int size = q.size();

            while (size-- > 0) {
                int index = q.front();
                q.pop();

                if (index == n - 1) {
                    return jumps;
                }

                if (index > 0 && !seen[index - 1]) {
                    seen[index - 1] = true;
                    q.push(index - 1);
                }

                if (index + 1 < n && !seen[index + 1]) {
                    seen[index + 1] = true;
                    q.push(index + 1);
                }

                int value = nums[index];
                if (value >= 2 && spf[value] == value) {
                    auto it = edges.find(value);

                    if (it != edges.end()) {
                        for (int next : it->second) {
                            if (!seen[next]) {
                                seen[next] = true;
                                q.push(next);
                            }
                        }

                        edges.erase(it);
                    }
                }
            }

            jumps++;
        }

        return -1;
    }

private:
    vector<int> buildSmallestPrimeFactor(int maxValue) {
        vector<int> spf(maxValue + 1, 0);

        for (int i = 2; i <= maxValue; i++) {
            if (spf[i] != 0) {
                continue;
            }

            spf[i] = i;

            if (1LL * i * i > maxValue) {
                continue;
            }

            for (long long j = 1LL * i * i; j <= maxValue; j += i) {
                if (spf[j] == 0) {
                    spf[j] = i;
                }
            }
        }

        return spf;
    }
};