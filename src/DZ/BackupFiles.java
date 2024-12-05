package DZ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BackupFiles {

    // Метод для создания резервной копии файлов из заданной директории
    public static void createBackup(String sourceDir) {
        File sourceDirectory = new File(sourceDir); // Создаем объект File для исходной директории
        File backupDirectory = new File("backup"); // Создаем объект File для директории бэкапа

        // Проверка, существует ли каталог
        if (!sourceDirectory.isDirectory()) { // Если указанный путь не является директорией
            System.out.println("Указанный путь не является директорией: " + sourceDir); // Выводим сообщение об ошибке
            return; // Завершаем метод
        }

        // Создаем каталог для резервной копии, если он не существует
        if (!backupDirectory.exists()) { // Если директория бэкапа не существует
            backupDirectory.mkdir(); // Создаем папку "backup"
        }

        // Получаем список всех файлов в исходной директории
        File[] files = sourceDirectory.listFiles(); // Получаем массив файлов и подкаталогов в исходной директории

        if (files != null) { // Проверяем, что массив не равен null
            for (File file : files) { // Перебираем каждый файл и подкаталог
                if (file.isFile()) { // Проверяем, что это файл, а не папка
                    try {
                        Path sourcePath = file.toPath(); // Получаем путь для исходного файла, преобразуем файл в пат, представляющий путь к папке, для дальнейшей работы
                        Path backupPath = backupDirectory.toPath().resolve(file.getName()); // Создаем путь для файла резервной копии
                        // Копируем файл из исходной директории в директорию резервной копии, перезаписывая, если файл уже существует
                        Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован: " + file.getName()); // Выводим сообщение об успешном копировании файла
                    } catch (IOException e) { // Обрабатываем исключение, если возникает ошибка при копировании
                        System.out.println("Ошибка при копировании файла: " + file.getName()); // Выводим сообщение об ошибке
                        e.printStackTrace(); // Печатаем стек вызовов для отладки
                    }
                }
            }
        } else { // Если массив files равен null, это значит, что директория пуста или не найдены файлы
            System.out.println("Директория пуста или не найдены файлы."); // Выводим сообщение об этом
        }
    }

    // Главный метод программы
    public static void main(String[] args) {
        // Укажите путь к директории, которую хотите создать резервную копию
        String sourceDir = "src/DZ"; // Замените этот путь на ваш
        createBackup(sourceDir); // Вызываем метод создания резервной копии
    }
}