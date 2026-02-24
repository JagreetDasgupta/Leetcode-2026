class DSU {
public:
    vector<int> parent;
    vector<int> component_and;

    DSU(int n) {
        parent.resize(n);
        iota(parent.begin(), parent.end(), 0);
        component_and.assign(n, -1); 
    }

    int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]);
    }

    void unite(int i, int j, int w) {
        int root_i = find(i);
        int root_j = find(j);
        
        if (root_i != root_j) {
            parent[root_i] = root_j;
            component_and[root_j] &= component_and[root_i] & w;
        } else {
            component_and[root_i] &= w;
        }
    }
};

class Solution {
public:
    vector<int> minimumCost(int n, vector<vector<int>>& edges, vector<vector<int>>& query) {
        DSU dsu(n);
        for (auto& edge : edges) {
            dsu.unite(edge[0], edge[1], edge[2]);
        }

        vector<int> result;
        for (auto& q : query) {
            int s = q[0], t = q[1];
            if (s == t) {
                result.push_back(0);
            } else if (dsu.find(s) != dsu.find(t)) {
                result.push_back(-1);
            } else {
                result.push_back(dsu.component_and[dsu.find(s)]);
            }
        }
        return result;
    }
};