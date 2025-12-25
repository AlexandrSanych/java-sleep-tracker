package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    // ========== Тесты для totalSessionsCount() ==========

    @Test
    public void testTotalSessionsCountEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0, BasicAnalyzers.totalSessionsCount().analyze(sessions));
    }

    @Test
    public void testTotalSessionsCountMultipleSessions() {
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
        assertEquals(2, BasicAnalyzers.totalSessionsCount().analyze(sessions));
    }

    // ========== Тесты для minDuration() ==========

    @Test
    public void testMinDurationEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0L, BasicAnalyzers.minDuration().analyze(sessions));
    }

    @Test
    public void testMinDurationMultipleSessions() {
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
        assertEquals(60L, BasicAnalyzers.minDuration().analyze(sessions));
    }

    // ========== Тесты для maxDuration() ==========

    @Test
    public void testMaxDurationEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0L, BasicAnalyzers.maxDuration().analyze(sessions));
    }

    @Test
    public void testMaxDurationMultipleSessions() {
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
        assertEquals(480L, BasicAnalyzers.maxDuration().analyze(sessions));
    }

    // ========== Тесты для averageDuration() ==========

    @Test
    public void testAverageDurationEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0.0, BasicAnalyzers.averageDuration().analyze(sessions));
    }

    @Test
    public void testAverageDurationMultipleSessions() {
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
        assertEquals(340.0, (Double) BasicAnalyzers.averageDuration().analyze(sessions), 0.01);
    }

    // ========== Тесты для badQualitySessionsCount() ==========

    @Test
    public void testBadQualitySessionsCountEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0L, BasicAnalyzers.badQualitySessionsCount().analyze(sessions));
    }

    @Test
    public void testBadQualitySessionsCountMultipleSessions() {
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
        assertEquals(2L, BasicAnalyzers.badQualitySessionsCount().analyze(sessions));
    }

    // ========== Тесты для sleeplessNightsCount() ==========

    @Test
    public void testIntersectsNight() {
        // Тест 1: Дневной сон 14:00-15:00 НЕ должен пересекать ночь 01.10
        SleepingSession daySession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 15, 0),
                SleepQuality.GOOD
        );
        assertFalse(daySession.intersectsNight(LocalDate.of(2025, 10, 1)));

        // Тест 2: Утренний сон 7:00-11:00 НЕ должен пересекать ночь 01.10
        SleepingSession morningSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 7, 0),
                LocalDateTime.of(2025, 10, 1, 11, 0),
                SleepQuality.GOOD
        );
        assertFalse(morningSession.intersectsNight(LocalDate.of(2025, 10, 1)));

        // Тест 3: Ночной сон 23:00-3:00 должен пересекать ночь 02.10
        SleepingSession nightSession1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 3, 0),
                SleepQuality.GOOD
        );
        assertFalse(nightSession1.intersectsNight(LocalDate.of(2025, 10, 1)));
        assertTrue(nightSession1.intersectsNight(LocalDate.of(2025, 10, 2)));
    }

    @Test
    public void testSleeplessNightsCountFirstSleepBeforeNoon() {
        // Тест 1: Утренний сон 7:00-11:00 (начинается до 12:00)
        List<SleepingSession> sessions1 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(1L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions1));
    }

    @Test
    public void testSleeplessNightsCountFirstSleepAfterNoon() {
        // Тест 2: Ночной сон 23:00-3:00 (начинается после 12:00)
        List<SleepingSession> sessions2 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(0L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions2));
    }

    @Test
    public void testSleeplessNightsCountSleepCrossingMidnight() {
        // Тест 3: Сон 20:00-2:00 (начинается после 12:00)
        List<SleepingSession> sessions3 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 20, 0),
                        LocalDateTime.of(2025, 10, 2, 2, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(0L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions3));
    }

    @Test
    public void testSleeplessNightsCountMultipleSessions() {
        // Тест 4: Несколько сессий сна
        List<SleepingSession> sessions4 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        LocalDateTime.of(2025, 10, 3, 11, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(1L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions4));
    }

    @Test
    public void testSleeplessNightsCountDaytimeSleepOnly() {
        // Тест 5: Дневной сон после 12:00
        List<SleepingSession> sessions5 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(0L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions5));
    }

    @Test
    public void testSleeplessNightsCountTwoSleeplessNights() {
        // Тест 6: Две ночи, обе бессонные
        List<SleepingSession> sessions6 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(2L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions6));
    }

    @Test
    public void testSleeplessNightsCountCrossMonthBoundary() {
        // Тест 7: Пересечение границы месяца
        List<SleepingSession> sessions7 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 9, 30, 23, 0),
                        LocalDateTime.of(2025, 10, 1, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        LocalDateTime.of(2025, 10, 3, 11, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals(1L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions7));
    }

    @Test
    public void testSleeplessNightsCountEmptyList() {
        // Тест 8: Пустой список сессий
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals(0L, BasicAnalyzers.sleeplessNightsCount().analyze(sessions));
    }

    // ========== Тесты для determineChronotype() ==========

    @Test
    public void testDetermineChronotypeEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();
        assertEquals("Недостаточно данных", BasicAnalyzers.determineChronotype().analyze(sessions));
    }

    @Test
    public void testDetermineChronotypeLark() {
        // Тест 1: Жаворонок - сон с 21:00 до 6:00
        List<SleepingSession> sessions1 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 20, 30),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Жаворонок", BasicAnalyzers.determineChronotype().analyze(sessions1));
    }

    @Test
    public void testDetermineChronotypeOwl() {
        // Тест 2: Сова - сон с 23:30 до 9:30
        List<SleepingSession> sessions2 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 0, 15),
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Сова", BasicAnalyzers.determineChronotype().analyze(sessions2));
    }

    @Test
    public void testDetermineChronotypeDove() {
        // Тест 3: Голубь - промежуточное время
        List<SleepingSession> sessions3 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Голубь", BasicAnalyzers.determineChronotype().analyze(sessions3));
    }

    @Test
    public void testDetermineChronotypeMixedWithDaytimeSessions() {
        // Тест 4: Смешанные данные с дневными сессиями
        List<SleepingSession> sessions4 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 45),
                        LocalDateTime.of(2025, 10, 3, 8, 45),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 13, 30),
                        LocalDateTime.of(2025, 10, 3, 14, 15),
                        SleepQuality.GOOD
                )
        );
        assertEquals("Сова", BasicAnalyzers.determineChronotype().analyze(sessions4));
    }

    @Test
    public void testDetermineChronotypeTieBreak() {
        // Тест 5: Ничья между типами -> Голубь
        List<SleepingSession> sessions5 = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0), // Жаворонок
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30), // Сова
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 30), // Голубь
                        LocalDateTime.of(2025, 10, 4, 7, 30),
                        SleepQuality.GOOD
                )
        );
        // Должен вернуть "Голубь" по правилу ничьей
        assertEquals("Голубь", BasicAnalyzers.determineChronotype().analyze(sessions5));
    }

    @Test
    public void testBasicAnalyzers() {
        // Комплексный тест нескольких функций
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 30),
                        LocalDateTime.of(2025, 10, 2, 15, 20),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.BAD
                )
        );

        assertEquals(3, BasicAnalyzers.totalSessionsCount().analyze(sessions));
        assertEquals(50L, BasicAnalyzers.minDuration().analyze(sessions));
        assertEquals(480L, BasicAnalyzers.maxDuration().analyze(sessions));
        assertEquals(336.7, (Double) BasicAnalyzers.averageDuration().analyze(sessions), 0.1);
        assertEquals(1L, BasicAnalyzers.badQualitySessionsCount().analyze(sessions));
    }
}