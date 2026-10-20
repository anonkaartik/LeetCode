class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        java.util.Arrays.sort(sorted);

        int answer = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                int[] pattern = new int[k];
                int[] lps = new int[k];

                for (int i = 0; i < k; i++) {
                    pattern[i] = nums[start + i];
                }

                for (int i = 1, len = 0; i < k;) {
                    if (pattern[i] == pattern[len]) {
                        lps[i++] = ++len;
                    } else if (len > 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i++] = 0;
                    }
                }

                int matched = 0;
                boolean found = false;

                for (int i = 0; i < 2 * k - 1; i++) {
                    int value = sorted[start + i % k];

                    while (matched > 0 && value != pattern[matched]) {
                        matched = lps[matched - 1];
                    }

                    if (value == pattern[matched]) {
                        matched++;
                    }

                    if (matched == k) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    valid = false;
                }
            }

            if (valid) {
                answer += k;
            }
        }

        return answer;
    }
}