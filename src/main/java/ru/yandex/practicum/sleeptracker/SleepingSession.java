package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality quality;
    private final long durationMinutes;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, SleepQuality quality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.quality = quality;
        this.durationMinutes = ChronoUnit.MINUTES.between(sleepStart, sleepEnd);
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    // Проверяет, пересекает ли сессия указанную ночь (ночь с nightDate на nightDate+1: 00:00 до 06:00 следующего дня)
    public boolean intersectsNight(LocalDate nightDate) {
        // Утренняя часть ночи: 00:00-06:00 указанной даты
        LocalDateTime morningStart = nightDate.atStartOfDay(); // 00:00
        LocalDateTime morningEnd = nightDate.atTime(6, 0); // 06:00

        // Сон пересекает утреннюю часть ночи, если:
        // 1. Он заканчивается после 00:00 И начинается до 06:00

        return sleepStart.isBefore(morningEnd) && sleepEnd.isAfter(morningStart);
    }

}