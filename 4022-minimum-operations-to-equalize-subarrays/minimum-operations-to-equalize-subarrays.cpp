#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<long long> minOperations(vector<int>& nums, int k, vector<vector<int>>& queries) {
        int n = (int)nums.size();
        vector<long long> rem(n);
        for(int i=0;i<n;i++) rem[i] = nums[i] % (long long)k;

        vector<int> lg(n+1);
        lg[1]=0;
        for(int i=2;i<=n;i++) lg[i]=lg[i/2]+1;
        int K = (n==0?0:lg[n]+1);
        vector<vector<long long>> stMin(K, vector<long long>(n));
        vector<vector<long long>> stMax(K, vector<long long>(n));
        for(int i=0;i<n;i++){ stMin[0][i]=rem[i]; stMax[0][i]=rem[i]; }
        for(int j=1;j<K;j++){
            int len = 1<<j;
            int half = len>>1;
            for(int i=0;i+len-1<n;i++){
                stMin[j][i] = min(stMin[j-1][i], stMin[j-1][i+half]);
                stMax[j][i] = max(stMax[j-1][i], stMax[j-1][i+half]);
            }
        }
        auto rangeMin = [&](int L,int R)->long long{
            if(L>R) return LLONG_MAX;
            int j = lg[R-L+1];
            int span=(1<<j);
            return min(stMin[j][L], stMin[j][R-span+1]);
        };
        auto rangeMax = [&](int L,int R)->long long{
            if(L>R) return LLONG_MIN;
            int j = lg[R-L+1];
            int span=(1<<j);
            return max(stMax[j][L], stMax[j][R-span+1]);
        };

        vector<long long> a(n);
        for(int i=0;i<n;i++){
            a[i] = (nums[i] - rem[i]) / (long long)k;
        }

        vector<long long> vals = a;
        sort(vals.begin(), vals.end());
        vals.erase(unique(vals.begin(), vals.end()), vals.end());
        int m = (int)vals.size();


        vector<int> Lc; Lc.reserve((n+5)*20);
        vector<int> Rc; Rc.reserve((n+5)*20);
        vector<int> Cnt; Cnt.reserve((n+5)*20);
        vector<long long> Sum; Sum.reserve((n+5)*20);

        auto newNode = [&](){ 
            Lc.push_back(0); Rc.push_back(0); Cnt.push_back(0); Sum.push_back(0);
            return (int)Lc.size()-1;
        };
        newNode(); 

        
        function<int(int,int,int,int,long long)> update = [&](int prev,int l,int r,int pos,long long val)->int{
            int cur = newNode();
            Lc[cur]=Lc[prev]; Rc[cur]=Rc[prev]; Cnt[cur]=Cnt[prev]; Sum[cur]=Sum[prev];
            if(l==r){
                Cnt[cur] = Cnt[prev] + 1;
                Sum[cur] = Sum[prev] + val; 
                return cur;
            }
            int mid=(l+r)>>1;
            if(pos<=mid){
                int newLeft = update(Lc[prev], l, mid, pos, val);
                Lc[cur] = newLeft;
            } else {
                int newRight = update(Rc[prev], mid+1, r, pos, val);
                Rc[cur] = newRight;
            }
           Cnt[cur] = Cnt[Lc[cur]] + Cnt[Rc[cur]];
            Sum[cur] = Sum[Lc[cur]] + Sum[Rc[cur]];
            return cur;
        };

        vector<int> roots(n+1);
        roots[0]=0; 
        for(int i=0;i<n;i++){
            int idx = (int)(lower_bound(vals.begin(), vals.end(), a[i]) - vals.begin());
            roots[i+1] = update(roots[i], 0, m-1, idx, a[i]);
        }

        function<int(int,int,int,int,int)> kth = [&](int rootR, int rootL, int l, int r, int k)->int{
            if(l==r) return l;
            int leftCount = Cnt[Lc[rootR]] - Cnt[Lc[rootL]];
            int mid = (l+r)>>1;
            if(k <= leftCount) return kth(Lc[rootR], Lc[rootL], l, mid, k);
            else return kth(Rc[rootR], Rc[rootL], mid+1, r, k-leftCount);
        };

        
        function<pair<int,long long>(int,int,int,int,int,int)> queryRange = [&](int rootR, int rootL, int l, int r, int ql, int qr)->pair<int,long long>{
            if(ql>qr || rootR==rootL) return {0,0LL};
            if(ql<=l && r<=qr){
                int c = Cnt[rootR] - Cnt[rootL];
                long long s = Sum[rootR] - Sum[rootL];
                return {c,s};
            }
            int mid=(l+r)>>1;
            pair<int,long long> leftPart={0,0}, rightPart={0,0};
            if(ql<=mid) leftPart = queryRange(Lc[rootR], Lc[rootL], l, mid, ql, min(qr,mid));
            if(qr>mid) rightPart = queryRange(Rc[rootR], Rc[rootL], mid+1, r, max(ql,mid+1), qr);
            return {leftPart.first + rightPart.first, leftPart.second + rightPart.second};
        };

       
        auto getCountSumUpToIndex = [&](int rootR, int rootL, int pos)->pair<int,long long>{
            if(pos < 0) return {0,0LL};
            return queryRange(rootR, rootL, 0, m-1, 0, pos);
        };

        int Q = (int)queries.size();
        vector<long long> ans(Q, -1);
        for(int qi=0; qi<Q; qi++){
            int L = queries[qi][0];
            int R = queries[qi][1];
            long long mn = rangeMin(L,R);
            long long mx = rangeMax(L,R);
            if(mn!=mx){
                ans[qi] = -1; 
                continue;
            }
            int len = R - L + 1;
            int kthPos = (len+1)/2; 
            int rootR = roots[R+1];
            int rootL = roots[L];
            int medianIdx = kth(rootR, rootL, 0, m-1, kthPos);
            long long medianVal = vals[medianIdx];
            auto leftPair = getCountSumUpToIndex(rootR, rootL, medianIdx);
            long long cntLeft = leftPair.first;
            long long sumLeft = leftPair.second;
            auto totalPair = getCountSumUpToIndex(rootR, rootL, m-1);
            long long totalCnt = totalPair.first;
            long long totalSum = totalPair.second;
            long long cntRight = totalCnt - cntLeft;
            long long sumRight = totalSum - sumLeft;
            long long ops = medianVal * cntLeft - sumLeft + (sumRight - medianVal * cntRight);
            ans[qi] = ops;
        }
        return ans;
    }
};