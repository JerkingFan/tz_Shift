package org.example.sorting;

import org.example.object.Employee;
import java.util.Comparator;
import java.util.List;

public class Sort {
    /**
     * Метод сортирует список сотрудников по имени или зарплате.
     *
     * @param employees   Список для хранения корректных данных о сотрудниках.
     * @param sortBy    Поле для сортировки.
     * @param order Порядок сортировки.
     */
    public static void sortEmployees(List<Employee> employees, String sortBy, String order) {
        if (employees == null || employees.isEmpty()) {
            return;
        }

        sortBy = sortBy.trim().toLowerCase();
        order = (order != null) ? order.trim().toLowerCase() : "asc";

        Comparator<Employee> comparator;

        if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getName);
        } else if ("salary".equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else {
            System.err.println("Error: Incorrect sorting parameter — " + sortBy);
            return;
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        }

        employees.sort(comparator);
    }
}
