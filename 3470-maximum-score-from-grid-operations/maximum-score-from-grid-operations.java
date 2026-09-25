import java.util.*;

class Solution {
    public long maximumScore(int[][] grid) {
        int n = grid.length;
        long INF = Long.MIN_VALUE / 2;

        long[][] prefix = new long[n][n + 1];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                prefix[j][i + 1] = prefix[j][i] + grid[i][j];
            }
        }

        long[][] dp = new long[n + 1][n + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        for (int h = 0; h <= n; h++) {
            dp[h][0] = 0;
        }

        for (int col = 0; col < n - 1; col++) {
            long[][] next = new long[n + 1][n + 1];

            for (long[] row : next) {
                Arrays.fill(row, INF);
            }

            for (int current = 0; current <= n; current++) {
                long[] pre = new long[n + 1];
                long[] suf = new long[n + 2];

                Arrays.fill(pre, INF);
                Arrays.fill(suf, INF);

                pre[0] = dp[current][0];

                for (int previous = 1; previous <= n; previous++) {
                    pre[previous] = Math.max(
                        pre[previous - 1],
                        dp[current][previous]
                    );
                }

                for (int previous = n; previous >= 0; previous--) {
                    long value = dp[current][previous];

                    if (value != INF) {
                        value += Math.max(
                            0,
                            prefix[col][previous] - prefix[col][current]
                        );
                    }

                    suf[previous] = Math.max(
                        suf[previous + 1],
                        value
                    );
                }

                for (int nextHeight = 0; nextHeight <= n; nextHeight++) {
                    long add = Math.max(
                        0,
                        prefix[col][nextHeight] - prefix[col][current]
                    );

                    long option1 = pre[nextHeight] == INF
                        ? INF
                        : pre[nextHeight] + add;

                    long option2 = suf[nextHeight + 1];

                    next[nextHeight][current] =
                        Math.max(option1, option2);
                }
            }

            dp = next;
        }

        long answer = 0;

        for (int current = 0; current <= n; current++) {
            for (int previous = 0; previous <= n; previous++) {
                if (dp[current][previous] != INF) {
                    long score = dp[current][previous]
                        + Math.max(
                            0,
                            prefix[n - 1][previous]
                                - prefix[n - 1][current]
                        );

                    answer = Math.max(answer, score);
                }
            }
        }

        return answer;
    }
}