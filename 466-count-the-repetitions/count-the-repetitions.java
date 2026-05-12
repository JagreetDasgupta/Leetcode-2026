class Solution {
  public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
    record Record(int count, int nextIndex) {}

    List<Record> records = new ArrayList<>();

    for (int i = 0; i < s2.length(); ++i) {
      int count = 0;
      int nextIndex = i;
      for (final char c : s1.toCharArray())
        if (s2.charAt(nextIndex) == c)
          if (++nextIndex == s2.length()) { 
            ++count;
            nextIndex = 0;
          }
      records.add(new Record(count, nextIndex));
    }

    int matches = 0; 
    int i = 0;      
    while (n1-- > 0) {
      matches += records.get(i).count;
      i = records.get(i).nextIndex;
    }

    return matches / n2;
  }
}