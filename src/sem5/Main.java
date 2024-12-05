package sem5;



import java.io.*;
import java.util.Arrays;


public class Main {
        public static void main(String[] args) throws IOException { // Главный метод, выбрасывает IOException при возникновении ошибки
                int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // Создание и инициализация массива целых чисел
                writeToFile1(writeZero(array)); // Вызов метода writeZero для преобразования массива и запись результата в файл
                writeToFile2(array);
                writeToFile3(array);
                readFile1();
                readFile2();

        }

        // СОЗДАТЬ МАССИВ из 9 цифр и записать его в файл, используя поток вывода (через "," и через "0")

        // вариант 1 (через ",")

        public static void writeToFile1(String arr) throws IOException { // Метод для записи строки в файл, выбрасывает IOException
                try (FileWriter fileWriter = new FileWriter("text1.txt")) { // Инициализация FileWriter (создает и записывает файл) внутри try-with-resources для автоматического закрытия
                        fileWriter.write(arr); // Запись строки в файл
                } // FileWriter автоматически закрывается в конце блока try
        }

        // вариант 1 (через "0")

        public static String writeZero(int[] arr){ // Метод для преобразования массива в строку с добавлением '0' между элементами
                StringBuilder sb = new StringBuilder(); // Создание объекта StringBuilder для накопления строки
                sb.append("[");
                // Цикл для перебора элементов массива, кроме последнего
                for (int i=0; i < arr.length - 1; i++) {
                        sb.append(arr[i]).append("0"); // Добавление текущего элемента массива и '0' в строку
                }
                sb.append(arr[arr.length-1]).append("]");
                return sb.toString(); // Преобразование StringBuilder в строку и возврат результата
        }

        // вариант 2 (через ",")

        public static void writeToFile2(int[] arr) throws IOException { // Метод для записи строки в файл, выбрасывает IOException
                final int DIGIT_BOUND = 48; // Константа, представляющая значение ASCII для символа '0'
                try (FileOutputStream fos = new FileOutputStream("text2.txt")) { // Создает объект FileOutputStream для записи данных в файл "text2.txt"
                        fos.write('[');
                        for (int i = 0; i < arr.length; i++) {
                                fos.write(DIGIT_BOUND + arr[i]);  // Записывает элемент массива в файл, предварительно конвертируя его в символ (ASCII + значение элемента)
                                // Если это не последний элемент массива, пишем запятую
                                if (i < arr.length - 1) fos.write(',');
                        }
                        fos.write(']');
                        fos.flush(); // Принудительно сбрасывает буфер, чтобы записать все данные в файл
                }

        }

        // вариант 2 (через "0")

        public static void writeToFile3(int[] arr) throws IOException {
                try (FileOutputStream fos = new FileOutputStream("text3.txt")) {
                        for (int i = 0; i < arr.length; i++) {
                                fos.write((arr[i] + "").getBytes()); // Преобразуем число в строку и затем в байты
                                // Если это не последний элемент, добавляем разделитель '0'
                                if (i < arr.length - 1) {
                                        fos.write("0".getBytes()); // Записываем разделитель '0'
                                }

                        }
                        fos.flush(); // Принудительно сбрасывает буфер, чтобы записать все данные в файл

                }
        }

        // СОЗДАТЬ МАССИВ целых чисел и заполнить его информацией из файла, записанного в предыдущем задании.
        // (через ",")

        public static void readFile1() throws IOException {
                int[] arr = new int[9]; // Инициализируем массив для хранения 9 целых чисел.
                final int DIGIT_BOUND = 48; // Задаем константу, представляющую символ '0' для последующего преобразования символа в его числовое значение.

                // Используем блок try-with-resources для автоматического закрытия FileInputStream после завершения работы.
                try (FileInputStream fis = new FileInputStream("text2.txt")) {
                        // Проверяем, соответствует ли первый символ ожидаемому значению '['.
                        if (fis.read() != '[') {
                                throw new IOException("Expected '[', but got something else."); // Если не так, выбрасываем исключение с сообщением об ошибке.
                        }

                        // Считываем 9 цифр из файла.
                        for (int i = 0; i < arr.length; i++) {
                                int digit = fis.read(); // Считываем следующий байт (символ) из файла.
                                if (digit == -1) { // Проверяем, достигли ли конца файла.
                                        throw new IOException("Unexpected end of file while reading digits."); // Если да, выбрасываем исключение.
                                }
                                arr[i] = digit - DIGIT_BOUND; // Преобразуем символ в его целочисленное значение.

                                // Считываем следующий символ, который должен быть запятой.
                                digit = fis.read();
                                if (digit == -1 && i < arr.length - 1) { // Проверяем, достигли ли конца файла на предпоследнем элементе (то есть ожидается, что после последней цифры нет запятой).
                                        throw new IOException("Unexpected end of file, expected more digits."); // Если да, выбрасываем исключение.
                                }
                        }
                } // Здесь FileInputStream будет автоматически закрыт.

                // Выводим массив в консоль.
                System.out.println(Arrays.toString(arr));
        }

        // (через "0")

        public static void readFile2() throws IOException { // Метод для преобразования текста из файла в массив
                int[] arr = new int[9]; // Инициализируем массив для хранения 9 целых чисел.

                try (FileInputStream fis = new FileInputStream("text3.txt")) { // Используем try-with-resources для автоматического закрытия ресурса.
                        int b; // Переменная для хранения считанного значения (байта).
                        int i = 0; // Индекс для отслеживания текущей позиции записи в массив.

                        // Читаем байты из файла до тех пор, пока не достигнем конца файла (читая возвращаемое значение -1).
                        while ((b = fis.read()) != -1) {
                                // Если считанный байт не равен нулю, добавляем его в массив.
                                if (b != 48 && i < arr.length) { // Проверяем, не превышен ли размер массива.
                                        arr[i++] = b - 48; // Записываем байт в массив и увеличиваем индекс.
                                }
                        }
                } // Конец блока try.

                // Выводим массив в консоль (т.е. печатаем его значения).
                System.out.println(Arrays.toString(arr));
        }
}
