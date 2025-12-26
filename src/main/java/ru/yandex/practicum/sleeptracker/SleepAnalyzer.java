package ru.yandex.practicum.sleeptracker;

import java.util.List;


@FunctionalInterface
public interface SleepAnalyzer {
    Object analyze(List<SleepingSession> sessions);
}