package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsCount implements SleepAnalyzer {
    @Override
    public Object analyze(List<SleepingSession> sessions) {
        return sessions.size();
    }
}