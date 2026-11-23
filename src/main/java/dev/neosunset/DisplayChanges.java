package dev.neosunset;

public final class DisplayChanges {
    // The vanilla window events and presentation hooks run on the render thread.
    private static int generation;

    private DisplayChanges() {
    }

    public static int generation() {
        return generation;
    }

    public static void invalidate() {
        generation++;
    }
}
