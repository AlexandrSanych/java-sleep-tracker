package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AverageDuration implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0.0);
    }
}