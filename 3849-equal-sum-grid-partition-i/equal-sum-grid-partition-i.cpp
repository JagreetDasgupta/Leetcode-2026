class Solution {
public:
    bool canPartitionGrid(vector<vector<int>>& grid) {
        long long totalSum = 0;
        for (const auto& row : grid) {
            for (int value : row) {
                totalSum += value;
            }
        }
      
        if (totalSum % 2 != 0) {
            return false;
        }
      
        int rows = grid.size();
        int cols = grid[0].size();
      
        long long prefixSum = 0;
        for (int i = 0; i < rows; ++i) {
            for (int value : grid[i]) {
                prefixSum += value;
            }
          
            if (prefixSum * 2 == totalSum && i + 1 < rows) {
                return true;
            }
        }
      
        prefixSum = 0;
        for (int j = 0; j < cols; ++j) {
            for (int i = 0; i < rows; ++i) {
                prefixSum += grid[i][j];
            }
          
            if (prefixSum * 2 == totalSum && j + 1 < cols) {
                return true;
            }
        }
      
        return false;
    }
};