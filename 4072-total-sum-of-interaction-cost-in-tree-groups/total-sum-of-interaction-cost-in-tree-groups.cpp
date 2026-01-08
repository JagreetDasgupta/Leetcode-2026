class Solution {
public:
    long long interactionCosts(int n, vector<vector<int>>& edges, vector<int>& group) {
        vector<vector<int>> adj(n);
        for (const auto& e : edges) {
            adj[e[0]].emplace_back(e[1]);
            adj[e[1]].emplace_back(e[0]);
        } 
        unordered_map<int, int64_t> total;
        for (const auto& x : group) {
            ++total[x];
        }
        int64_t result = 0;
        const auto& bfs = [&]() {
            vector<int> order = {0};
            vector<int> parent(n, -1);
            for (int i = 0; i < size(adj); ++i) {
                const auto u = order[i];
                for (const auto& v : adj[u]) {
                    if (v == parent[u]) {
                        continue;
                    }
                    parent[v] = u;
                    order.emplace_back(v);
                }
            }
            return pair(order, parent);
        };
        
        const auto& [order, parent] = bfs();
        vector<unordered_map<int, int64_t>> cnt(n);
        for (int i = size(order) - 1; i >= 0; --i) {
            const auto& u = order[i];
            ++cnt[u][group[u]];
            for (const auto& v : adj[u]) {
                if (u != parent[v]) {
                    continue;
                }
                for (const auto& [k, c] : cnt[v]) {
                    result += c * (total[k] - c);
                }
                if (size(cnt[v]) > size(cnt[u])) {
                    swap(cnt[u], cnt[v]);
                }
                for (const auto& [k, c] : cnt[v]) {
                    cnt[u][k] += c;
                }
                cnt[v].clear();
            }
        }
        return result;
    }
};