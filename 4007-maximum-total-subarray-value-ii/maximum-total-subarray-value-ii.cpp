class Solution {
public:
    long long maxTotalValue(vector<int>& nums, int k) {
        int n = (int)nums.size();
        if (n == 0 || k <= 0) return 0LL;

        vector<long long> a(n);
        for (int i = 0; i < n; ++i) a[i] = nums[i];

        buildLog(n);
        buildSparseTables(a);

        priority_queue<Node, vector<Node>, Cmp> pq;
        for (int l = 0; l < n; ++l) {
            long long val = rangeMax(l, n-1) - rangeMin(l, n-1);
            pq.emplace(val, l, n-1);
        }

        long long ans = 0;
        int taken = 0;
        while (taken < k && !pq.empty()) {
            Node cur = pq.top(); pq.pop();
            ans += cur.value;
            ++taken;
            if (cur.r > cur.l) {
                int nl = cur.l;
                int nr = cur.r - 1;
                long long nextVal = rangeMax(nl, nr) - rangeMin(nl, nr);
                pq.emplace(nextVal, nl, nr);
            }
        }

        return ans;
    }

private:
    struct Node {
        long long value;
        int l, r;
        Node(long long v = 0, int L = 0, int R = 0) : value(v), l(L), r(R) {}
    };
    struct Cmp {
        bool operator()(Node const &a, Node const &b) const {
            return a.value < b.value; 
        }
    };

    vector<vector<long long>> stMax;
    vector<vector<long long>> stMin;
    vector<int> lg2;

    void buildLog(int n) {
        lg2.assign(n + 1, 0);
        for (int i = 2; i <= n; ++i) lg2[i] = lg2[i/2] + 1;
    }

    void buildSparseTables(const vector<long long>& arr) {
        int n = (int)arr.size();
        int maxK = lg2[n] + 1;
        stMax.assign(maxK, vector<long long>(n));
        stMin.assign(maxK, vector<long long>(n));

        for (int i = 0; i < n; ++i) {
            stMax[0][i] = arr[i];
            stMin[0][i] = arr[i];
        }

        for (int k = 1; k < maxK; ++k) {
            int half = 1 << (k - 1);
            int len = 1 << k;
            for (int i = 0; i + len - 1 < n; ++i) {
                stMax[k][i] = max(stMax[k-1][i], stMax[k-1][i + half]);
                stMin[k][i] = min(stMin[k-1][i], stMin[k-1][i + half]);
            }
        }
    }

    long long rangeMax(int l, int r) const {
        int len = r - l + 1;
        int k = lg2[len];
        long long left = stMax[k][l];
        long long right = stMax[k][r - (1 << k) + 1];
        return max(left, right);
    }

    long long rangeMin(int l, int r) const {
        int len = r - l + 1;
        int k = lg2[len];
        long long left = stMin[k][l];
        long long right = stMin[k][r - (1 << k) + 1];
        return min(left, right);
    }
};
