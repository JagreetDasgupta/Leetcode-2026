import java.util.*;

class Solution {
    public int maxRectangleArea(int[][] points) {
        int n = points.length;
        Set<String> pointSet = new HashSet<>();

        for (int[] point : points) {
            pointSet.add(point[0] + "," + point[1]);
        }

        int maxArea = -1;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];

                if (x1 != x2 && y1 != y2) {
                    String corner1 = x1 + "," + y2;
                    String corner2 = x2 + "," + y1;

                    if (pointSet.contains(corner1) && pointSet.contains(corner2)) {
                        if (isRectangleValid(points, x1, y1, x2, y2)) {
                            int area = Math.abs(x1 - x2) * Math.abs(y1 - y2);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }

        return maxArea;
    }

    private boolean isRectangleValid(int[][] points, int x1, int y1, int x2, int y2) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        for (int[] point : points) {
            int px = point[0], py = point[1];
            if ((px > minX && px < maxX && py > minY && py < maxY) ||
                ((px == minX || px == maxX) && (py > minY && py < maxY)) ||
                ((py == minY || py == maxY) && (px > minX && px < maxX))) {
                return false;
            }
        }

        return true;
    }
}