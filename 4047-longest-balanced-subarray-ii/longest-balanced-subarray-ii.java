import java.util.HashMap;
import java.util.Map;

class Solution {
    
    static class SegmentTree {
        int n;
        int[] min;
        int[] max;
        int[] lazy;

        public SegmentTree(int n) {
            this.n = n;
            this.min = new int[4 * n];
            this.max = new int[4 * n];
            this.lazy = new int[4 * n];
        }

        private void push(int node, int start, int end) {
            if (lazy[node] != 0) {
                if (start != end) {
                    lazy[2 * node] += lazy[node];
                    min[2 * node] += lazy[node];
                    max[2 * node] += lazy[node];

                    lazy[2 * node + 1] += lazy[node];
                    min[2 * node + 1] += lazy[node];
                    max[2 * node + 1] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        public void update(int node, int start, int end, int l, int r, int val) {
            if (l > end || r < start) return;
            if (l <= start && end <= r) {
                min[node] += val;
                max[node] += val;
                lazy[node] += val;
                return;
            }
            push(node, start, end);
            int mid = (start + end) / 2;
            update(2 * node, start, mid, l, r, val);
            update(2 * node + 1, mid + 1, end, l, r, val);
            min[node] = Math.min(min[2 * node], min[2 * node + 1]);
            max[node] = Math.max(max[2 * node], max[2 * node + 1]);
        }

        public int findFirstZero(int node, int start, int end) {

            if (min[node] > 0 || max[node] < 0) {
                return -1;
            }
            if (start == end) {
                return start;
            }
            push(node, start, end);
            int mid = (start + end) / 2;
            
            int res = findFirstZero(2 * node, start, mid);
            if (res != -1) return res;
            
            return findFirstZero(2 * node + 1, mid + 1, end);
        }
    }

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(n);
        Map<Integer, Integer> lastPos = new HashMap<>();
        int maxLen = 0;

        for (int r = 0; r < n; r++) {
            int num = nums[r];
            int prev = lastPos.getOrDefault(num, -1);
            
            int val = (num % 2 != 0) ? -1 : 1;
            

            st.update(1, 0, n - 1, prev + 1, r, val);
            
            int l = st.findFirstZero(1, 0, n - 1);
            
            if (l != -1 && l <= r) {
                maxLen = Math.max(maxLen, r - l + 1);
            }
            
            lastPos.put(num, r);
        }

        return maxLen;
    }
}