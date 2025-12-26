package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.util.List;

public class SleeplessNightsCount implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0L;
        }

        // 1. Находим диапазон дат в логе
        LocalDate firstDate = sessions.stream()
                .map(s -> s.getSleepStart().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow();
        LocalDate lastDate = sessions.stream()
                .map(s -> s.getSleepEnd().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow();

        // 2. Определяем, с какой ночи начинать проверку
        LocalDate startNight = sessions.get(0).getSleepStart().getHour() < 12 ?
                firstDate : firstDate.plusDays(1);

        // 3. Если startNight после lastDate, возвращаем 0
        if (startNight.isAfter(lastDate)) {
            return 0L;
        }

        // 4. Проверяем все ночи от startNight до lastDate включительно
        return startNight.datesUntil(lastDate.plusDays(1))
                .filter(night -> sessions.stream()
                        .noneMatch(session -> session.intersectsNight(night)))
                .count();
    }
}