class Solution {
public:
    vector<int> findSubtreeSizes(vector<int>& parent, string s) {
        int n = s.size();
      
        vector<vector<int>> children(n);
        for (int i = 1; i < n; ++i) {
            children[parent[i]].push_back(i);
        }
      

        vector<vector<int>> ancestors(26);
      
        vector<int> subtreeSizes(n);
      
        auto dfs = [&](this auto&& dfs, int node, int parentNode) -> void {
            subtreeSizes[node] = 1;
          
            int charIndex = s[node] - 'a';
          
            ancestors[charIndex].push_back(node);
          
            for (int child : children[node]) {
                dfs(child, node);
            }

            int nearestAncestor = ancestors[charIndex].size() > 1 ? 
                                  ancestors[charIndex][ancestors[charIndex].size() - 2] : 
                                  parentNode;
          
            if (nearestAncestor >= 0) {
                subtreeSizes[nearestAncestor] += subtreeSizes[node];
            }
          
            ancestors[charIndex].pop_back();
        };
      
        dfs(0, -1);
      
        return subtreeSizes;
    }
};
