package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    protected int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        if (state == 0) {
            period *= factor;
        }
        return 2 * ((double) state / (period - 1)) - 1;
    }
}
