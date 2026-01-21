class Solution {
 public:
  string generateString(string str1, string str2) {
    const int n = str1.length();
    const int m = str2.length();
    const int sz = n + m - 1;
    string ans(sz, '\0');
    vector<bool> modifiable(sz, true);

    for (int i = 0; i < n; ++i)
      if (str1[i] == 'T')
        for (int j = 0; j < m; ++j) {
          const int pos = i + j;
          if (ans[pos] && ans[pos] != str2[j])
            return "";
          ans[pos] = str2[j];
          modifiable[pos] = false;
        }

    for (int i = 0; i < sz; ++i)
      if (ans[i] == '\0')
        ans[i] = 'a';

    for (int i = 0; i < n; ++i)
      if (str1[i] == 'F' && match(ans, i, str2)) {
        const int modifiablePos = lastModifiablePosition(i, m, modifiable);
        if (modifiablePos == -1)
          return "";
        ans[modifiablePos] = 'b';
        modifiable[modifiablePos] = false;
      }

    return ans;
  }

 private:
  bool match(string& ans, int i, string& s) {
    for (int j = 0; j < s.length(); ++j)
      if (ans[i + j] != s[j])
        return false;
    return true;
  }

  int lastModifiablePosition(int i, int m, vector<bool>& modifiable) {
    int modifiablePos = -1;
    for (int j = 0; j < m; ++j) {
      const int pos = i + j;
      if (modifiable[pos])
        modifiablePos = pos;
    }
    return modifiablePos;
  }
};