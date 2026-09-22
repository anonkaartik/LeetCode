class Solution {
    int m;
    int n;
    char[][] grid;
    boolean[][] visited;

    int[][] directions = {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };

    public boolean containsCycle(char[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j]) {
                    if (dfs(i, j, -1, -1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(int row, int col, int parentRow, int parentCol) {
        visited[row][col] = true;

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow < 0 || newRow >= m || newCol < 0 || newCol >= n) {
                continue;
            }

            if (grid[newRow][newCol] != grid[row][col]) {
                continue;
            }

            if (newRow == parentRow && newCol == parentCol) {
                continue;
            }

            if (visited[newRow][newCol]) {
                return true;
            }

            if (dfs(newRow, newCol, row, col)) {
                return true;
            }
        }

        return false;
    }
}