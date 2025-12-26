package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    // ========== Тесты для TotalSessionsCount ==========

    @Test
    public void testTotalSessionsCountEmptyList() {
        SleepAnalyzer analyzer = new TotalSessionsCount();
        assertEquals(0, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testTotalSessionsCountSingleSession() {
        SleepAnalyzer analyzer = new TotalSessionsCount();
        List<SleepingSession> sessions = Collections.singletonList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(1, analyzer.analyze(sessions));
    }

    @Test
    public void testTotalSessionsCountMultipleSessions() {
        SleepAnalyzer analyzer = new TotalSessionsCount();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 15, 0),
                        SleepQuality.BAD
                )
        );
        assertEquals(3, analyzer.analyze(sessions));
    }

    // ========== Тесты для MinDuration ==========

    @Test
    public void testMinDurationEmptyList() {
        SleepAnalyzer analyzer = new MinDuration();
        assertEquals(0L, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testMinDurationSingleSession() {
        SleepAnalyzer analyzer = new MinDuration();
        List<SleepingSession> sessions = Collections.singletonList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(480L, analyzer.analyze(sessions));
    }

    @Test
    public void testMinDurationMultipleSessions() {
        SleepAnalyzer analyzer = new MinDuration();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0), // 60 минут
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        assertEquals(60L, analyzer.analyze(sessions));
    }

    // ========== Тесты для MaxDuration ==========

    @Test
    public void testMaxDurationEmptyList() {
        SleepAnalyzer analyzer = new MaxDuration();
        assertEquals(0L, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testMaxDurationSingleSession() {
        SleepAnalyzer analyzer = new MaxDuration();
        List<SleepingSession> sessions = Collections.singletonList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(480L, analyzer.analyze(sessions));
    }

    @Test
    public void testMaxDurationMultipleSessions() {
        SleepAnalyzer analyzer = new MaxDuration();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0), // 60 минут
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        assertEquals(480L, analyzer.analyze(sessions));
    }

    // ========== Тесты для AverageDuration ==========

    @Test
    public void testAverageDurationEmptyList() {
        SleepAnalyzer analyzer = new AverageDuration();
        assertEquals(0.0, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testAverageDurationSingleSession() {
        SleepAnalyzer analyzer = new AverageDuration();
        List<SleepingSession> sessions = Collections.singletonList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(480.0, (Double) analyzer.analyze(sessions), 0.01);
    }

    @Test
    public void testAverageDurationMultipleSessions() {
        SleepAnalyzer analyzer = new AverageDuration();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0), // 60 минут
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0), // 480 минут
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        assertEquals(340.0, (Double) analyzer.analyze(sessions), 0.01);
    }

    // ========== Тесты для BadQualitySessionsCount ==========

    @Test
    public void testBadQualitySessionsCountEmptyList() {
        SleepAnalyzer analyzer = new BadQualitySessionsCount();
        assertEquals(0L, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testBadQualitySessionsCountNoBadQuality() {
        SleepAnalyzer analyzer = new BadQualitySessionsCount();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        assertEquals(0L, analyzer.analyze(sessions));
    }

    @Test
    public void testBadQualitySessionsCountMultipleBadQuality() {
        SleepAnalyzer analyzer = new BadQualitySessionsCount();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 30),
                        SleepQuality.BAD
                )
        );
        assertEquals(2L, analyzer.analyze(sessions));
    }

    // ========== Тесты для SleeplessNightsCount ==========

    @Test
    public void testSleeplessNightsCountEmptyList() {
        SleepAnalyzer analyzer = new SleeplessNightsCount();
        assertEquals(0L, analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testSleeplessNightsCountNoSleeplessNights() {
        SleepAnalyzer analyzer = new SleeplessNightsCount();
        // Все ночи со сном
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        // Ночной сон (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        // Ночной сон (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(0L, analyzer.analyze(sessions));
    }

    @Test
    public void testSleeplessNightsCountWithSleeplessNights() {
        SleepAnalyzer analyzer = new SleeplessNightsCount();
        // Утренний сон - означает бессонную ночь
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        // Утренний сон (ночь 01.10 бессонная)
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        // Дневной сон (ночь 02.10 бессонная)
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(2L, analyzer.analyze(sessions));
    }

    @Test
    public void testSleeplessNightsCountMixed() {
        SleepAnalyzer analyzer = new SleeplessNightsCount();
        // Смесь ночных и дневных сессий
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        // Ночной сон (ночь 01.10 со сном)
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        // Дневной сон (ночь 02.10 бессонная)
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        // Ночной сон (ночь 03.10 со сном)
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(1L, analyzer.analyze(sessions));
    }

    // ========== Тесты для DetermineChronotype ==========

    @Test
    public void testDetermineChronotypeEmptyList() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        assertEquals("Недостаточно данных", analyzer.analyze(Collections.emptyList()));
    }

    @Test
    public void testDetermineChronotypeLark() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        // Жаворонок (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 20, 30),
                        // Жаворонок (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Жаворонок", analyzer.analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeOwl() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        // Сова (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 0, 15),
                        // Сова (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Сова", analyzer.analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeDove() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        // Голубь (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        // Голубь (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Голубь", analyzer.analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeTieBreakToDove() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        // Ничья между Жаворонком и Совой -> Голубь
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        // Жаворонок (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        // Сова (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Голубь", analyzer.analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeMultipleSessionsInOneNight() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        // Несколько сессий в одну ночь - должны агрегироваться
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        // Сова (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 4, 0),
                        // Продолжение сна (ночь 01.10)
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        // Сова (ночь 02.10)
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Сова", analyzer.analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeOnlyDaytimeSessions() {
        SleepAnalyzer analyzer = new DetermineChronotype();
        // Только дневные сессии - недостаточно данных
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        // Дневной сон
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 13, 0),
                        // Дневной сон
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Недостаточно данных", analyzer.analyze(sessions));
    }

    // ========== Тесты для SleepingSession ==========

    @Test
    public void testSleepingSessionGetters() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 1, 22, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 6, 0);
        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        assertEquals(start, session.getSleepStart());
        assertEquals(end, session.getSleepEnd());
        assertEquals(SleepQuality.GOOD, session.getQuality());
        assertEquals(480L, session.getDurationMinutes());
    }

    @Test
    public void testIntersectsNightDaytimeSleep() {
        // Дневной сон не пересекает ночь
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 15, 0),
                SleepQuality.GOOD
        );
        assertFalse(session.intersectsNight(LocalDate.of(2025, 10, 1)));
    }

    @Test
    public void testIntersectsNightMorningSleep() {
        // Утренний сон не пересекает ночь
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 7, 0),
                LocalDateTime.of(2025, 10, 1, 11, 0),
                SleepQuality.GOOD
        );
        assertFalse(session.intersectsNight(LocalDate.of(2025, 10, 1)));
    }

    @Test
    public void testIntersectsNightNightSleep() {
        // Ночной сон пересекает ночь
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 3, 0),
                SleepQuality.GOOD
        );
        assertFalse(session.intersectsNight(LocalDate.of(2025, 10, 1)));
        // Не пересекает ночь 01.10
        assertTrue(session.intersectsNight(LocalDate.of(2025, 10, 2)));
        // Пересекает ночь 02.10
    }

    @Test
    public void testIntersectsNightSleepCrossingMidnight() {
        // Сон через полночь
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 20, 0),
                LocalDateTime.of(2025, 10, 2, 2, 0),
                SleepQuality.GOOD
        );
        assertFalse(session.intersectsNight(LocalDate.of(2025, 10, 1)));
        // Не пересекает ночь 01.10
        assertTrue(session.intersectsNight(LocalDate.of(2025, 10, 2)));
        // Пересекает ночь 02.10
    }

    @Test
    public void testIntersectsNightEarlyMorningSleep() {
        // Сон ранним утром (1:00-5:00)
        SleepingSession session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 1, 0),
                LocalDateTime.of(2025, 10, 1, 5, 0),
                SleepQuality.GOOD
        );
        assertTrue(session.intersectsNight(LocalDate.of(2025, 10, 1)));
        // Пересекает ночь 01.10
    }

    // ========== Комплексный тест ==========

    @Test
    public void testComplexScenario() {
        // Комплексный сценарий со всеми типами данных
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        // Голубь, 480 мин, GOOD
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 30),
                        // Дневной сон, 50 мин, NORMAL
                        LocalDateTime.of(2025, 10, 2, 15, 20),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        // Голубь, 480 мин, BAD
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 0),
                        // Жаворонок, 540 мин, GOOD
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.GOOD
                )
        );

        assertEquals(4, new TotalSessionsCount().analyze(sessions));
        assertEquals(50L, new MinDuration().analyze(sessions));
        assertEquals(540L, new MaxDuration().analyze(sessions));
        assertEquals(387.5, (Double) new AverageDuration().analyze(sessions), 0.1);
        assertEquals(1L, new BadQualitySessionsCount().analyze(sessions));
        assertEquals(0L, new SleeplessNightsCount().analyze(sessions));
        assertEquals("Голубь", new DetermineChronotype().analyze(sessions));
    }
}