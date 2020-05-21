package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
//        if (v == t) {
//            marked[v] = true;
//            targetFound = true;
//            announce();
//            return;
//        }
        Queue<Integer> q = new Queue<>();
        marked[v] = true;
        q.enqueue(v);

        while (!q.isEmpty()) {
            int temp = q.dequeue();
            for (int neighbor : maze.adj(temp)) {
                if (!marked[neighbor]) {
                    edgeTo[neighbor] = temp;
                    distTo[neighbor] = distTo[temp] + 1;
                    marked[neighbor] = true;
                    q.enqueue(neighbor);
                    announce();
                }
                if (neighbor == t) {
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

