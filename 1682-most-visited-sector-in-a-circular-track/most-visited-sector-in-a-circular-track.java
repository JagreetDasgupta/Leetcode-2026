class Solution {
  public List<Integer> mostVisited(int n, int[] rounds) {

    final int start = rounds[0];
    final int end = rounds[rounds.length - 1];
    List<Integer> ans = new ArrayList<>();
    for (int i = 1; i <= n; ++i)
      if (start <= end) {
        if (start <= i && i <= end)
          ans.add(i);
      } else { 
        if (i >= start || i <= end)
          ans.add(i);
      }
    return ans;
  }
}