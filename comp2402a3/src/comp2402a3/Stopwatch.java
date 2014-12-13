package comp2402a3;

public class Stopwatch {

    protected long start, stop;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
    }

    public double elapsedSeconds() {
        return (stop - start) * 1e-9;
    }
}
