class Solution {
    public long appealSum(String s) {
        int n = s.length();
        long ans = 0;
        int[] lst = new int[26];
        Arrays.fill(lst, -1);
        
        for(int i = 0;i<n;i++) {
            int ind = s.charAt(i) - 'a';
            int prev = lst[ind];

            ans += (long) (i - prev) * (n - i);

            lst[ind] = i;
        }
        return ans;
    }
}