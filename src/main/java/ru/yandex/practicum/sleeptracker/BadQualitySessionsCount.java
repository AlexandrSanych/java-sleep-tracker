package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualitySessionsCount implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        return sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
    }
}