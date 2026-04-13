class Solution {
public:

    long long minimumTime(vector<int>& d, vector<int>& r) {

        
        long long lcm_val = lcm((long long)r[0], (long long)r[1]);
        long long left = 0, right = LLONG_MAX, ans = LLONG_MAX;

        while(left < right){
            long long mid = (left+right)/2;
          
            long long slots_1 = mid - mid/r[0], slots_2 = mid - mid/r[1];
            long long tot_slots = mid - mid/lcm_val;
            if(slots_1 >= d[0] && slots_2 >= d[1] && tot_slots >= (d[0]+d[1])){
                ans = mid;
                right = mid;
            }
            else
                left = mid + 1;
        }
        return ans;
    }
};