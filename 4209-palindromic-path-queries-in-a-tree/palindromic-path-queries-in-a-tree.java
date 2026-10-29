import java.util.*;

class Solution {
    private int LOG;
    private List<Integer>[] adj;
    private int[][] up;
    private int[] depth, tin, tout, bit, mask;
    private char[] chars;
    private int timer, n;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        bit = new int[n + 2];
        mask = new int[n];
        chars = s.toCharArray();

        buildTree();

        for (int i = 0; i < n; i++) {
            rangeXor(tin[i], tout[i], mask[i]);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int newMask = 1 << (p[2].charAt(0) - 'a');
                rangeXor(tin[u], tout[u], mask[u] ^ newMask);
                mask[u] = newMask;
                chars[u] = p[2].charAt(0);
            } else {
                int v = Integer.parseInt(p[2]);
                int w = lca(u, v);

                int pathMask = pointQuery(tin[u])
                             ^ pointQuery(tin[v])
                             ^ mask[w];

                answer.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return answer;
    }

    private void buildTree() {
        int[] parent = new int[n];
        int[] order = new int[n];
        int[] stack = new int[n];
        int[] size = new int[n];

        Arrays.fill(parent, -1);

        int top = 0;
        int count = 0;
        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer++;
            order[count++] = u;
            mask[u] = 1 << (chars[u] - 'a');
            up[0][u] = parent[u];

            for (int v : adj[u]) {
                if (parent[v] != -1) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int i = 0; i < n; i++) {
            tout[i] = tin[i] + size[i];
        }
    }

    private void bitAdd(int index, int value) {
        index++;

        while (index <= n + 1) {
            bit[index] ^= value;
            index += index & -index;
        }
    }

    private void rangeXor(int left, int right, int value) {
        bitAdd(left, value);
        bitAdd(right, value);
    }

    private int pointQuery(int index) {
        int result = 0;
        index++;

        while (index > 0) {
            result ^= bit[index];
            index -= index & -index;
        }

        return result;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }
}