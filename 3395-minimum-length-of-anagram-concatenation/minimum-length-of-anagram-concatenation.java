class Solution {
    public int minAnagramLength(String s) {
        int n = s.length();

        for (int len = 1; len <= n; len++) {
            if (n % len != 0) {
                continue;
            }

            int[] first = new int[26];

            for (int i = 0; i < len; i++) {
                first[s.charAt(i) - 'a']++;
            }

            boolean valid = true;

            for (int start = len; start < n; start += len) {
                int[] current = new int[26];

                for (int i = start; i < start + len; i++) {
                    current[s.charAt(i) - 'a']++;
                }

                for (int j = 0; j < 26; j++) {
                    if (first[j] != current[j]) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                return len;
            }
        }

        return n;
    }
}