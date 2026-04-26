class Solution {
public:
    int garbageCollection(vector<string>& garbage, vector<int>& travel) {
        unordered_map<char, int> lastHouseWithGarbage;
      
        int totalTime = 0;
      
        for (int houseIndex = 0; houseIndex < garbage.size(); ++houseIndex) {
            const string& currentHouseGarbage = garbage[houseIndex];
          
            totalTime += currentHouseGarbage.size();
          
            for (const char& garbageType : currentHouseGarbage) {
                lastHouseWithGarbage[garbageType] = houseIndex;
            }
        }
      
        int cumulativeTravelTime = 0;
      
        for (int segmentIndex = 1; segmentIndex <= travel.size(); ++segmentIndex) {
            cumulativeTravelTime += travel[segmentIndex - 1];
          
            for (const auto& [garbageType, lastHouseIndex] : lastHouseWithGarbage) {
                if (segmentIndex == lastHouseIndex) {
                    totalTime += cumulativeTravelTime;
                }
            }
        }
      
        return totalTime;
    }
};