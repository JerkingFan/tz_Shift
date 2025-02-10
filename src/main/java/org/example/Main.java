package org.example;

import org.example.inputoutput.DataPrinter;
import org.example.inputoutput.DataWriter;
import org.example.inputoutput.ReadFromFile;
import org.example.object.Employee;
import org.example.object.Manager;
import org.example.dataparser.DataParser;
import org.example.sorting.Sort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Map<String, String> params = parseArguments(args);
            String inputFile = params.get("--input");
            String sortBy = params.getOrDefault("--sort", null);
            String order = params.getOrDefault("--order", "asc");
            String output = params.get("--output");
            String outputPath = params.get("--path");

            if (inputFile == null) {
                System.err.println("Error: Input file is required.");
                return;
            }

            if (!Files.exists(Path.of(inputFile))) {
                System.err.println("Error: Input file does not exist.");
                return;
            }

            List<String> lines = ReadFromFile.readFile(inputFile);
            List<Employee> employees = new ArrayList<>();
            List<Manager> managers = new ArrayList<>();
            List<String> invalidData = new ArrayList<>();

            DataParser.parsingData(lines, employees, managers, invalidData);

            if (sortBy != null) {
                Sort.sortEmployees(employees, sortBy, order);
            }

            if ("file".equalsIgnoreCase(output) && outputPath != null) {
                DataWriter.writeOutputToFile(employees, managers, invalidData, outputPath);
            } else {
                DataPrinter.printOutputToConsole(employees, managers, invalidData);
            }

        } catch (Exception e) {
            System.err.println("Critical error: " + e.getMessage());
        }
    }
    /**
     * Метод для разбирания аргументов командной строки и сохранения их в виде ключ-значение.
     *
     * @param args       Список аргументов командной строки.
     *
     */
    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i];
                String value = (i + 1 < args.length && !args[i + 1].startsWith("--")) ? args[++i] : null;
                params.put(key, value);
            }
        }
        return params;
    }
}
