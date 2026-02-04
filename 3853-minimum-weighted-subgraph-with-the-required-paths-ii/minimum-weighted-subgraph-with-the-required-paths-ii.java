class Solution {
  public int[] minimumWeight(int[][] edges, int[][] queries) {
    final int n = edges.length + 1;
    final int m = (int) Math.ceil(Math.log(n) / Math.log(2));
    int[] ans = new int[queries.length];
    List<Pair<Integer, Integer>>[] graph = new List[n];
    int[][] jump = new int[n][m];
    int[] depth = new int[n];
    int[] dist = new int[n];
    Arrays.setAll(graph, i -> new ArrayList<>());

    for (int[] edge : edges) {
      final int u = edge[0];
      final int v = edge[1];
      final int w = edge[2];
      graph[u].add(new Pair<>(v, w));
      graph[v].add(new Pair<>(u, w));
    }

    dfs(graph, 0, -1, jump, depth, dist);

    for (int j = 1; j < m; ++j)
      for (int i = 0; i < n; ++i)
        jump[i][j] = jump[jump[i][j - 1]][j - 1];

    for (int i = 0; i < queries.length; ++i) {
      final int src1 = queries[i][0];
      final int src2 = queries[i][1];
      final int dest = queries[i][2];
      ans[i] = (distance(src1, src2, jump, depth, dist) + distance(src1, dest, jump, depth, dist) +
                distance(src2, dest, jump, depth, dist)) /
               2;
    }

    return ans;
  }

  private void dfs(List<Pair<Integer, Integer>>[] graph, int u, int prev, int[][] jump, int[] depth,
                   int[] dist) {
    for (Pair<Integer, Integer> pair : graph[u]) {
      final int v = pair.getKey();
      final int w = pair.getValue();
      if (v == prev)
        continue;
      jump[v][0] = u;
      depth[v] = depth[u] + 1;
      dist[v] = dist[u] + w;
      dfs(graph, v, u, jump, depth, dist);
    }
  }

  private int getLCA(int u, int v, int[][] jump, int[] depth) {
    if (depth[u] > depth[v])
      return getLCA(v, u, jump, depth);
    for (int j = 0; j < jump[0].length; ++j)
      if ((depth[v] - depth[u] >> j & 1) == 1)
        v = jump[v][j];
    if (u == v)
      return u;
    for (int j = jump[0].length - 1; j >= 0; --j)
      if (jump[u][j] != jump[v][j]) {
        u = jump[u][j];
        v = jump[v][j];
      }
    return jump[u][0];
  }

  private int distance(int u, int v, int[][] jump, int[] depth, int[] dist) {
    final int lca = getLCA(u, v, jump, depth);
    return dist[u] + dist[v] - 2 * dist[lca];
  }
}