class Solution {
public:
    int removeCoveredIntervals(vector<vector<int>>& intervals) {
   
        sort(intervals.begin(), intervals.end(), [](const vector<int>& a, const vector<int>& b) {
            if (a[0] == b[0]) {
                return a[1] > b[1];  
            }
            return a[0] < b[0];  
        });
      
        int nonCoveredCount = 0;
        int previousEnd = INT_MIN; 
        for (const auto& interval : intervals) {
            int currentEnd = interval[1];
          
           
            if (currentEnd > previousEnd) {
                nonCoveredCount++;
                previousEnd = currentEnd;  
            }
        }
      
        return nonCoveredCount;
    }
};