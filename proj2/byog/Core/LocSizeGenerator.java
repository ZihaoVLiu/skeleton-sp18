package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.LinkedList;
import java.util.Random;

public class LocSizeGenerator extends Game{
    private static int area = Game.HEIGHT * Game.WIDTH;
    private static int[][] matrix = new int[Game.WIDTH][Game.HEIGHT];


    /**
     * Generating a certain number of several validated room on the windows
     * @param percentage is the number of room tile divided by total area
     * @return a linkedlist of room
     */
    public static LinkedList room(double percentage) {
        int totalArea = 0;
        LinkedList roomList = new LinkedList();
        while (totalArea < percentage * area) {
            int[] room = getMetaRoom();
            totalArea += room[0] * room[1];
            roomList.add(room);
        }
        return roomList;
    }

    public int[][] hallway() {
        int[][] result = new int[3][4];
        return result;
    }

    /**
     * generate a room whose size is smaller than 10*10 on each call,
     * meanwhile validation has been detected on each generating.
     * @return an int array.
     */
    public static int[] getMetaRoom() {
        int rWidth = randomNum(0,10);
        int rHeight = randomNum(0,10);
        int posX = randomNum(0,Game.WIDTH - rWidth);
        int posY = randomNum(0, Game.HEIGHT - rHeight);
        int [] info = {rWidth, rHeight, posX, posY};
        if (isMatrixFilled(info) || !isValidRoom(info)) {
            return getMetaRoom();
        }
        fillMatrix(info);
        return info;
    }

    /**
     * detect whether the generated room valid
     * @param info
     * @return true if room is valid
     */
    private static boolean isValidRoom(int[] info) {
        if (info[0] < 4 || info[1] < 4) {
            return false;
        }
        return true;
    }

    /**
     * detect any overlap between matrix and new generated room
     * @param info
     * @return
     */
    private static boolean isMatrixFilled (int[] info) {
        for (int i = info[2]; i < info[2] + info[0]; i++) {
            for (int j = info[3]; j < info[3] + info[1]; j++) {
                if (matrix[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * fill the relative unit of given room position
     * @param info
     */
    private static void fillMatrix (int[] info) {
        for (int i = info[2]; i < info[2] + info[0]; i++) {
            for (int j = info[3]; j < info[3] + info[1]; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    /**
     * return a random number between lower bound and upper bound
     * @param lowerBound
     * @param upBound
     * @return an random int value
     */
    public static int randomNum(int lowerBound, int upBound) {
        int num = Game.RANDOM.nextInt(upBound) - lowerBound;
        return num;
    }



    public static void main(String[] args) {
        LinkedList roomList = room(0.25);

        for (int[] i : matrix) {
            for (int j : i) {
                System.out.print(j);
            }
            System.out.println();
        }
        int[] first = (int[]) roomList.get(0);
        System.out.println(first[0]);
    }
}
