package org.example.object;

import java.util.Objects;

public class Manager {
    private final int id;
    private final String name;
    private final double salary;
    private final String department;

    public Manager(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return String.format("Manager{id=%d, name='%s', salary=%.2f, department='%s'}",
                id, name, salary, department);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                Double.compare(manager.salary, salary) == 0 &&
                Objects.equals(name, manager.name) &&
                Objects.equals(department, manager.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, department);
    }
}
