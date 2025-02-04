
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Введите число");
        int number1= new Scanner(System.in).nextInt();
        System.out.println("Введите второе число");
        int number2= new Scanner(System.in).nextInt();
        System.out.println("сумма чисел равна: "+ (number1+number2));
        System.out.println("разность чисел равна: "+ (number1-number2));
        System.out.println("произведение чисел равно: "+ (number1*number2));
        System.out.println("частное чисел равно: "+ ((double)number1/number2));
    }
}
