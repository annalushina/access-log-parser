import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        List<LogEntry> logEntrieList = new ArrayList<>();


        while (true) {
            int totalRequests = 0;
            int googleRequests = 0;
            int yandexRequests = 0;
            System.out.print("Введите путь к файлу: ");

            String path = scanner.nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isFile = file.isFile();

            if (!fileExists) {
                System.out.println("Файл не существует.");
                continue;
            }

            if (!isFile) {
                System.out.println("Указанный путь ведет к папке, а не к файлу.");
                continue;
            }

            counter++;
            System.out.println("Путь указан верно. Это файл номер " + counter);

            Statistics statistics = new Statistics();

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    totalRequests++;

                    // Проверка длины строки
                    if (line.length() > 1024) {
                        throw new RuntimeException("Ошибка!!! Строка длиннее 1024 символов: " + line.length());
                    }

                    LogEntry logEntry = new LogEntry(line);
                    logEntrieList.add(logEntry);

                    statistics.addEntry(logEntry);


                    if (line != null) {
                        if (line.contains("Googlebot")) {
                            googleRequests++;
                        } else if (line.contains("YandexBot")) {
                            yandexRequests++;
                        }
                    }
                }
                System.out.println("Общее количество строк в файле: " + totalRequests);

                double googlePercent = (double) googleRequests / totalRequests * 100;
                double yandexPercent = (double) yandexRequests / totalRequests * 100;

                System.out.println("Количество запросов от Googlebot в процентном соотношении от общего числа запросов составляет: " + googlePercent + "%");
                System.out.println("Количество запросов от YandexBot в процентном соотношении от общего числа запросов составляет: " + yandexPercent + "%");
                System.out.println("Средний объём трафика сайта за час: " + statistics.getTrafficRate() + " байт/час");
                System.out.println("Среднее количество посещений сайта за час: " + statistics.countVisitsPerHour());
                System.out.println("Среднее количество ошибочных запросов в час: " + statistics.countErrorRequestsPerHour());
                System.out.println("Средняя посещаемость одним пользователем: " + statistics.countVisitsPerUser());
                //  System.out.println("Список всех существующих страниц: " + statistics.countExistingPages());
                System.out.println("Статистика операционных систем: " + statistics.getOsStatistics());
                bufferedReader.close();
                fileReader.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
