package lab14;
import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    protected int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = (state + 1);
        int weirdState = state & (state >> 3) & (state >> 8) % period;
        return weirdState;
    }
}
