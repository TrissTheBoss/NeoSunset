package dev.neosunset;

public final class FramePolicy {
    // The 26.3 Video Settings slider encodes Unlimited as 260.
    private static final int UNLIMITED = 260;

    private FramePolicy() {
    }

    public static boolean isUnlimited(int limit) {
        return limit == UNLIMITED;
    }

    public static boolean eligible(boolean enabled, boolean compatible, boolean focused,
                                   boolean vsync, int selectedLimit, int effectiveLimit) {
        return enabled && compatible && focused && !vsync
                && isUnlimited(selectedLimit) && isUnlimited(effectiveLimit);
    }
}
