class Solution {
public:
    int findShortestCycle(int n, vector<vector<int>>& edges) {
        vector<vector<int>> adjacencyList(n);
        for (auto& edge : edges) {
            int nodeU = edge[0];
            int nodeV = edge[1];
            adjacencyList[nodeU].push_back(nodeV);
            adjacencyList[nodeV].push_back(nodeU);
        }
      
        const int INF = 1 << 30;  
        auto findShortestPathExcludingEdge = [&](int startNode, int endNode) -> int {
            int distances[n];
            fill(distances, distances + n, INF);
            distances[startNode] = 0;
          
            queue<int> bfsQueue;
            bfsQueue.push(startNode);
          
            while (!bfsQueue.empty()) {
                int currentNode = bfsQueue.front();
                bfsQueue.pop();
              
                for (int neighbor : adjacencyList[currentNode]) {
                    bool isExcludedEdge = (currentNode == startNode && neighbor == endNode) || 
                                          (currentNode == endNode && neighbor == startNode);
                  
                    if (isExcludedEdge || distances[neighbor] != INF) {
                        continue;
                    }
                  
                    distances[neighbor] = distances[currentNode] + 1;
                    bfsQueue.push(neighbor);
                }
            }
         
            return distances[endNode] + 1;
        };
      
        int shortestCycleLength = INF;
        for (auto& edge : edges) {
            int nodeU = edge[0];
            int nodeV = edge[1];
            shortestCycleLength = min(shortestCycleLength, findShortestPathExcludingEdge(nodeU, nodeV));
        }
      
        return shortestCycleLength < INF ? shortestCycleLength : -1;
    }
};