class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(n);
        int[] last = new int[100001];
        long ans = 0;

        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            int l = last[x] + 1;

            st.update(l, i, 1);
            last[x] = i;

            ans = (ans + st.querySquareSum()) % MOD;
        }

        return (int) ans;
    }

    static class SegmentTree {
        int n;
        long[] sum, sq, lazy;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n + 5];
            sq = new long[4 * n + 5];
            lazy = new long[4 * n + 5];
        }

        void apply(int node, int left, int right, long v) {
            long len = right - left + 1L;

            sq[node] = (sq[node] + 2L * v % MOD * sum[node] % MOD
                    + len % MOD * v % MOD * v % MOD) % MOD;

            sum[node] = (sum[node] + len * v) % MOD;
            lazy[node] += v;
        }

        void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) {
                return;
            }

            int mid = (left + right) / 2;
            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);
            lazy[node] = 0;
        }

        void update(int node, int left, int right,
                    int ql, int qr, long v) {
            if (ql <= left && right <= qr) {
                apply(node, left, right, v);
                return;
            }

            push(node, left, right);
            int mid = (left + right) / 2;

            if (ql <= mid) {
                update(node * 2, left, mid, ql, qr, v);
            }

            if (qr > mid) {
                update(node * 2 + 1, mid + 1, right, ql, qr, v);
            }

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
        }

        void update(int left, int right, long v) {
            if (left <= right) {
                update(1, 1, n, left, right, v);
            }
        }

        long querySquareSum() {
            return sq[1];
        }
    }
}