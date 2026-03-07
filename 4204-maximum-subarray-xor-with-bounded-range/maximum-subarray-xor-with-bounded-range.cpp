class Solution {
public:
    int maxXor(vector<int>& nums, int k) {
        vector<int> lookup(size(nums), -1);
        deque<int> max_dq, min_dq;
        for (int right = 0, left = 0; right < size(nums); ++right) {
            while (!empty(max_dq) && nums[max_dq.back()] <= nums[right]) {
                max_dq.pop_back();
            }
            max_dq.emplace_back(right);
            while (!empty(min_dq) && nums[min_dq.back()] >= nums[right]) {
                min_dq.pop_back();
            }
            min_dq.emplace_back(right);
            while (nums[max_dq[0]] - nums[min_dq[0]] > k) {
                if (!empty(max_dq) && max_dq[0] == left) {
                    max_dq.pop_front();
                }
                if (!empty(min_dq) && min_dq[0] == left) {
                    min_dq.pop_front();
                }
                ++left;
            }
            lookup[right] = left;
        }
        int result = 0;
        const uint32_t mx = max(ranges::max(nums), 1);
        for (int i = bit_width(mx) - 1; i >= 0; --i) {
            unordered_map<int, int> lookup2;
            lookup2[0] = 0;
            for (int right = 0, prefix = 0; right < size(nums); ++right) {
                prefix ^= nums[right] >> i;
                if (lookup2.count(((result >> i) | 1) ^ prefix) && lookup2[((result >> i) | 1) ^ prefix] >= lookup[right]) {
                    result |= 1 << i;
                    break;
                }
                lookup2[prefix] = right + 1;
            }
        }
        return result;
    }
};