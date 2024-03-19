package uk.ac.ucl.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONWriter {
    private DataFrame allData;

    public JSONWriter(DataFrame allData) {
        this.allData = allData;
    }

    public void writeToJson(String filePath) {
        List<String> columnNames = allData.getColumnNames();
        List<Map<String, String>> rows = new ArrayList<>();

        for (int i = 0; i < allData.getRowCount(); i++) {
            Map<String, String> row = new HashMap<>();
            for (String columnName : columnNames) {
                row.put(columnName, allData.getValue(columnName, i));
            }
            rows.add(row);
        }

        Map<String, List<Map<String, String>>> data = new HashMap<>();
        data.put("patients", rows);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}