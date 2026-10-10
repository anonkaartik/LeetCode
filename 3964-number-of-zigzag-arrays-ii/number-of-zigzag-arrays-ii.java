class Solution {
    private static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        int size = 2 * m;

        long[][] matrix = new long[size][size];
        long[] dp = new long[size];

        for (int i = 0; i < m; i++) {
            dp[i] = i;
            dp[m + i] = m - 1 - i;

            for (int j = 0; j < i; j++) {
                matrix[i][m + j] = 1;
            }

            for (int j = i + 1; j < m; j++) {
                matrix[m + i][j] = 1;
            }
        }

        int power = n - 2;

        while (power > 0) {
            if ((power & 1) == 1) {
                dp = multiply(matrix, dp);
            }

            matrix = multiply(matrix, matrix);
            power >>= 1;
        }

        long answer = 0;

        for (long value : dp) {
            answer = (answer + value) % MOD;
        }

        return (int) answer;
    }

    private long[] multiply(long[][] matrix, long[] vector) {
        int n = vector.length;
        long[] result = new long[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i] = (result[i] + matrix[i][j] * vector[j]) % MOD;
            }
        }

        return result;
    }

    private long[][] multiply(long[][] a, long[][] b) {
        int n = a.length;
        long[][] result = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (a[i][k] == 0) {
                    continue;
                }

                for (int j = 0; j < n; j++) {
                    result[i][j] = (result[i][j]
                            + a[i][k] * b[k][j]) % MOD;
                }
            }
        }

        return result;
    }
}