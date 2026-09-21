import java.util.*;

class Solution {
    private long[] nums;
    private int side;
    private int k;

    public int maxDistance(int side, int[][] points, int k) {
        this.side = side;
        this.k = k;

        int n = points.length;
        nums = new long[n];

        for (int i = 0; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];

            if (x == 0) {
                nums[i] = y;
            } else if (y == side) {
                nums[i] = (long) side + x;
            } else if (x == side) {
                nums[i] = (long) side * 3 - y;
            } else {
                nums[i] = (long) side * 4 - x;
            }
        }

        Arrays.sort(nums);

        int left = 1;
        int right = side;

        while (left < right) {
            int mid = left + (right - left + 1) / 2;

            if (check(mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    private boolean check(int distance) {
        long perimeter = (long) side * 4;

        for (int i = 0; i < nums.length; i++) {
            long start = nums[i];
            long end = start + perimeter - distance;
            long current = start;

            boolean possible = true;

            for (int j = 0; j < k - 1; j++) {
                long target = current + distance;

                int index = lowerBound(target);

                if (index == nums.length || nums[index] > end) {
                    possible = false;
                    break;
                }

                current = nums[index];
            }

            if (possible) {
                return true;
            }
        }

        return false;
    }

    private int lowerBound(long target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}