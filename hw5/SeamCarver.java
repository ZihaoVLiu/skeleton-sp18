import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private boolean horizontal = false;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture picture() { // current picture
        return new Picture(this.picture);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        double rX;
        double gX;
        double bX;
        double rY;
        double gY;
        double bY;
        double deltaX;
        double deltaY;
        double energy;
        if (x >= this.width || x < 0) {
            throw new IndexOutOfBoundsException("The input x is outside its prescribed range");
        }
        if (y >= this.height || y < 0) {
            throw new IndexOutOfBoundsException("The input y is outside its prescribed range");
        }
//        if (x == 0) {
//            Color right = picture.get(width() - 1, y);
//            Color left = picture.get(x + 1, y);
//            isHorBoundary = true;
//            rX = right.getRed() - left.getRed();
//        } else if (x == width() - 1) {
//            Color left = picture.get(0, y);
//            Color right = picture.get(x - 1, y);
//            isHorBoundary = true;
//        }
//        if (y == 0) {
//            Color bottom = picture.get(x, height() - 1);
//            Color top = picture.get(x, y + 1);
//            isVerBoundary = true;
//        } else if (y == height() - 1) {
//            Color top = picture.get(x, 0);
//            Color bottom = picture.get(x, y - 1);
//            isVerBoundary = true;
//        }
//        if (!isHorBoundary) {
//            Color left = picture.get(x - 1, y);
//            Color right = picture.get(x + 1, y);
//        }
//        if (!isVerBoundary) {
//            Color top = picture.get(x, y - 1);
//            Color bottom = picture.get(x, y + 1);
//        }
        rX = picture().get((x - 1 + width()) % width(), y).getRed() -
                picture().get((x + 1 + width()) % width(), y).getRed();
        gX = picture().get((x - 1 + width()) % width(), y).getGreen() -
                picture().get((x + 1 + width()) % width(), y).getGreen();
        bX = picture().get((x - 1 + width()) % width(), y).getBlue() -
                picture().get((x + 1 + width()) % width(), y).getBlue();
        rY = picture().get(x, (y - 1 + height()) % height()).getRed() -
                picture().get(x, (y + 1 + height()) % height()).getRed();
        gY = picture().get(x, (y - 1 + height()) % height()).getGreen() -
                picture().get(x, (y + 1 + height()) % height()).getGreen();
        bY = picture().get(x, (y - 1 + height()) % height()).getBlue() -
                picture().get(x, (y + 1 + height()) % height()).getBlue();
        deltaX = Math.pow(rX, 2) + Math.pow(gX, 2) + Math.pow(bX, 2);
        deltaY = Math.pow(rY, 2) + Math.pow(gY, 2) + Math.pow(bY, 2);
        energy = deltaX + deltaY;
        return energy;
    }

    private double[][] buildM() {
        // initial a energy matrix
        double e[][] = new double[width()][height()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                e[i][j] = energy(i, j);
            }
        }

        // if use horizontal seam assign eH to e
        double[][] eH;
        if (this.horizontal) {
            int temp = width();
            this.width = this.height;
            this.height = temp;
            eH = new double[width()][height()];
            for (int j = 0; j < height(); j++) {
                for (int i = 0; i < width(); i++) {
                    eH[i][j] = e[j][i];
                }
            }
            e = eH;
        }

        // initial first row of M matrix
        double M[][] = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            M[i][0] = e[i][0];
        }
        // generate rest row of M matrix
        for (int j = 1; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                if (i == 0) {
                    M[i][j] = Math.min(M[i][j - 1], M[i + 1][j - 1]) + e[i][j];
                } else if (i == width() - 1) {
                    M[i][j] = Math.min(M[i][j - 1], M[i - 1][j - 1]) + e[i][j];
                } else {
                    double prop = Math.min(Math.min(M[i][j - 1], M[i - 1][j - 1]),
                            Math.min(M[i][j - 1], M[i + 1][j - 1]));
                    M[i][j] = prop + e[i][j];
                }
            }
        }
        return M;
    }

    public int[] findHorizontalSeam() { //sequence of indices for horizontal seam
        this.horizontal = true;
        int [] HorizontalArr = findVerticalSeam();
        int temp = this.width;
        this.width = this.height;
        this.height = temp;
        this.horizontal = false;
        return HorizontalArr;
    }

    public int[] findVerticalSeam() { // sequence of indices for vertical seam
        int[] path;
        double[][] M = buildM();
        double min = M[0][height() - 1];
        int minIndex = 0;
        for (int i = 1; i < width(); i++) {
            if (M[i][height() - 1] < min) {
                min = M[i][height() - 1];
                minIndex = i;
            }
        }
        path = getPath(M, minIndex);
        return path;
    }

    private int[] getPath(double[][] M, int minIndex) {
        int[] path = new int[height()];
        path[height() - 1] = minIndex;
        int start = minIndex;
        for (int j = height() - 1; j >= 1; j--) {
            if (start == 0) {
                if (M[start][j - 1] < M[start + 1][j - 1]) {
                    path[j - 1] = start;
                } else {
                    path[j - 1] = start + 1;
                }
            } else if (start == width() - 1) {
                if (M[start][j - 1] < M[start - 1][j - 1]) {
                    path[j - 1] = start;
                } else {
                    path[j - 1] = start - 1;
                }
            } else {
                double min = Math.min(Math.min(M[start - 1][j - 1], M[start][j - 1]),
                        Math.min(M[start + 1][j - 1], M[start][j - 1]));
                if (min == M[start - 1][j - 1]) {
                    path[j - 1] = start - 1;
                } else if (min == M[start + 1][j - 1]) {
                    path[j - 1] = start + 1;
                } else {
                    path[j - 1] = start;
                }
//                if (M[start][j - 1] > M[start - 1][j - 1]) {
//                    path[j - 1] = start - 1;
//                } else if (M[start][j - 1] > M[start + 1][j - 1]) {
//                    path[j - 1] = start + 1;
//                } else {
//                    path[j - 1] = start;
//                }
            }
            start = path[j - 1];
        }
        return path;
    }


    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from picture
        if (seam.length == 0) {
            return;
        }
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeHorizontalSeam(this.picture, seam));
            height--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from picture
        if (seam.length == 0) {
            return;
        }
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeVerticalSeam(this.picture, seam));
            width--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }

}
