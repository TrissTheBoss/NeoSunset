package dev.neosunset;

public final class SwapCadence {
    private long nextSwap;
    private long period;

    public void refresh(float refreshRate, boolean displayChanged) {
        long newPeriod = Float.isFinite(refreshRate) && refreshRate >= 1 && refreshRate <= 1000
                ? Math.round(1_000_000_000.0 / refreshRate)
                : 0;
        if (period != newPeriod || displayChanged) {
            period = newPeriod;
            nextSwap = 0;
        }
    }

    public boolean shouldSwap(long now) {
        if (period == 0) {
            return true;
        }
        if (nextSwap == 0 || now - nextSwap >= period) {
            nextSwap = now + period;
            return true;
        }
        if (now - nextSwap < 0) {
            return false;
        }
        // Keep the average cadence when rendered frames cross a refresh deadline late.
        nextSwap += period;
        return true;
    }
}
