import java.util.*;

class Solution {
    public boolean hasValidPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        int[][] directions = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
        };

        int[][] connections = {
            {},
            {3, 1},
            {0, 2},
            {3, 2},
            {1, 2},
            {3, 0},
            {1, 0}
        };

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];

            if (r == m - 1 && c == n - 1) {
                return true;
            }

            int street = grid[r][c];

            for (int direction : connections[street]) {
                int nr = r + directions[direction][0];
                int nc = c + directions[direction][1];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                if (visited[nr][nc]) {
                    continue;
                }

                if (isConnected(grid[nr][nc], direction)) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return false;
    }

    private boolean isConnected(int street, int direction) {
        int opposite = (direction + 2) % 4;

        int[][] connections = {
            {},
            {3, 1},
            {0, 2},
            {3, 2},
            {1, 2},
            {3, 0},
            {1, 0}
        };

        for (int d : connections[street]) {
            if (d == opposite) {
                return true;
            }
        }

        return false;
    }
}