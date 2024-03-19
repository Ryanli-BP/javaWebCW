package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ucl.model.DataFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private String filePath;

    public DataLoader(String filePath) {
        this.filePath = filePath;
    }

    public DataFrame loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            CSVParser csvParser = new CSVParser(
                    bufferedReader,
                    CSVFormat.DEFAULT
                            .withHeader()  // Treat the first row as header
                            .withSkipHeaderRecord(true)  // Skip the header during parsing
            );

            List<String> header = new ArrayList<>(csvParser.getHeaderMap().keySet());

            if (header.isEmpty()) {
                throw new IOException("CSV file is empty or missing header.");
            }

            DataFrame dataFrame = new DataFrame();

            for (String columnName : header) {
                dataFrame.addColumn(columnName);
            }

            for (CSVRecord record : csvParser) {
                for (String columnName : header) {
                    dataFrame.addValue(columnName, record.get(columnName));
                }
            }

            return dataFrame;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Example usage
        DataLoader dataLoader = new DataLoader("patients100.csv");
        DataFrame loadedDataFrame = dataLoader.loadData();

        if (loadedDataFrame != null) {
            // Display loaded DataFrame
            System.out.println("Loaded DataFrame:");
            displayDataFrame(loadedDataFrame);
        }
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
