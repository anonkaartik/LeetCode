class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }

        int[] freq = new int[max + 1];
        for (int num : nums) {
            freq[num]++;
        }

        long[] gcdCount = new long[max + 1];

        for (int g = max; g >= 1; g--) {
            long count = 0;

            for (int multiple = g; multiple <= max; multiple += g) {
                count += freq[multiple];
            }

            long pairs = count * (count - 1) / 2;

            for (int multiple = g * 2; multiple <= max; multiple += g) {
                pairs -= gcdCount[multiple];
            }

            gcdCount[g] = pairs;
        }

        for (int g = 1; g <= max; g++) {
            gcdCount[g] += gcdCount[g - 1];
        }

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long target = queries[i];
            int left = 1, right = max;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (gcdCount[mid] > target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            answer[i] = left;
        }

        return answer;
    }
}