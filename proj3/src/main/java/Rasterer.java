import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final double ULLON = -122.2998046875;
    private final double ULLAT = 37.892195547244356;
    private final double LRLON = -122.2119140625;
    private final double LRLAT = 37.82280243352756;
    private double LONDPP = (LRLON - ULLON) / 256;
    private double paramUllon;
    private double paramLrlon;
    private double paramUllat;
    private double paramLrlat;
    private double paramW;
    private double paramH;
    private int level;
    private int size;
    private double width;
    private double height;
    private boolean query_success = false;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                + "your browser.");

        getParamsInfo(params);
        int[] indexes = getCornerLonLatIndex(params);
        double[] latlon = getCornerLonLat(indexes);
        String[][] renders = getRenders(indexes);
        ifSuccess();

        results.put("render_grid", renders);
        results.put("raster_ul_lon", latlon[0]);
        results.put("raster_ul_lat", latlon[1]);
        results.put("raster_lr_lon", latlon[2]);
        results.put("raster_lr_lat", latlon[3]);
        results.put("depth", level);
        results.put("query_success", query_success);
        return results;
    }

    private void getParamsInfo(Map<String, Double> params) {
        paramUllon = params.get("ullon");
        paramLrlon = params.get("lrlon");
        paramUllat = params.get("ullat");
        paramLrlat = params.get("lrlat");
        paramW = params.get("w");
        paramH = params.get("h");
    }

    private int getLevel(Map<String, Double> params) {
        double paramLONDPP = (paramLrlon - paramUllon) / paramW;
        for (int level = 0; level < 8; level++) {
            if (paramLONDPP >= (LONDPP / (int)Math.pow(2, level))) {
                return level;
            }
        }
        return 7;
    }

    private int getSize(int level) {
        return (int)Math.pow(2, level);
    }

    private int[] getCornerLonLatIndex(Map<String, Double> params) {
        int[] lonLatIndex = new int[4];
        level = getLevel(params);
        size = getSize(level);
        width = (LRLON - ULLON) / size;
        height = (ULLAT - LRLAT) / size;

        if (paramUllon < ULLON) {
            lonLatIndex[0] = 0;
        } else {
            for (int i = 0; i < size; i++) {
                if (ULLON + width * i <= paramUllon && paramUllon < ULLON + width * (i + 1)) {
                    lonLatIndex[0] = i;
                    break;
                }
            }
        }

        if (paramUllat > ULLAT) {
            lonLatIndex[1] = 0;
        } else {
            for (int i = 0; i < size; i++) {
                if (ULLAT - height * i >= paramUllat && paramUllat > ULLAT - height * (i + 1)) {
                    lonLatIndex[1] = i;
                    break;
                }
            }
        }

        if (paramLrlon > LRLON) {
            lonLatIndex[2] = size - 1;
        } else {
            for (int i = 0; i < size; i++) {
                if (LRLON - width * i >= paramLrlon && paramLrlon > LRLON - width * (i + 1)) {
                    lonLatIndex[2] = size - 1 - i;
                    break;
                }
            }
        }

        if (paramLrlat < LRLAT) {
            lonLatIndex[3] = size - 1;
        } else {
            for (int i = 0; i < size; i++) {
                if (LRLAT + height * i <= paramLrlat && paramLrlat < LRLAT + height * (i + 1)) {
                    lonLatIndex[3] = size - 1- i;
                    break;
                }
            }
        }
        query_success = true;
        return lonLatIndex;
    }

    private void ifSuccess() {
        if ((paramUllat > ULLAT && paramLrlat > ULLAT) ||
                (paramUllat < LRLAT && paramLrlat < LRLAT) ||
                (paramUllon < ULLON && paramLrlon < ULLON) ||
                (paramUllon > LRLON && paramLrlon > LRLON)) {
            query_success = false;
        }
        if (paramUllon > paramLrlon || paramUllat < paramLrlat) {
            query_success = false;
        }
    }

    private String[][] getRenders(int[] indexes) {
        int column = indexes[2] - indexes[0] + 1;
        int row = indexes[3] - indexes[1] + 1;
        String[][] renders = new String[row][column];
        int x;
        int y;
        for (int i = 0; i < row; i++) {
            y = i + indexes[1];
            for (int j = 0; j< column; j++) {
                x = j + indexes[0];
                renders[i][j] = "d" + level +"_x" + x +"_y" + y + ".png";
            }
        }
        return renders;
    }

    private double[] getCornerLonLat(int[] indexes) {
        double[] results = new double[4];
        results[0] = ULLON + width * indexes[0];
        results[1] = ULLAT - height * indexes[1];
        results[2] = ULLON + width * (indexes[2] + 1);
        results[3] = ULLAT - height * (indexes[3] + 1);
        return results;
    }

}
