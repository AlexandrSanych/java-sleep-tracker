package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DetermineChronotype implements SleepAnalyzer {

    @Override
    public Object analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return "Недостаточно данных";
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

        // 3. Если startNight после lastDate, недостаточно данных
        if (startNight.isAfter(lastDate)) {
            return "Недостаточно данных";
        }

        // 4. Для каждой ночи определяем хронотип
        List<String> nightChronotypes = startNight.datesUntil(lastDate.plusDays(1))
                .map(night -> getChronotypeForNight(sessions, night))
                .filter(chronotype -> !chronotype.isEmpty())
                .collect(Collectors.toList());

        if (nightChronotypes.isEmpty()) {
            return "Недостаточно данных";
        }

        // 5. Считаем количество определений хронотипа для каждой ночи
        Map<String, Long> chronotypeCounts = nightChronotypes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 6. Находим максимальное количество
        Optional<Map.Entry<String, Long>> maxEntry = chronotypeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (maxEntry.isEmpty()) {
            return "Недостаточно данных";
        }

        long maxCount = maxEntry.get().getValue();

        // 7. Считаем, сколько типов имеют максимальное количество
        long numberOfMaxTypes = chronotypeCounts.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxCount))
                .count();

        // 8. Если больше одного типа имеет максимальное количество (ничья) -> Голубь
        if (numberOfMaxTypes > 1) {
            return "Голубь";
        }

        return maxEntry.get().getKey();
    }

    // Определяет хронотип для конкретной ночи
    private String getChronotypeForNight(List<SleepingSession> sessions, LocalDate nightDate) {
        // Находим все сессии сна, которые пересекают указанную ночь
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(session -> session.intersectsNight(nightDate))
                .collect(Collectors.toList());

        if (nightSessions.isEmpty()) {
            return "";
        }


        Optional<LocalDateTime> minStart = nightSessions.stream()
                .map(SleepingSession::getSleepStart)
                .min(LocalDateTime::compareTo);
        Optional<LocalDateTime> maxEnd = nightSessions.stream()
                .map(SleepingSession::getSleepEnd)
                .max(LocalDateTime::compareTo);

        if (minStart.isEmpty() || maxEnd.isEmpty()) {
            return "";
        }

        LocalDateTime sleepStart = minStart.get();
        LocalDateTime sleepEnd = maxEnd.get();

        // Определяем хронотип по времени начала и окончания сна
        return determineChronotypeByTime(sleepStart, sleepEnd);
    }

    // Определяет хронотип по времени начала и окончания сна
    private String determineChronotypeByTime(LocalDateTime sleepStart, LocalDateTime sleepEnd) {
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