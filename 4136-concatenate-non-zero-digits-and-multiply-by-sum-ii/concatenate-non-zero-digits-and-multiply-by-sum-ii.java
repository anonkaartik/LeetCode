class Solution {
    private static final long MOD = 1_000_000_007L;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();
        int q = queries.length;

        long[] prefixNumber = new long[n + 1];
        long[] prefixSum = new long[n + 1];
        int[] count = new int[n + 1];

        for (int i = 0; i < n; i++) {
            int digit = s.charAt(i) - '0';

            prefixNumber[i + 1] = prefixNumber[i];
            prefixSum[i + 1] = prefixSum[i];
            count[i + 1] = count[i];

            if (digit != 0) {
                prefixNumber[i + 1] =
                    (prefixNumber[i] * 10 + digit) % MOD;

                prefixSum[i + 1] += digit;
                count[i + 1]++;
            }
        }

        long[] pow10 = new long[n + 1];
        pow10[0] = 1;

        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        int[] answer = new int[q];

        for (int i = 0; i < q; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int digits = count[r + 1] - count[l];

            long x = prefixNumber[r + 1]
                    - prefixNumber[l] * pow10[digits] % MOD;

            x = (x + MOD) % MOD;

            long sum = prefixSum[r + 1] - prefixSum[l];

            answer[i] = (int) (x * sum % MOD);
        }

        return answer;
    }
}