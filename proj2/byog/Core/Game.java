package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdStats;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private int mouseX;
    private int mouseY;
    private boolean win = false;
    public static Random RANDOM;
    private PointHere door;
    private PointHere player;
    private static TETile[][] finalWorldFrame;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawInitialize();
        drawGUI();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
        char c = StdDraw.nextKeyTyped();
        if (c == 'n' || c == 'N') {
            newGame();
            System.exit(0);
        } else if (c == 'l' || c == 'L') {
            TETile[][] object1 = null;
            PointHere player1 = null;
            PointHere door1 = null;
            String filename = "file.ser";
            String filename1 = "file1.ser";
            String filename2 ="file2.ser";
            // Deserialization
            try
            {
                // Reading the object from a file
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                FileInputStream file1 = new FileInputStream(filename1);
                ObjectInputStream in1 = new ObjectInputStream(file1);
                FileInputStream file2 = new FileInputStream(filename2);
                ObjectInputStream in2 = new ObjectInputStream(file2);


                // Method for deserialization of object
                object1 = (TETile[][]) in.readObject();
                finalWorldFrame = object1;
                player1 = (PointHere) in1.readObject();
                player = player1;
                door1 = (PointHere) in2.readObject();
                door = door1;

                in.close();
                file.close();
                in1.close();
                file1.close();
                in2.close();
                file2.close();

                ter.renderFrame(finalWorldFrame);
                System.out.println("Object has been deserialized ");
                operation();
                System.exit(0);
            } catch(IOException ex) {
                System.out.println("IOException is caught");
            } catch(ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException is caught");
            }

        } else if (c == 'Q' || c == 'q') {
            System.exit(0);
        }
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
        ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        long seed = toDigit(input);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        ter.renderFrame(finalWorldFrame);
        PlaceRooms placeRoom = new PlaceRooms(seed);
        TETile floor = Tileset.FLOOR;
        placeRoom.addMaze(finalWorldFrame, floor);
        TETile wall = Tileset.WALL;
        placeRoom.addWall(finalWorldFrame, wall);
        door = placeRoom.door;
        player = placeRoom.player;
        ter.renderFrame(finalWorldFrame);
        operation();
        return finalWorldFrame;
    }

    public long toDigit(String input) {
        char[] c = input.toCharArray();
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(c[i])) {
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

    public TETile[][] playerMove(TETile[][] world, char c) {
        TETile wallTile = Tileset.WALL;
        TETile playerTile = Tileset.PLAYER;
        TETile floorTile = Tileset.FLOOR;
        if (c == 'w') {
            if (world[player.x][player.y + 1] != wallTile) {
                world[player.x][player.y + 1] = playerTile;
                world[player.x][player.y] =floorTile;
                player.y += 1;
                if (player.equals(door)) {
                    win = true;
                }
            }
        } else if (c == 's') {
            if (world[player.x][player.y - 1] != wallTile) {
                world[player.x][player.y - 1] = playerTile;
                world[player.x][player.y] =floorTile;
                player.y -= 1;
                if (player.equals(door)) {
                    win = true;
                }
            }
        } else if (c == 'a') {
            if (world[player.x - 1][player.y] != wallTile) {
                world[player.x - 1][player.y] = playerTile;
                world[player.x][player.y] =floorTile;
                player.x -= 1;
                if (player.equals(door)) {
                    win = true;
                }
            }
        } else if (c == 'd') {
            if (world[player.x + 1][player.y] != wallTile) {
                world[player.x + 1][player.y] = playerTile;
                world[player.x][player.y] =floorTile;
                player.x += 1;
                if (player.equals(door)) {
                    win = true;
                }
            }
        }
        return world;
    }

    public void drawInitialize() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);
    }

    public void drawGUI() {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);

        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        Font smallFont = new Font("Monace", Font.BOLD, 20);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "CS 61B: Project2: The Game");
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 3, "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 7, "Quit Game (Q)");
        StdDraw.show();
    }

    public TETile[][] drawHUD(TETile[][] world, String des) {
        for (int i = 0; i < des.length(); i++) {
            char c = des.charAt(i);
            world[i][HEIGHT-1] = new TETile(c, Color.white, Color.black, "character");
        }
        return world;
    }

    public void drawHUD(String des) {
        Font smallFont = new Font("Monace", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(3, HEIGHT-1,  des);
        StdDraw.show();
    }

    public void drawSEED(String seed) {
        Font smallFont = new Font("Monaco", Font.CENTER_BASELINE, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(((double)WIDTH)/2, 3.0, "SEED: "+seed);
        StdDraw.show();
    }

    public void newGame() {
        ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        String input = "";
        drawSEED(input);
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (c == 'S') {
                break;
            }
            input += c;
            StdDraw.clear(Color.black);
            drawSEED(input);
        }
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
        player = placeRoom.player;

        ter.renderFrame(finalWorldFrame);
        /**
         * should add operation here.
         */
        operation();
    }

    public void operation() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                String fileWorld = "file.ser";
                String filePlayer = "file1.ser";
                String fileDoor = "file2.ser";
                if (c == 'Q') {
                    try {
                        //Saving of object in a file
                        FileOutputStream file = new FileOutputStream(fileWorld);
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        FileOutputStream file1 = new FileOutputStream(filePlayer);
                        ObjectOutputStream out1 = new ObjectOutputStream(file1);
                        FileOutputStream file2 = new FileOutputStream(fileDoor);
                        ObjectOutputStream out2 = new ObjectOutputStream(file2);

                        // Method for serialization of object
                        out.writeObject(finalWorldFrame);
                        out1.writeObject(player);
                        out2.writeObject(door);

                        out.close();
                        file.close();
                        out1.close();
                        file1.close();
                        out2.close();
                        file2.close();

                        System.out.println("Object has been serialized");

                    } catch (IOException e) {
                        System.out.println("IOException has been caught (Saving)");
                    }
                    break;
                }
                finalWorldFrame = playerMove(finalWorldFrame, c);
                ter.renderFrame(finalWorldFrame);

                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (StdDraw.isMousePressed()) {
                mouseX = (int) StdDraw.mouseX();
                mouseY = (int) StdDraw.mouseY();
                String des = finalWorldFrame[mouseX][mouseY].description();
                drawHUD(des);
                ter.renderFrame(finalWorldFrame);
            }
        }
    }

}
