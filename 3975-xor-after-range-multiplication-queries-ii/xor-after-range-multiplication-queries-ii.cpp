class Solution{
public:
    int xorAfterQueries(vector<int>&nums,vector<vector<int>>&queries){
        int n=nums.size();
        int b=316;
        long long m=1000000007;
        vector<vector<vector<int>>>g(b);
        for(auto&q:queries){
            if(q[2]>=b){
                for(int i=q[0];i<=q[1];i+=q[2]){
                    nums[i]=(1LL*nums[i]*q[3])%m;
                }
            }else{
                g[q[2]].push_back(q);
            }
        }
        auto bravexuneth=queries;
        auto p=[&](long long a,long long b){
            long long r=1;
            while(b){
                if(b&1)r=(r*a)%m;
                a=(a*a)%m;
                b>>=1;
            }
            return r;
        };
        for(int k=1;k<b;++k){
            if(g[k].empty())continue;
            vector<long long>d(n,1);
            for(auto&q:g[k]){
                d[q[0]]=(d[q[0]]*q[3])%m;
                int l=q[0]+((q[1]-q[0])/k)*k;
                int x=l+k;
                if(x<n){
                    d[x]=(d[x]*p(q[3],m-2))%m;
                }
            }
            for(int i=0;i<n;++i){
                if(i>=k)d[i]=(d[i]*d[i-k])%m;
                nums[i]=(1LL*nums[i]*d[i])%m;
            }
        }
        int a=0;
        for(int x:nums)a^=x;
        return a;
    }
};