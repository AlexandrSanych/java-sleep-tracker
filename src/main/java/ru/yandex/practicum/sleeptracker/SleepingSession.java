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

    // Определяет тип ночи для хронотипа
    public String getChronotypeForNight() {
        // Проверяем, является ли это ночной сессией (пересекает ночь 00:00-06:00)
        LocalDate sleepDate = sleepStart.toLocalDate();
        if (!intersectsNight(sleepDate) && !intersectsNight(sleepDate.plusDays(1))) {
            return ""; // Дневной сон или не пересекает ночь - игнорируем
        }

        int sleepHour = sleepStart.getHour();
        int sleepMinute = sleepStart.getMinute();
        int wakeHour = sleepEnd.getHour();
        int wakeMinute = sleepEnd.getMinute();

        int sleepTime = sleepHour * 60 + sleepMinute;
        int wakeTime = wakeHour * 60 + wakeMinute;

        boolean isOwl, isLark;

        if (wakeTime < sleepTime) {
            // Сон через полночь
            isOwl = sleepTime >= 23 * 60 && (wakeTime + 24 * 60) >= 9 * 60;
            isLark = sleepTime < 22 * 60 && wakeTime < 7 * 60;
        } else {
            // Сон в пределах одних суток
            // Для засыпания между 0:00 и 6:00 - корректируем
            if (sleepTime < 6 * 60) {
                sleepTime += 24 * 60;
                wakeTime += 24 * 60;
            }
            isOwl = sleepTime >= 23 * 60 && wakeTime >= 9 * 60;
            isLark = sleepTime < 22 * 60 && wakeTime < 7 * 60;
        }

        return isOwl ? "Сова" : (isLark ? "Жаворонок" : "Голубь");
    }
}