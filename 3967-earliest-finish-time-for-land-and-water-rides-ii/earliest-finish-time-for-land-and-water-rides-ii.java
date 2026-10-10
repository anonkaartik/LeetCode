import java.util.*;

class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {
        int a = solve(landStartTime, landDuration, waterStartTime, waterDuration);
        int b = solve(waterStartTime, waterDuration, landStartTime, landDuration);
        return Math.min(a, b);
    }

    private int solve(int[] start1, int[] duration1,
                      int[] start2, int[] duration2) {
        int m = start2.length;
        int[][] rides = new int[m][2];

        for (int i = 0; i < m; i++) {
            rides[i][0] = start2[i];
            rides[i][1] = duration2[i];
        }

        Arrays.sort(rides, (a, b) -> Integer.compare(a[0], b[0]));

        int[] starts = new int[m];
        int[] prefixMinDuration = new int[m];
        int[] suffixMinFinish = new int[m];

        for (int i = 0; i < m; i++) {
            starts[i] = rides[i][0];

            prefixMinDuration[i] = rides[i][1];
            if (i > 0) {
                prefixMinDuration[i] = Math.min(
                    prefixMinDuration[i],
                    prefixMinDuration[i - 1]
                );
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            suffixMinFinish[i] = rides[i][0] + rides[i][1];

            if (i + 1 < m) {
                suffixMinFinish[i] = Math.min(
                    suffixMinFinish[i],
                    suffixMinFinish[i + 1]
                );
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < start1.length; i++) {
            int finishFirst = start1[i] + duration1[i];
            int index = upperBound(starts, finishFirst) - 1;

            if (index >= 0) {
                answer = Math.min(
                    answer,
                    finishFirst + prefixMinDuration[index]
                );
            }

            if (index + 1 < m) {
                answer = Math.min(
                    answer,
                    suffixMinFinish[index + 1]
                );
            }
        }

        return answer;
    }

    private int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}