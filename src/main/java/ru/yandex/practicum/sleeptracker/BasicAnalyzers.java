package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicAnalyzers {

    public static SleepAnalyzer totalSessionsCount() {
        return sessions -> sessions.size();
    }

    public static SleepAnalyzer minDuration() {
        return sessions -> sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0L);
    }

    public static SleepAnalyzer maxDuration() {
        return sessions -> sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0L);
    }

    public static SleepAnalyzer averageDuration() {
        return sessions -> sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0.0);
    }

    public static SleepAnalyzer badQualitySessionsCount() {
        return sessions -> sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
    }

    public static SleepAnalyzer sleeplessNightsCount() {
        return sessions -> {
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
        };
    }
    public static SleepAnalyzer determineChronotype() {
        return sessions -> {
            if (sessions.isEmpty()) {
                return "Недостаточно данных";
            }

            // Считаем количество определений хронотипа для каждой сессии
            Map<String, Long> chronotypeCounts = sessions.stream()
                    .map(SleepingSession::getChronotypeForNight)
                    .filter(chronotype -> !chronotype.isEmpty())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            if (chronotypeCounts.isEmpty()) {
                return "Недостаточно данных";
            }

            // Находим максимальное количество
            Optional<Map.Entry<String, Long>> maxEntry = chronotypeCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            if (maxEntry.isEmpty()) {
                return "Недостаточно данных";
            }

            long maxCount = maxEntry.get().getValue();

            // Считаем, сколько типов имеют максимальное количество
            long numberOfMaxTypes = chronotypeCounts.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(maxCount))
                    .count();

            // Если больше одного типа имеет максимальное количество (ничья) -> Голубь
            if (numberOfMaxTypes > 1) {
                return "Голубь";
            }

            return maxEntry.get().getKey();
        };
    }
}