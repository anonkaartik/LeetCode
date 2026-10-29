class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int[] half = new int[26];
        char middle = 0;
        int length = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            length += half[i];

            if (freq[i] % 2 == 1) {
                middle = (char) ('a' + i);
            }
        }

        if (countWays(half, k) < k) {
            return "";
        }

        StringBuilder left = new StringBuilder();
        long rank = k;

        for (int pos = 0; pos < length; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) {
                    continue;
                }

                half[c]--;

                long ways = countWays(half, k);

                if (rank > ways) {
                    rank -= ways;
                    half[c]++;
                } else {
                    left.append((char) ('a' + c));
                    break;
                }
            }
        }

        String firstHalf = left.toString();
        StringBuilder answer = new StringBuilder(firstHalf);

        if (middle != 0) {
            answer.append(middle);
        }

        answer.append(left.reverse());
        return answer.toString();
    }

    private long countWays(int[] freq, long limit) {
        int remaining = 0;

        for (int count : freq) {
            remaining += count;
        }

        long ways = 1;

        for (int count : freq) {
            if (count == 0) {
                continue;
            }

            long combinations = chooseCapped(remaining, count, limit);

            if (combinations >= limit || ways > limit / combinations) {
                return limit;
            }

            ways *= combinations;
            remaining -= count;
        }

        return ways;
    }

    private long chooseCapped(int n, int r, long limit) {
        r = Math.min(r, n - r);
        long result = 1;

        for (int i = 1; i <= r; i++) {
            result = result * (n - r + i) / i;

            if (result >= limit) {
                return limit;
            }
        }

        return result;
    }
}