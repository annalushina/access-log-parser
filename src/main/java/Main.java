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

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int totalLines = 0;
                int maxLength = Integer.MIN_VALUE;
                int minLength = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    totalLines++;

                    if (length > maxLength) {
                        maxLength = length;
                    }

                    if (length < minLength) {
                        minLength = length;
                    }

                    if (length > 1024) {
                        throw new RuntimeException("Ошибка!!! Строка длиннее 1024 символов: " + length);
                    }
                }

                System.out.println("Общее количество строк в файле: " + totalLines);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);

                reader.close();
                fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}