package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SleepTrackerApp {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Пожалуйста, укажите путь к файлу с логом сна");
            return;
        }

        String filePath = args[0];
        List<SleepingSession> sessions = readSleepSessions(filePath);

        if (sessions.isEmpty()) {
            System.out.println("Файл пуст или не содержит корректных данных");
            return;
        }

        // Список аналитических функций
        List<SleepAnalyzer> analyzers = new ArrayList<>();
        analyzers.add(BasicAnalyzers.totalSessionsCount());
        analyzers.add(BasicAnalyzers.minDuration());
        analyzers.add(BasicAnalyzers.maxDuration());
        analyzers.add(BasicAnalyzers.averageDuration());
        analyzers.add(BasicAnalyzers.badQualitySessionsCount());
        analyzers.add(BasicAnalyzers.sleeplessNightsCount());
        analyzers.add(BasicAnalyzers.determineChronotype());

        // Выводим результаты анализа
        System.out.println("=== Анализ сна ===");
        System.out.println("Период анализа: " +
                sessions.get(0).getSleepStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " - " +
                sessions.get(sessions.size() - 1).getSleepEnd().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        System.out.println();

        String[] analyzerNames = {
                "Общее количество сессий сна",
                "Минимальная продолжительность сна (мин)",
                "Максимальная продолжительность сна (мин)",
                "Средняя продолжительность сна (мин)",
                "Количество сессий с плохим качеством сна",
                "Количество бессонных ночей",
                "Хронотип пользователя"
        };

        // Используем IntStream вместо традиционного цикла for
        IntStream.range(0, analyzers.size())
                .forEach(i -> {
                    Object result = analyzers.get(i).analyze(sessions);
                    System.out.println(analyzerNames[i] + ": " + result);
                });
    }

    // Читает сессии сна из файла
    private static List<SleepingSession> readSleepSessions(String filePath) {
        List<SleepingSession> sessions = new ArrayList<>();

        try {
            Files.lines(Paths.get(filePath))
                    .filter(line -> !line.trim().isEmpty())
                    .forEach(line -> {
                        try {
                            String[] parts = line.split(";");
                            if (parts.length != 3) {
                                System.err.println("Некорректный формат строки: " + line);
                                return;
                            }

                            LocalDateTime sleepStart = LocalDateTime.parse(parts[0], DATE_TIME_FORMATTER);
                            LocalDateTime sleepEnd = LocalDateTime.parse(parts[1], DATE_TIME_FORMATTER);
                            SleepQuality quality = SleepQuality.valueOf(parts[2].trim());

                            sessions.add(new SleepingSession(sleepStart, sleepEnd, quality));
                        } catch (Exception e) {
                            System.err.println("Ошибка при чтении строки: " + line);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return sessions;
    }
}