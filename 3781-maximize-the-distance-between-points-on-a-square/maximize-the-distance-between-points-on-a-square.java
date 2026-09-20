import java.util.*;

class Solution {
    public int maxDistance(int side, int[][] points, int k) {
        int n = points.length;
        long perimeter = 4L * side;

        long[] pos = new long[n];

        for (int i = 0; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];

            if (x == 0) {
                pos[i] = y;
            } else if (y == side) {
                pos[i] = (long) side + x;
            } else if (x == side) {
                pos[i] = 3L * side - y;
            } else {
                pos[i] = 4L * side - x;
            }
        }

        Arrays.sort(pos);

        long low = 0;
        long high = side;

        while (low < high) {
            long mid = low + (high - low + 1) / 2;

            if (canSelect(pos, perimeter, k, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        return (int) low;
    }

    private boolean canSelect(long[] pos, long perimeter, int k, long d) {
        int n = pos.length;

        long[] arr = new long[2 * n];

        for (int i = 0; i < n; i++) {
            arr[i] = pos[i];
            arr[i + n] = pos[i] + perimeter;
        }

        int[] next = new int[2 * n];

        int j = 0;

        for (int i = 0; i < 2 * n; i++) {
            if (j < i + 1) {
                j = i + 1;
            }

            while (j < 2 * n && arr[j] - arr[i] < d) {
                j++;
            }

            next[i] = j;
        }

        for (int start = 0; start < n; start++) {
            int current = start;

            for (int count = 1; count < k; count++) {
                current = next[current];

                if (current >= start + n) {
                    break;
                }
            }

            if (current < start + n &&
                arr[current] - arr[start] <= perimeter - d) {
                return true;
            }
        }

        return false;
    }
}