package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<String> rows;

    public Column(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return rows.size();
    }

    public String getRowValue(int index) {
        return rows.get(index);
    }

    public void setRowValue(int index, String value) {
        rows.set(index, value);
    }

    public void addRowValue(String value) {
        rows.add(value);
    }

    public List<String> getRows() { return rows; }

    public static void main(String[] args) {
        // Create a new Column named "TestColumn"
        Column testColumn = new Column("TestColumn");

        // Add some values to the column
        testColumn.addRowValue("Value1");
        testColumn.addRowValue("Value2");
        testColumn.addRowValue("Value3");

        // Display the initial state of the column
        System.out.println("Column Name: " + testColumn.getName());
        System.out.println("Column Size: " + testColumn.getSize());
        System.out.println("Values in the Column:");
        for (int i = 0; i < testColumn.getSize(); i++) {
            System.out.println("Row " + i + ": " + testColumn.getRowValue(i));
        }

        // Update a value in the column
        testColumn.setRowValue(1, "UpdatedValue");

        // Display the updated state of the column
        System.out.println("\nColumn Size after update: " + testColumn.getSize());
        System.out.println("Values in the Column after update:");
        for (int i = 0; i < testColumn.getSize(); i++) {
            System.out.println("Row " + i + ": " + testColumn.getRowValue(i));
        }
    }
}