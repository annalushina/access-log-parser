import java.io.File;
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
        }
    }
}