class Solution{
    public long maximizeXorAndXor(int[]nums){
        int n=nums.length;
        long ans=0;
        int lim=1<<n;
        for(int msk=0;msk<lim;msk++){
            long nd=-1;
            long xr=0;
            boolean emp=true;
            for(int i=0;i<n;i++){
                if((msk&(1<<i))!=0){
                    xr^=nums[i];
                }else{
                    if(emp){
                        nd=nums[i];
                        emp=false;
                    }else{
                        nd&=nums[i];
                    }
                }
            }
            if(emp)nd=0;
            int inv=~(int)xr;
            int[]bas=new int[30];
            for(int i=0;i<n;i++){
                if((msk&(1<<i))!=0){
                    int val=nums[i]&inv;
                    if(val==0)continue;
                    for(int j=29;j>=0;j--){
                        if(((val>>j)&1)!=0){
                            if(bas[j]==0){
                                bas[j]=val;
                                break;
                            }
                            val^=bas[j];
                        }
                    }
                }
            }
            long mx=0;
            for(int j=29;j>=0;j--){
                if((mx^bas[j])>mx){
                    mx^=bas[j];
                }
            }
            long cur=nd+xr+2*mx;
            if(cur>ans)ans=cur;
        }
        return ans;
    }
}