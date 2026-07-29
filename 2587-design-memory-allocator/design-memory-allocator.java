class Allocator {
    
    int[]memory;
    int n;
    public Allocator(int n) {
        this.n=n;
        memory=new int[n];
        Arrays.fill(memory,-1);
    }
    
    public int allocate(int size, int mID) {
        
        int cnt=0;
        int index=-1;
        for(int i=0;i<n;++i)
        {
            if(memory[i]==-1)
            cnt++;
            else
            cnt=0;
            if(cnt==size)
            {
                index=(i-size+1);
                break;
            }
        }
        if(index==-1)return -1;

        for(int i=index;i<index+size;++i)
        {
            memory[i]=mID;
        }
        return index;
    }
    
    public int freeMemory(int mID) {
        int cnt=0;
        for(int i=0;i<n;++i)
        {
            if(memory[i]==mID){
            memory[i]=-1;
            cnt++;
            }
        }
      return cnt;
    }
}

