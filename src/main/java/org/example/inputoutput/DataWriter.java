package org.example.inputoutput;

import org.example.object.Employee;
import org.example.object.Manager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DataWriter {
    /**
     * Метод сохраняет данные о сотрудниках и менеджерах в указанный файл.
     * Отображает некорректные данные, если такие имеются.
     *
     * @param employees   Список для хранения корректных данных о сотрудниках.
     * @param managers    Список для хранения корректных данных о менеджерах.
     * @param invalidData Список для хранения строк с некорректными данными.
     */
    public static void writeOutputToFile(List<Employee> employees, List<Manager> managers, List<String> invalidData, String outputPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            // Группируем сотрудников по менеджерам
            Map<Integer, List<Employee>> employeesByManager = new HashMap<>();
            for (Employee employee : employees) {
                employeesByManager
                        .computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>())
                        .add(employee);
            }

            // Сортируем менеджеров по отделу
            managers.sort(Comparator.comparing(Manager::getDepartment));

            for (Manager manager : managers) {
                writer.println("\n=== " + manager.getDepartment() + " ===");
                writer.println(manager);

                List<Employee> managedEmployees = employeesByManager.getOrDefault(manager.getId(), new ArrayList<>());

                double totalSalary = 0;
                for (Employee employee : managedEmployees) {
                    writer.println("   " + employee);
                    totalSalary += employee.getSalary();
                }

                double averageSalary = managedEmployees.isEmpty() ? 0 : totalSalary / managedEmployees.size();
                writer.printf(">>> Number of employees: %d | Average salary: %.2f%n", managedEmployees.size(), averageSalary);
            }

            // Запись ошибок, если они есть
            if (!invalidData.isEmpty()) {
                writer.println("\n Incorrect data:");
                for (String data : invalidData) {
                    writer.println("   " + data);
                }
            }

            System.out.println("The data has been successfully written to the file: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error writing to a file: " + e.getMessage());
        }
    }
}
