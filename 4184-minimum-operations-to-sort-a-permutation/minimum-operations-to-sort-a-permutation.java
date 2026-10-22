import java.util.*;

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int start = nums[0];
        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != (start + i) % n) {
                increasing = false;
            }

            if (nums[i] != (start - i + n) % n) {
                decreasing = false;
            }
        }

        if (!increasing && !decreasing) {
            return -1;
        }

        int[] dist = new int[2 * n];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        dist[0] = 0;
        queue.offer(0);

        while (!queue.isEmpty()) {
            int state = queue.poll();
            int d = dist[state];

            if (state == (increasing ? start : n + start)) {
                return d;
            }

            int next;

            if (state < n) {
                next = (state + n - 1) % n;
                if (dist[next] == -1) {
                    dist[next] = d + 1;
                    queue.offer(next);
                }

                next = n + (state + n - 1) % n;
            } else {
                int p = state - n;
                next = n + (p + 1) % n;
                if (dist[next] == -1) {
                    dist[next] = d + 1;
                    queue.offer(next);
                }

                next = (p + 1) % n;
            }

            if (dist[next] == -1) {
                dist[next] = d + 1;
                queue.offer(next);
            }
        }

        return -1;
    }
}