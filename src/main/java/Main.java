import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;

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

            try  {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    totalRequests++;
                    String userAgent = extractUserAgent(line);

                    if (line.length() > 1024) {
                        throw new RuntimeException("Ошибка!!! Строка длиннее 1024 символов: " + line.length());

                    }
                    if (userAgent != null) {
                        if (userAgent.contains("Googlebot")) {
                            googleRequests++;
                        } else if (userAgent.contains("YandexBot")) {
                            yandexRequests++;
                        }
                    }

                }
                System.out.println("Общее количество строк в файле: " + totalRequests);

                double googlePercent = (double) googleRequests / totalRequests * 100;
                double yandexPercent = (double) yandexRequests / totalRequests * 100;

                System.out.println("Количество заросов от Googlebot в процентном соотношении от общего числа запросов составляет: " + googlePercent + "%");
                System.out.println("Количество заросов от YandexBot в процентном соотношении от общего числа запросов составляет: " + yandexPercent + "%");
                bufferedReader.close();
                fileReader.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }


        }
    }

    private static String extractUserAgent(String log) {
        // Пример строки лога: 37.231.123.209 - - [15/May/2015:19:07:32 +0200] "GET / HTTP/1.1" 200 150 "-"
        // "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"
        String[] parts = log.split("\"");
        if (parts.length >= 6) {
            String userAgent = parts[parts.length-1];
            String[] brackets = userAgent.split("\\(");
            if (brackets.length >= 2) {
                String firstBrackets = brackets[brackets.length-1].split("\\)")[0];
                String[] fragments = firstBrackets.split(";");
                if (fragments.length >= 2) {
                    String fragment = fragments[1].trim();
                    String[] botInfo = fragment.split("/");
                    if (botInfo.length >= 1) {
                        return botInfo[0].trim();
                    }
                }
            }
        }
        return null;
    }
}
