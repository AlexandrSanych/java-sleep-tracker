package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxDuration implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0L);
    }
}