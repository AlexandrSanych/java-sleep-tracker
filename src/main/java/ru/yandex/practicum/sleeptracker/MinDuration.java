package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDuration implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0L);
    }
}