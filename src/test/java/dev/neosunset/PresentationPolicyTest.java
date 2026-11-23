package dev.neosunset;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PresentationPolicyTest {
    @Test
    void needsOptInUncappedFocusedCompatibleProfileWithoutVsync() {
        assertTrue(FramePolicy.eligible(true, true, true, false, 260, 260));
        assertFalse(FramePolicy.eligible(false, true, true, false, 260, 260));
        assertFalse(FramePolicy.eligible(true, false, true, false, 260, 260));
        assertFalse(FramePolicy.eligible(true, true, false, false, 260, 260));
        assertFalse(FramePolicy.eligible(true, true, true, true, 260, 260));
        assertFalse(FramePolicy.eligible(true, true, true, false, 120, 260));
        assertFalse(FramePolicy.eligible(true, true, true, false, 260, 60));
    }

    @Test
    void declinesUnknownModsAndFailedLoaderDiscovery() {
        assertTrue(ProfileCompatibility.areBuiltIns(List.of("minecraft", "fabricloader", "neosunset", "java")));
        assertFalse(ProfileCompatibility.areBuiltIns(List.of("minecraft", "neosunset", "fps-limiter")));
        assertFalse(ProfileCompatibility.areBuiltIns(List.of()));
    }

    @Test
    void skipsSurplusSwapsWhileKeepingTheRefreshCadence() {
        SwapCadence cadence = new SwapCadence();
        cadence.refresh(100, true);
        int swaps = 0;
        for (long now = 1; now <= 1_000_000_000; now += 1_000_000) {
            if (cadence.shouldSwap(now)) {
                swaps++;
            }
        }
        assertEquals(100, swaps);
    }

    @Test
    void stableProbeKeepsDeadlineAndDisplayChangeResetsIt() {
        SwapCadence cadence = new SwapCadence();
        cadence.refresh(60, true);
        assertTrue(cadence.shouldSwap(100));
        cadence.refresh(60, false);
        assertFalse(cadence.shouldSwap(101));
        cadence.refresh(144, true);
        assertTrue(cadence.shouldSwap(102));
        assertFalse(cadence.shouldSwap(103));
    }

    @Test
    void unknownRefreshPassesThroughAndStallsDoNotCauseCatchUpSwaps() {
        SwapCadence cadence = new SwapCadence();
        cadence.refresh(Float.NaN, false);
        assertTrue(cadence.shouldSwap(100));
        assertTrue(cadence.shouldSwap(101));
        cadence.refresh(60, true);
        assertTrue(cadence.shouldSwap(102));
        assertTrue(cadence.shouldSwap(1_000_000_000));
        assertFalse(cadence.shouldSwap(1_000_000_001));
    }
}
