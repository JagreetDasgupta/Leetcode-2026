
struct bound{
    int up, down, left, right;
    
    bound(){
        up = left = INT_MAX;
        down = right = INT_MIN;
    }
};

class Solution {
public:
    bool isPrintable(vector<vector<int>>& targetGrid) {
        unordered_map<int, bound> color2bound;
        
        int m = targetGrid.size();
        int n = targetGrid[0].size();
        
        unordered_map<int, bool> filled;
        
        for(int i = 0; i < m; ++i){
            for(int j = 0; j < n; ++j){
                int& c = targetGrid[i][j];
                bound& b = color2bound[c];
                b.left = min(b.left, j);
                b.right = max(b.right, j);
                b.up = min(b.up, i);
                b.down = max(b.down, i);
                filled[c] = false;
            }
        }
        
        int color_count = color2bound.size();
        while(color_count-- > 0){
            for(auto it = color2bound.begin(); it != color2bound.end(); ++it){
                const int& c = it->first;
                const bound& b = it->second;
                if(filled[c]) continue;
                bool fillable = true;
                for(int i = b.up; i <= b.down; ++i){
                    for(int j = b.left; j <= b.right; ++j){
                        if(targetGrid[i][j] != 0 && targetGrid[i][j] != c){
                            fillable = false;
                            break;
                        }
                    }
                    if(!fillable) break;
                }
                
                if(fillable){
                    for(int i = b.up; i <= b.down; ++i)
                        for(int j = b.left; j <= b.right; ++j)
                            targetGrid[i][j] = 0;
                    filled[c] = true;
                    break;
                }
            }
        }
        
        for(int i = 0; i < m; ++i){
            for(int j = 0; j < n; ++j){
                if(targetGrid[i][j] != 0){
                    return false;
                }
            }
        }
        
        return true;
    }
};

