import java.util.*;

class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return 0;
        }

        int maxValue = 0;

        for (int num : nums) {
            maxValue = Math.max(maxValue, num);
        }

        int[] spf = new int[maxValue + 1];

        for (int i = 2; i <= maxValue; i++) {
            if (spf[i] == 0) {
                spf[i] = i;

                if ((long) i * i <= maxValue) {
                    for (int j = i * i; j <= maxValue; j += i) {
                        if (spf[j] == 0) {
                            spf[j] = i;
                        }
                    }
                }
            }
        }

        List<Integer>[] positions = new ArrayList[maxValue + 1];

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            while (x > 1) {
                int p = spf[x];

                if (positions[p] == null) {
                    positions[p] = new ArrayList<>();
                }

                positions[p].add(i);

                while (x % p == 0) {
                    x /= p;
                }
            }
        }

        int[] distance = new int[n];
        Arrays.fill(distance, -1);

        boolean[] usedPrime = new boolean[maxValue + 1];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        distance[0] = 0;

        while (!queue.isEmpty()) {
            int i = queue.poll();

            if (i == n - 1) {
                return distance[i];
            }

            int nextDistance = distance[i] + 1;

            if (i > 0 && distance[i - 1] == -1) {
                distance[i - 1] = nextDistance;
                queue.offer(i - 1);
            }

            if (i + 1 < n && distance[i + 1] == -1) {
                distance[i + 1] = nextDistance;
                queue.offer(i + 1);
            }

            int value = nums[i];

            if (spf[value] == value && !usedPrime[value]) {
                usedPrime[value] = true;

                if (positions[value] != null) {
                    for (int index : positions[value]) {
                        if (distance[index] == -1) {
                            distance[index] = nextDistance;
                            queue.offer(index);
                        }
                    }
                }
            }
        }

        return -1;
    }
}