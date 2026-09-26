class Solution {
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][k + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int c = 0; c <= k; c++) {
                    dp[i][j][c] = -1;
                }
            }
        }

        dp[0][0][0] = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int cost = 0; cost <= k; cost++) {
                    if (dp[i][j][cost] == -1) {
                        continue;
                    }

                    if (i + 1 < m) {
                        int value = grid[i + 1][j];
                        int newCost = cost + (value == 0 ? 0 : 1);

                        if (newCost <= k) {
                            dp[i + 1][j][newCost] = Math.max(
                                dp[i + 1][j][newCost],
                                dp[i][j][cost] + value
                            );
                        }
                    }

                    if (j + 1 < n) {
                        int value = grid[i][j + 1];
                        int newCost = cost + (value == 0 ? 0 : 1);

                        if (newCost <= k) {
                            dp[i][j + 1][newCost] = Math.max(
                                dp[i][j + 1][newCost],
                                dp[i][j][cost] + value
                            );
                        }
                    }
                }
            }
        }

        int answer = -1;

        for (int cost = 0; cost <= k; cost++) {
            answer = Math.max(answer, dp[m - 1][n - 1][cost]);
        }

        return answer;
    }
}