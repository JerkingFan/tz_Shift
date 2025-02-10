package org.example.inputoutput;

import org.example.object.Employee;
import org.example.object.Manager;

import java.util.*;

public class DataPrinter {
    /**
     * Метод группирует сотрудников по менеджерам, сортирует данные и выводит их в консоль.
     * Отображает некорректные данные, если они есть.
     *
     * @param employees   Список для хранения корректных данных о сотрудниках.
     * @param managers    Список для хранения корректных данных о менеджерах.
     * @param invalidData Список для хранения строк с некорректными данными.
     */
    public static void printOutputToConsole(List<Employee> employees, List<Manager> managers, List<String> invalidData) {
        // Группировка сотрудников по менеджерам
        Map<Integer, List<Employee>> employeesByManager = new HashMap<>();
        for (Employee employee : employees) {
            employeesByManager
                    .computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>())
                    .add(employee);
        }

        // Сортируем менеджеров по отделу
        managers.sort(Comparator.comparing(Manager::getDepartment));

        for (Manager manager : managers) {
            System.out.println("\n=== " + manager.getDepartment() + " ===");
            System.out.println(manager);

            List<Employee> managedEmployees = employeesByManager.getOrDefault(manager.getId(), new ArrayList<>());

            double totalSalary = 0;
            for (Employee employee : managedEmployees) {
                System.out.println("   " + employee);
                totalSalary += employee.getSalary();
            }

            double averageSalary = managedEmployees.isEmpty() ? 0 : totalSalary / managedEmployees.size();
            System.out.printf(">>> Number of employees: %d | Average salary: %.2f%n", managedEmployees.size(), averageSalary);
        }

        // Вывод ошибок
        if (!invalidData.isEmpty()) {
            System.out.println("\n Incorrect data:");
            for (String data : invalidData) {
                System.out.println("   " + data);
            }
        }
    }
}
