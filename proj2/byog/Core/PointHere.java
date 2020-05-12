package byog.Core;

public class PointHere implements java.io.Serializable {
    public int x;
    public int y;
    public PointHere(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (this.getClass() != p.getClass()) {
            return false;
        }
        PointHere that = (PointHere) p;
        return ((this.x == that.x) && (this.y == that.y));
    }
}
