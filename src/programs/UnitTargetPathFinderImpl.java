package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    private static final int W = 27;
    private static final int H = 21;
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        int sx = attackUnit.getxCoordinate();
        int sy = attackUnit.getyCoordinate();
        int gx = targetUnit.getxCoordinate();
        int gy = targetUnit.getyCoordinate();
        if (sx == gx && sy == gy) {
            List<Edge> one = new ArrayList<>();
            one.add(new Edge(sx, sy));
            return one;
        }
        boolean[][] blocked = new boolean[H][W];
        for (Unit u : existingUnitList) {
            if (u.isAlive()) {
                int ux = u.getxCoordinate();
                int uy = u.getyCoordinate();
                if (!(ux == sx && uy == sy) && !(ux == gx && uy == gy)) blocked[uy][ux] = true;
            }
        }
        boolean[][] visited = new boolean[H][W];
        Edge[][] parent = new Edge[H][W];
        Queue<Edge> q = new LinkedList<>();
        q.add(new Edge(sx, sy));
        visited[sy][sx] = true;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            Edge cur = q.poll();
            if (cur.getX() == gx && cur.getY() == gy) return buildPath(parent, cur);
            for (int[] d : dirs) {
                int nx = cur.getX() + d[0];
                int ny = cur.getY() + d[1];
                if (nx >= 0 && nx < W && ny >= 0 && ny < H) {
                    if (!blocked[ny][nx] && !visited[ny][nx]) {
                        visited[ny][nx] = true;
                        parent[ny][nx] = cur;
                        q.add(new Edge(nx, ny));
                    }
                }
            }
        }
        return Collections.emptyList();
    }
    private List<Edge> buildPath(Edge[][] parent, Edge goal) {
        List<Edge> path = new ArrayList<>();
        Edge cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = parent[cur.getY()][cur.getX()];
        }
        Collections.reverse(path);
        return path;
    }
}
