package dev.neosunset;

/** Samples completed native present calls, not frames scanned out by the display. */
public final class PresentRate {
    public static final PresentRate CLIENT = new PresentRate();
    private static final long SECOND = 1_000_000_000L;

    private boolean started;
    private boolean observed;
    private long start;
    private long attempts;
    private long rate = -1;

    public void recordAttempt() {
        observed = true;
        if (started) {
            attempts++;
        }
    }

    public long sample(long now) {
        if (!started) {
            started = true;
            start = now;
        } else {
            long elapsed = now - start;
            if (elapsed >= SECOND) {
                rate = observed ? Math.round(attempts * (double) SECOND / elapsed) : -1;
                attempts = 0;
                start = now;
            }
        }
        return rate;
    }
}
