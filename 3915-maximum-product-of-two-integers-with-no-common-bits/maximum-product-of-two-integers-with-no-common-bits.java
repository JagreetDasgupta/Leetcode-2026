class Solution {
    public long maxProduct(int[] nums) {
        int max=0;
        for(int val:nums)max=Math.max(max,val);
        int totalBit=(int)(Math.log(max)/Math.log(2) + 1);
        int maxMask=(1<<totalBit)-1;

        int[] dp=new int[maxMask+1];
        for(int val:nums)dp[val]=val;
        
        for(int i=0; i<totalBit; i++){
            for(int val=1; val<=maxMask; val++){
               
                if((val & (1<<i) )!=0){
                    int offBitNumber=(val ^ (1<<i));
                    dp[val]=Math.max(dp[val],dp[offBitNumber]);
                }
            }
        }
        long ans=0;
        for(int val:nums){
            
            int comp=(maxMask ^ val);
            ans=Math.max(ans,(long)val*dp[comp]);
        }
        return ans;
    }
} 
