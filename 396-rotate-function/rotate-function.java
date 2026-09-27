class Solution {
    public int maxRotateFunction(int[] nums) {
        int n = nums.length;

        long sum = 0;
        long current = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            current += (long) i * nums[i];
        }

        long answer = current;

        for (int k = 1; k < n; k++) {
            current = current + sum - (long) n * nums[n - k];
            answer = Math.max(answer, current);
        }

        return (int) answer;
    }
}