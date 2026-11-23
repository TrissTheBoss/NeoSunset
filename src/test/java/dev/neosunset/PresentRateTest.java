package dev.neosunset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PresentRateTest {
    @Test
    void waitsForAFullSampleAndNormalizesByElapsedTime() {
        PresentRate rate = new PresentRate();
        assertEquals(-1, rate.sample(0));
        for (int i = 0; i < 90; i++) {
            rate.recordAttempt();
        }
        assertEquals(-1, rate.sample(999_999_999));
        assertEquals(60, rate.sample(1_500_000_000));
        assertEquals(60, rate.sample(2_000_000_000));
        assertEquals(0, rate.sample(2_500_000_000L));
    }

    @Test
    void reportsUnavailableUntilThePresentHookIsObserved() {
        PresentRate rate = new PresentRate();
        rate.sample(0);
        assertEquals(-1, rate.sample(1_000_000_000));
        rate.recordAttempt();
        assertEquals(1, rate.sample(2_000_000_000));
    }
}
