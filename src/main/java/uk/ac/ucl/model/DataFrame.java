package uk.ac.ucl.model;

import java.util.*;

public class DataFrame {

    private ArrayList<Column> columns;
    private Map<String, Column> columnMap; //for accessing columns via name

    public DataFrame() {
        this.columns = new ArrayList<>();
        this.columnMap = new LinkedHashMap<>(); //linked hash map to maintain order of columns
    }

    public void addColumn(String columnName) {
        Column newColumn = new Column(columnName);
        columns.add(newColumn);
        columnMap.put(columnName, newColumn);
    }

    public List<String> getColumnNames() {
        return new ArrayList<>(columnMap.keySet());
    }

    public int getRowCount() {
        return columns.get(1).getSize(); //get size of first column
    }

    public String getValue(String columnName, int row) {
        Column column = columnMap.get(columnName);
        if (column != null && row <= column.getSize()) {
            return column.getRowValue(row);
        }
        else {
            throw new IllegalArgumentException("invalid column name or row index");
        }
    }

    public void putValue(String columnName, int row, String value) {
        Column column = columnMap.get(columnName);
        if (column != null && row <= column.getSize()) {
            column.setRowValue(row, value);
        }
        else {
            throw new IllegalArgumentException("invalid column name or row index");
        }
    }

    public List<String> getColumnData(String columnName) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            data.add(getValue(columnName, i));
        }
        return data;
    }

    public void addValue(String columnName, String value) {
        Column column = columnMap.get(columnName);
        if (column != null) {
            column.addRowValue(value);
        } else {
            throw new IllegalArgumentException("invalid column name");
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            throw new IllegalArgumentException("Invalid row index");
        }

        for (Column column : columns) {
            column.getRows().remove(rowIndex);
        }
    }

    public void addRow(List<String> values) {
        if (values.size() != columns.size()) {
            throw new IllegalArgumentException("Invalid number of values");
        }

        for (int i = 0; i < values.size(); i++) {
            columns.get(i).addRowValue(values.get(i));
        }
    }

    public void setValue(String columnName, int row, String value) {
        Column column = columnMap.get(columnName);
        if (column != null && row < column.getSize()) {
            column.setRowValue(row, value);
        } else {
            throw new IllegalArgumentException("Invalid column name or row index");
        }
    }

            public static void main(String[] args) {
        // Create a new DataFrame
        DataFrame dataFrame = new DataFrame();

        // Add columns to the DataFrame
        dataFrame.addColumn("Name");
        dataFrame.addColumn("Age");

        // Display column names
        System.out.println("Column Names: " + dataFrame.getColumnNames());

        // Add values to the columns
        dataFrame.addValue("Name", "John");
        dataFrame.addValue("Name", "Alice");
        dataFrame.addValue("Age", "25");
        dataFrame.addValue("Age", "30");

        // Display row count
        System.out.println("Row Count: " + dataFrame.getRowCount());

        // Display values in the DataFrame
        System.out.println("Values in the DataFrame:");
        displayDataFrame(dataFrame);

        // Update a value in the DataFrame
        dataFrame.putValue("Age", 1, "28");

        // Display updated values
        System.out.println("\nValues in the DataFrame after update:");
        displayDataFrame(dataFrame);
    }

    private static void displayDataFrame(DataFrame dataFrame) {
        List<String> columnNames = dataFrame.getColumnNames();
        int rowCount = dataFrame.getRowCount();

        // Display header
        System.out.printf("%-15s", " ");
        for (String columnName : columnNames) {
            System.out.printf("%-15s", columnName);
        }
        System.out.println();

        // Display values
        for (int i = 0; i < rowCount; i++) {
            System.out.printf("%-15s", "Row " + i + ":");
            for (String columnName : columnNames) {
                System.out.printf("%-15s", dataFrame.getValue(columnName, i));
            }
            System.out.println();
        }
    }
}