class ExamTracker {
public:
    ExamTracker() {
        times.push_back(0);
        pre.push_back(0LL);
    }

    void record(int time, int score) {
        times.push_back(time);
        pre.push_back(pre.back() + score);
    }

    long long totalScore(int startTime, int endTime) {
        int l = lower_bound(times.begin(), times.end(), startTime) - times.begin() - 1;
        int r = lower_bound(times.begin(), times.end(), endTime + 1) - times.begin() - 1;
        return pre[r] - pre[l];
    }

private:
    vector<int> times;
    vector<long long> pre;
};

