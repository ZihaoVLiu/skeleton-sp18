package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public Point door;
    public static Random RANDOM;
    public static TETile[][] finalWorldFrame;

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
        //ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        Long seed = toDigit(input);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        PlaceRooms placeRoom = new PlaceRooms(seed);
        TETile floor = Tileset.FLOOR;
        placeRoom.addMaze(finalWorldFrame, floor);
        TETile wall = Tileset.WALL;
        placeRoom.addWall(finalWorldFrame, wall);
        door = placeRoom.door;

        //ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    public long toDigit(String input){
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

}