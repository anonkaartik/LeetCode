import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        int LOG = 17;

        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int[][] up = new int[n + 1][LOG];
        int[] depth = new int[n + 1];
        int[] parent = new int[n + 1];
        int[] stack = new int[n];
        int top = 0;

        stack[top++] = 1;
        parent[1] = 1;

        while (top > 0) {
            int node = stack[--top];

            for (int next : graph[node]) {
                if (next == parent[node]) {
                    continue;
                }

                parent[next] = node;
                up[next][0] = node;
                depth[next] = depth[node] + 1;
                stack[top++] = next;
            }
        }

        up[1][0] = 1;

        for (int j = 1; j < LOG; j++) {
            for (int i = 1; i <= n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];

            int lca = getLCA(u, v, depth, up, LOG);
            int distance = depth[u] + depth[v] - 2 * depth[lca];

            ans[i] = distance == 0 ? 0 : power(2, distance - 1);
        }

        return ans;
    }

    private int getLCA(int u, int v, int[] depth, int[][] up, int LOG) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[u][j];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }

        return up[u][0];
    }

    private int power(long base, int exp) {
        long result = 1;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = result * base % MOD;
            }

            base = base * base % MOD;
            exp >>= 1;
        }

        return (int) result;
    }
}