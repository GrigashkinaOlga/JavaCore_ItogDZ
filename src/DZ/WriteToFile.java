package DZ;


import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToFile {

        public static void main(String[] args) throws IOException {
            // Пример массива состояний ячеек (9 элементов)
            int[] arr = {1, 1, 2, 0, 0, 3, 1, 2, 0}; // 0 - пусто, 1 - крестик, 2 - нолик, 3 - резерв


            // Запись в файл
            try (FileOutputStream fos = new FileOutputStream("text4.txt")) {
                for (int b = 0; b < 3; b++) {
                    byte wr = 0;
                    for (int v = 0; v < 3; v++) {
                        wr += (byte) (arr[3 * b + v] << (v * 2));
                        }
                    fos.write(wr);
                    }
                fos.flush();
            }
            System.out.println("Данные записаны в файл.");
        }
}

