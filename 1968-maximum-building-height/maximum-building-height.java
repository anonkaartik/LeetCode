import java.util.*;

class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        List<int[]> list = new ArrayList<>();

        list.add(new int[]{1, 0});

        for (int[] r : restrictions) {
            list.add(new int[]{r[0], r[1]});
        }

        list.add(new int[]{n, n - 1});

        list.sort((a, b) -> Integer.compare(a[0], b[0]));

        for (int i = 1; i < list.size(); i++) {
            int[] prev = list.get(i - 1);
            int[] curr = list.get(i);

            curr[1] = Math.min(curr[1], prev[1] + curr[0] - prev[0]);
        }

        for (int i = list.size() - 2; i >= 0; i--) {
            int[] curr = list.get(i);
            int[] next = list.get(i + 1);

            curr[1] = Math.min(curr[1], next[1] + next[0] - curr[0]);
        }

        long answer = 0;

        for (int i = 1; i < list.size(); i++) {
            int[] left = list.get(i - 1);
            int[] right = list.get(i);

            long distance = right[0] - left[0];
            long leftHeight = left[1];
            long rightHeight = right[1];

            long peak;

            if (leftHeight > rightHeight) {
                peak = leftHeight + distance;
            } else {
                peak = rightHeight + distance;
            }

            peak = Math.min(peak, (leftHeight + rightHeight + distance) / 2);

            answer = Math.max(answer, peak);
        }

        return (int) answer;
    }
}