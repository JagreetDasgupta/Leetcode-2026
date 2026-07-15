class Solution {
 public:
  int countQuadruplets(vector<int>& nums) {
    const int n = nums.size();
    int ans = 0;
    unordered_map<int, int> count;

 
    for (int b = n - 1; b > 0; --b) {  
      for (int a = b - 1; a >= 0; --a)
        if (const auto it = count.find(nums[a] + nums[b]); it != count.cend())
          ans += it->second;
      for (int d = n - 1; d > b; --d)
        ++count[nums[d] - nums[b]]; 
    }

    return ans;
  }
};