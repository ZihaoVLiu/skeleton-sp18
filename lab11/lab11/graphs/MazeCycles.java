package lab11.graphs;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private int start;
    private int end;
    private Stack<Integer> fringe = new Stack<>();
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        int s = 0;
        int t = maze.V() - 1;
        distTo[s] = 0;
        edgeTo[s] = s;
        start = 0;
    }

    @Override
    public void solve() {
        dfs();
        if (start != 0) {
            drawCircle(start);
        }
    }

    private void dfs() {
        // TODO: Your code here!
        marked[s] = true;
        fringe.push(s);

        while (!fringe.isEmpty()) {
            int temp = fringe.pop();
            for (int neighbor : maze.adj(temp)) {
                if (!marked[neighbor]) {
                    //edgeTo[neighbor] = temp;
                    distTo[neighbor] = distTo[temp] + 1;
                    fringe.push(neighbor);
                    marked[neighbor] = true;
                    announce();
                } else {
                    if (distTo[neighbor] != distTo[temp] - 1) {
                        start = neighbor;
                        return;
                    }
                }
            }
        }
    }

    private void drawCircle(int start) {
        end = fringe.peek();
        while (fringe.peek() != start) {
            edgeTo[fringe.pop()] = fringe.peek();
            announce();
        }
        edgeTo[start] = end;
        announce();
    }

    // Helper methods go here
}

