class SegmentTree {
 public:
  explicit SegmentTree(int n, int kInf) : kInf(kInf), n(n), tree(4 * n, kInf) {}

  void update(int i, int val) {
    update(0, 0, n - 1, i, val);
  }

  int query(int i, int j) const {
    return query(0, 0, n - 1, i, j);
  }

 private:
  const int kInf;   
  const int n;      
  vector<int> tree;  

  void update(int treeIndex, int lo, int hi, int i, int val) {
    if (lo == hi) {
      tree[treeIndex] = val;
      return;
    }
    const int mid = (lo + hi) / 2;
    if (i <= mid)
      update(2 * treeIndex + 1, lo, mid, i, val);
    else
      update(2 * treeIndex + 2, mid + 1, hi, i, val);
    tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
  }

  int query(int treeIndex, int lo, int hi, int i, int j) const {
    if (i <= lo && hi <= j)  
      return tree[treeIndex];
    if (j < lo || hi < i)  
      return kInf;
    const int mid = (lo + hi) / 2;
    return merge(query(treeIndex * 2 + 1, lo, mid, i, j),
                 query(treeIndex * 2 + 2, mid + 1, hi, i, j));
  }

  int merge(int left, int right) const {
    return max(left, right);
  }
};

class Solution {
 public:
  long long maxRectangleArea(vector<int>& xCoord, vector<int>& yCoord) {
    long ans = -1;
    const vector<pair<int, int>> points = getSortedPoints(xCoord, yCoord);
    const vector<int> ys = getUniqueAndSortedYs(yCoord);
    SegmentTree tree(ys.size(), -1);
    unordered_map<int, int> yToIndex;
    unordered_map<int, int> yToX;

    for (int i = 0; i < ys.size(); ++i)
      yToIndex[ys[i]] = i;

    auto [prevX, prevY] = points[0];
    for (int i = 1; i < points.size(); ++i) {
      const auto [x, y] = points[i];
      if (yToX.contains(prevY) && yToX.contains(y)) {
        const int xLeft = yToX[y];
        if (prevX == x && yToX[prevY] == xLeft &&
            xLeft > tree.query(yToIndex[prevY] + 1, yToIndex[y] - 1))
          ans = max(ans, static_cast<long>(y - prevY) * (x - xLeft));
      }
      yToX[prevY] = prevX;
      tree.update(yToIndex[prevY], prevX);
      prevX = x;
      prevY = y;
    }

    return ans;
  }

 private:
  vector<pair<int, int>> getSortedPoints(const vector<int>& xCoord,
                                         const vector<int>& yCoord) {
    vector<pair<int, int>> points;
    for (int i = 0; i < xCoord.size(); ++i)
      points.emplace_back(xCoord[i], yCoord[i]);
    ranges::sort(points);
    return points;
  }

  vector<int> getUniqueAndSortedYs(const vector<int>& yCoord) {
    vector<int> ys = yCoord;
    ranges::sort(ys);
    ys.erase(unique(ys.begin(), ys.end()), ys.end());
    return ys;
  }
};