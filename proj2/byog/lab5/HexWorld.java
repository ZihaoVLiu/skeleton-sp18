package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     *  Position is a very simple class with
     *  two variables p.x and p.y and no methods.
     */
    public static class Position {
        public int x;
        public int y;
        public Position(int xp, int yp) {
            x = xp;
            y = yp;
        }
    }

    public static TETile[][] initialTile() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * adds a hexagon of side length s to a given position in the world
     * @param world the world to draw on
     * @param p two parameter position: x and y
     * @param s the size of hexagon
     */
    public static void addHexagon(TETile[][] world, Position p, int s){
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        TETile randomTile = randomTile();

        for (int y = p.y; y < p.y + 2*s; y ++) {
            int level = (y - p.y);
            if (level < s) {
                int statyX = p.x;
                for (int x = statyX - level; x < statyX + s + level; x ++) {
                    world[x][y] = randomTile;
                }
            } else {
                int startX = p.x - (s - 1);
                int numX = (s - 1) * 2 + s;
                for (int x = startX + (level - s); x < startX + numX - (level - s); x++) {
                    world[x][y] = randomTile;
                }
            }
        }

    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        TETile[][] world = initialTile();
        // fills in blocks with given parameters
        addHexagon(world, new Position(6,6), 5);

        ter.renderFrame(world);
    }
}
