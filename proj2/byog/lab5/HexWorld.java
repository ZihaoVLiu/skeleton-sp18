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
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * adds a hexagon of side length s to a given position in the world
     * @param world the world to draw on
     * @param p two parameter position: x and y
     * @param s the size of hexagon
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        TETile randomTile = randomTile();

        for (int y = p.y; y < p.y + 2*s; y ++) {
            int level = (y - p.y);
            if (level < s) {
                int statyX = p.x;
                for (int x = statyX - level; x < statyX + s + level; x ++) {
                    world[x][y] = t;
                }
            } else {
                int startX = p.x - (s - 1);
                int numX = (s - 1) * 2 + s;
                for (int x = startX + (level - s); x < startX + numX - (level - s); x++) {
                    world[x][y] = t;
                }
            }
        }
    }

    public static void drawAColumn(TETile[][] world, int size, Position p, TETile t, int number) {
        Position[] pList = new Position[number];
        pList[0] = p;
        for (int i = 1; i < number; i ++) {
            pList[i] = new Position(p.x, pList[i-1].y + 2 * size);
        }
        for (Position position : pList) {
            addHexagon(world, position, size, randomTile());
        }
    }

    public static void drawTesselation(TETile[][] world, int size, Position p, TETile t) {
        Position[] pList = new Position[5];
        pList[0] = p;
        for (int i = 1; i < 5; i ++) {
            if (i < 3) {
                pList[i] = new Position(pList[i-1].x + (2 * size -1), pList[i-1].y - size);
            } else {
                pList[i] = new Position(pList[i-1].x + (2 * size -1), pList[i-1].y + size);
            }
        }
        drawAColumn(world, size, pList[0], t, 3);
        drawAColumn(world, size, pList[1], t, 4);
        drawAColumn(world, size, pList[2], t, 5);
        drawAColumn(world, size, pList[3], t, 4);
        drawAColumn(world, size, pList[4], t, 3);
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        TETile[][] world = initialTile();
        // fills in blocks with given parameters
        //drawAColumn(world, 3, new Position(10, 10), randomTile(), 6);
        drawTesselation(world, 4, new Position(10, 10), randomTile());
        ter.renderFrame(world);
    }
}
