import java.util.*;

class Solution {
    public int findMaximumLength(int[] nums) {
        int n = nums.length;

        long[] prefix = new long[n + 1];
        for(int i=1;i<=n;i++){
            prefix[i]=prefix[i-1]+nums[i-1];
        }

        int[] dp=new int[n+1];

        int[] bestLeft=new int[n+2];

        for(int i=1;i<=n;i++){
            bestLeft[i]=Math.max(bestLeft[i],bestLeft[i-1]);

            int l=bestLeft[i];

            long target=2*prefix[i]-prefix[l];

            int r=lowerBound(prefix,target);

            dp[i]=dp[l]+1;

            if(r<=n)bestLeft[r]=i;
        }

        return dp[n];
    }

    private int lowerBound(long[] arr,long target){
        int l=0,r=arr.length;
        while(l<r){
            int mid=(l+r)/2;
            if(arr[mid]<target)l=mid+1;
            else r=mid;
        }
        return l;
    }
}