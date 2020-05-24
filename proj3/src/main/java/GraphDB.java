import example.CSCourseDB;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    public final Map<Long, GraphDB.Node> nodes = new LinkedHashMap<>();
    public final Map<Long, GraphDB.Way> ways = new LinkedHashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    void addNode(GraphDB.Node n) {
        this.nodes.put(n.id, n);
    }

    void addWay(GraphDB.Way w) {
        this.ways.put(w.id, w);
    }

    void addNdInWay(Long wayID, ArrayList<Long> nds) {
        this.ways.get(wayID).nds = nds;
    }

    void addMaxSpeed(Long wayID, double maxSpeed) {
        this.ways.get(wayID).maxSpeed = maxSpeed;
    }

    void addWayName(Long wayID, String name) {
        this.ways.get(wayID).name = name;
    }

    void putAdjInNode(Long NodeID, Long adjID) {
        if (!this.nodes.get(NodeID).adj.contains(adjID)) {
            this.nodes.get(NodeID).adj.add(adjID);
        }
    }

    void printWays(Long wayID) {
        System.out.println(ways.get(wayID));
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        HashSet<Long> temp = new HashSet<>();
        for (Long key: nodes.keySet()) {
            if (nodes.get(key).adj.isEmpty()) {
                temp.add(key);
            }
        }
        nodes.keySet().removeAll(temp);
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).adj;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        if (nodes.isEmpty()) {
            System.out.println("The graph is empty, cannot get the closet vertex.");
            return 0;
        }
        ArrayList<Node> nodesArray = new ArrayList<>(nodes.values());
        long rightID = nodesArray.get(0).id;
        double minClosest = distance(nodesArray.get(0).lon, nodesArray.get(0).lat, lon, lat);
        for (Node node : nodesArray) {
            double temp = distance(node.lon, node.lat, lon, lat);
            if (temp < minClosest) {
                minClosest = temp;
                rightID = node.id;
            }
        }
        return rightID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).lat;
    }


    static class Node {
        Long id;
        double lat;
        double lon;
        ArrayList<Long> adj;

        Node(Long id, String lat, String lon) {
            this.id = id;
            this.lat = Double.parseDouble(lat);
            this.lon = Double.parseDouble(lon);
            this.adj = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=========Node ID: ").append(id).append("=========\n");
            sb.append("* lat: ").append(lat).append('\n');
            sb.append("* lon: ").append(lon).append('\n');
            return sb.toString();
        }
    }

    static class Way {
        Long id;
        ArrayList<Long> nds;
        double maxSpeed;
        String name;

        Way(Long id) {
            this.id = id;
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=========Way ID: ").append(id).append("=========\n");
            int index = 1;
            for (Long nd: nds){
                String print = "* nd" + index + ": ";
                sb.append(print).append(nd).append('\n');
                index++;
            }
            return sb.toString();
        }
    }

    public boolean isContain(String id) {
        return ways.containsKey(id);
    }

}
