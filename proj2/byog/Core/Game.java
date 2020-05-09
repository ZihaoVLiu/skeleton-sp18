package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static Random RANDOM;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        RANDOM = inputJudge(input);
        TETile[][] finalWorldFrame = initialTile(ter);

        LinkedList roomList = LocSizeGenerator.room(0.3);
        generateFloor(finalWorldFrame, roomList);

        Point[] pointList =  getCenter(roomList);
        generateHallway(finalWorldFrame, pointList);
        addWall(finalWorldFrame, Tileset.WALL);

        System.out.println(toDigit(input));

        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    /**
     * Judge whether the input is valid and return a Random instance of input seed.
     * @param input a String inputted from Main.java
     * @return an instance of Random class
     */
    protected static Random inputJudge(String input) {
        if (input.charAt(0) != 'N') {
            throw new IllegalArgumentException("Expected 'N' as first char of input String.");
        } else if (input.charAt(input.length() - 1) != 'S') {
            throw new IllegalArgumentException("Expected 'S' as last char of input String.");
        }
        // get the seed from input String.
        long seed = Long.parseLong(input.substring(1, input.length() - 1));
        return new Random(seed);
    }

    public Long toDigit(String input){
        char[] c = input.toCharArray();
        int count = 0;
        for (int i = 0; i < input.length(); i++){
            if (Character.isDigit(c[i])){
                count++;
            }
        }
        int[] arr = new int[count];

        count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(c[i])) {
                arr[count] = Character.getNumericValue(c[i]);
                count++;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i : arr) {
            builder.append(i);
        }
        long requestLong = Long.parseLong(builder.toString());
        return requestLong;
    }

    /**
     *  Initialize the tile rendering engine with a window of size WIDTH x HEIGHT,
     *  and initialize tiles with .NOTHING.
     * @param ter is the TERenderer instance.
     * @return an initialized 2D TETile world.
     */
    public static TETile[][] initialTile(TERenderer ter) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        return finalWorldFrame;
    }

    /**
     * generate floor for all input room or hallway without outermost circle.
     * @param finalWorldFrame
     * @param list the linked list generated from room method
     */
    private static void generateFloor(TETile[][] finalWorldFrame, LinkedList list) {
        for (int i = 0; i < list.size(); i ++) {
            int[] room = (int[]) list.get(i);
            for (int x = room[2] + 1; x < room[2] + room[0] - 1; x ++) {
                for (int y = room[3] + 1; y < room[3] + room[1] - 1; y++) {
                    finalWorldFrame[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    /**
     * generate wall for all input room or hallway (actually set all as wall).
     * @param finalWorldFrame
     * @param list the linked list generated from room method
     */
    private static void generateWall(TETile[][] finalWorldFrame, LinkedList list) {
        for (int i = 0; i < list.size(); i ++) {
            int[] room = (int[]) list.get(i);
            for (int x = room[2]; x < room[2] + room[0]; x ++) {
                for (int y = room[3]; y < room[3] + room[1]; y++) {
                    finalWorldFrame[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public static void generateRoom(TETile[][] finalWorldFrame, LinkedList list) {
        generateWall(finalWorldFrame, list);
        generateFloor(finalWorldFrame, list);
    }

    public static Point[] getCenter(LinkedList list) {
        Point[] centerList = new Point[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            int[] room = (int[]) list.get(i);
            int x = room[2];
            int y = room[3];
            int width = room[0];
            int height = room[1];
            centerList[i] = new Point((int) Math.floor((x + x + width) / 2),
                    (int) Math.floor((y + y + height) / 2));
        }
        return centerList;
    }

    public void generateHallway(TETile[][] finalWorldFrame, Point[] centers) {
        for (int i = 1; i < centers.length; i ++) {
            Point prevCenter = centers[i - 1];
            Point newCenter = centers[i];
            Hallway hallway = new Hallway();

            if (RANDOM.nextInt(2) == 1) {
                Hallway.hCorridor hc = hallway.new hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                Hallway.vCorridor vc = hallway.new vCorridor(prevCenter.y, newCenter.y, newCenter.x);
            } else {
                Hallway.vCorridor vc = hallway.new vCorridor(prevCenter.y, newCenter.y, prevCenter.x);
                Hallway.hCorridor hc = hallway.new hCorridor(prevCenter.x, newCenter.x, newCenter.y);
            }
            for (int q = 0; q < WIDTH; q++) {
                for (int a = 0; a < HEIGHT; a++) {
                    if (hallway.arr[q][a] == 1) {
                        finalWorldFrame[q][a] = Tileset.FLOOR;
                    }
                }
            }
        }
    }


    public void addWall(TETile[][] world, TETile t){
        int count = 0;
        for(int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++){
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int j = 0; j < HEIGHT; j++){
            for (int i = 0; i < WIDTH; i++){
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int i = WIDTH - 1; i >= 0; i--) {
            for (int j = HEIGHT - 1; j >= 0 ; j--) {
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int j = HEIGHT - 1; j >= 0; j--) {
            for (int i = WIDTH - 1; i >= 0 ; i--) {
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
    }

}