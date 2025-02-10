package org.example.dataparser;

import org.example.object.Employee;
import org.example.object.Manager;

import java.util.List;

public class DataParser {
    /**
     * Метод для парсинга данных из строк файла.
     * Разделяет строки на корректные данные (сотрудники и менеджеры) и некорректные данные.
     *
     * @param lines       Список строк из входного файла.
     * @param employees   Список для хранения корректных данных о сотрудниках.
     * @param managers    Список для хранения корректных данных о менеджерах.
     * @param invalidData Список для хранения строк с некорректными данными.
     */
    public static void parsingData(List<String> lines, List<Employee> employees, List<Manager> managers, List<String> invalidData) {
        for (String line : lines) {
            String[] parts = line.split(",");

            // Проверка на количество элементов в строке. Если их не 5, строка считается некорректной.
            if (parts.length != 5) {
                invalidData.add(line);
                continue;
            }

            // Извлекаем и очищаем данные от лишних пробелов.
            String role = parts[0].trim();
            int id = parseInt(parts[1].trim(), line, invalidData);
            if (id == -1) continue;  // Если ID невалидный, строка пропускается.

            String name = parts[2].trim();
            double salary = parseDouble(parts[3].trim(), line, invalidData);
            if (salary == -1) continue;  // Если зарплата невалидная, строка пропускается.

            String departmentOrManagerId = parts[4].trim();

            // Обработка данных в зависимости от роли (Manager или Employee).
            if ("Manager".equals(role)) {
                managers.add(new Manager(id, name, salary, departmentOrManagerId));
            } else if ("Employee".equals(role)) {
                int managerId = parseInt(departmentOrManagerId, line, invalidData);
                if (managerId == -1) continue;  // Если managerId невалидный, строка пропускается.
                employees.add(new Employee(id, name, salary, managerId));
            } else {
                invalidData.add(line);  // Если роль не соответствует ожидаемым, строка считается некорректной.
            }
        }
    }

    /**
     * Метод для парсинга строки в целое число.
     * Если парсинг невозможен, строка добавляется в список некорректных данных.
     *
     * @param value       Строка, которую нужно преобразовать в число.
     * @param line        Исходная строка для добавления в список некорректных данных в случае ошибки.
     * @param invalidData Список для хранения некорректных данных.
     * @return Преобразованное число или -1, если парсинг не удался.
     */
    private static int parseInt(String value, String line, List<String> invalidData) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            invalidData.add(line);
            return -1;  // Возвращаем -1, чтобы указать на невалидность данных.
        }
    }

    /**
     * Метод для парсинга строки в число с плавающей точкой.
     * Если парсинг невозможен или зарплата отрицательная/нулевая, строка добавляется в список некорректных данных.
     *
     * @param value       Строка, которую нужно преобразовать в число.
     * @param line        Исходная строка для добавления в список некорректных данных в случае ошибки.
     * @param invalidData Список для хранения некорректных данных.
     * @return Преобразованное число или -1, если парсинг не удался.
     */
    private static double parseDouble(String value, String line, List<String> invalidData) {
        try {
            double result = Double.parseDouble(value);
            if (result <= 0) {  // Зарплата должна быть положительной.
                invalidData.add(line);
                return -1;  // Возвращаем -1, чтобы указать на невалидность данных.
            }
            return result;
        } catch (NumberFormatException e) {
            invalidData.add(line);
            return -1;
        }
    }
}