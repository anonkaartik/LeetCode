import java.util.*;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long operations = (long) k1 + k2;
        int n = nums1.length;
        int[] diff = new int[n];

        long total = 0;
        int maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            total += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        if (operations >= total) {
            return 0;
        }

        int left = 0;
        int right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long required = 0;

            for (int d : diff) {
                if (d > mid) {
                    required += d - mid;
                }
            }

            if (required <= operations) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int threshold = left;
        long answer = 0;
        long used = 0;

        for (int d : diff) {
            if (d > threshold) {
                used += d - threshold;
                answer += (long) threshold * threshold;
            } else {
                answer += (long) d * d;
            }
        }

        long remaining = operations - used;

        for (int d : diff) {
            if (remaining == 0) {
                break;
            }

            if (d >= threshold && d > 0) {
                answer -= (long) threshold * threshold;
                answer += (long) (threshold - 1) * (threshold - 1);
                remaining--;
            }
        }

        return answer;
    }
}