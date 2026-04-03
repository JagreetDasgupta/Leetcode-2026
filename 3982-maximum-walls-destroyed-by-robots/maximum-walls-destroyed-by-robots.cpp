class Solution {
public:
    int maxWalls(vector<int>& robots, vector<int>& distance, vector<int>& walls) {
        int n = robots.size();
      
        vector<pair<int, int>> robotInfo(n);
        for (int i = 0; i < n; i++) {
            robotInfo[i] = {robots[i], distance[i]};
        }
      
        ranges::sort(robotInfo, {}, &pair<int, int>::first);
      
        ranges::sort(walls);
 
        vector<vector<int>> dp(n, vector<int>(2, -1));
      
        auto solve = [&](this auto&& solve, int robotIdx, int prevDirection) -> int {
            if (robotIdx < 0) {
                return 0;
            }
          
            if (dp[robotIdx][prevDirection] != -1) {
                return dp[robotIdx][prevDirection];
            }
          
            int maxDestroyed = 0;
          

            int leftBound = robotInfo[robotIdx].first - robotInfo[robotIdx].second;
          
            if (robotIdx > 0) {
                leftBound = max(leftBound, robotInfo[robotIdx - 1].first + 1);
            }
          
            int leftWallIdx = ranges::lower_bound(walls, leftBound) - walls.begin();
            int rightWallIdx = ranges::lower_bound(walls, robotInfo[robotIdx].first + 1) - walls.begin();
            int wallsDestroyedLeft = rightWallIdx - leftWallIdx;
          

            maxDestroyed = solve(robotIdx - 1, 0) + wallsDestroyedLeft;

            int rightBound = robotInfo[robotIdx].first + robotInfo[robotIdx].second;
          
            if (robotIdx + 1 < n) {
                if (prevDirection == 0) {
                    rightBound = min(rightBound, robotInfo[robotIdx + 1].first - robotInfo[robotIdx + 1].second - 1);
                } else {
                    rightBound = min(rightBound, robotInfo[robotIdx + 1].first - 1);
                }
            }
          
            leftWallIdx = ranges::lower_bound(walls, robotInfo[robotIdx].first) - walls.begin();
            rightWallIdx = ranges::lower_bound(walls, rightBound + 1) - walls.begin();
            int wallsDestroyedRight = rightWallIdx - leftWallIdx;
          
            maxDestroyed = max(maxDestroyed, solve(robotIdx - 1, 1) + wallsDestroyedRight);
          
            dp[robotIdx][prevDirection] = maxDestroyed;
            return maxDestroyed;
        };
      
        return solve(n - 1, 1);
    }
};
