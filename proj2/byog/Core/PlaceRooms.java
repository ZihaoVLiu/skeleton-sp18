package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class PlaceRooms {
    private static int WIDTH = Game.WIDTH;
    private static int HEIGHT = Game.HEIGHT;
    private static int ptr = 0;
    private static final int maxRoomNum = 30;
    private static final int minRoomNum = 10;
    private static final int maxRoomSize = 8;
    private static final int minRoomSize = 2;
    public PointHere door;
    private static long SEED;
    private static Random RANDOM;

    public PlaceRooms(long seed) {
        SEED = seed;
        RANDOM = new Random(seed);
    }

    public void addMaze(TETile[][] world, TETile t) {
        Room[] rooms = new Room[maxRoomNum];
        int roomNumber = RANDOM.nextInt(maxRoomNum - minRoomNum + 1) + minRoomNum;
        for (int z = 0; z < roomNumber; z++) {
            int w = minRoomSize + RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            int h = minRoomSize + RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            int x = RANDOM.nextInt(WIDTH - w - 2) + 1;
            int y = RANDOM.nextInt(HEIGHT - h - 3) + 1;
            Room newRoom = new Room(x, y, w, h);

            rooms[z] = newRoom;
            for (int i = newRoom.x1; i <= newRoom.x2; i++) {
                for (int j = newRoom.y1; j <= newRoom.y2; j++) {
                    world[i][j] = t;
                }
            }

            PointHere newCenter = newRoom.center;

            if (z !=0 ) {
                PointHere prevCenter = rooms[z - 1].center;
                Hallway hallway = new Hallway();
                if (RANDOM.nextInt(2) == 1) {
                    Hallway.hCorridor hc = hallway.new hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                    Hallway.vCorridor vc = hallway.new vCorridor(prevCenter.y, newCenter.y, newCenter.x);
                } else {
                    Hallway.vCorridor vc = hallway.new vCorridor(prevCenter.y, newCenter.y, prevCenter.x);
                    Hallway.hCorridor hc = hallway.new hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                }
                for (int q = 0; q < WIDTH; q++) {
                    for (int a = 0; a < HEIGHT; a++) {
                        if (hallway.arr[q][a] == 1) {
                            world[q][a] = t;
                        }
                    }
                }
            }
            ptr++;
        }
        int randomDoor = RANDOM.nextInt(roomNumber);
        door = new PointHere(rooms[randomDoor].center.x, rooms[randomDoor].center.y);
        world[door.x][door.y] = Tileset.LOCKED_DOOR;
    }

    public void addWall(TETile[][] world, TETile t) {
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
