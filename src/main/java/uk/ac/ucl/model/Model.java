package uk.ac.ucl.model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Model {
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a suitable data structure.
  DataFrame allData;

  public Model(String filePath) {
    try {
      allData = readFile(filePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      allData = new DataFrame();
    }
  }

  public DataFrame readFile(String fileName) throws FileNotFoundException {
    DataLoader loader = new DataLoader(fileName);
    return loader.loadData();
  }

  public List<String> getPatientNames() {
    List<String> firstNames = allData.getColumnData("FIRST");
    List<String> lastNames = allData.getColumnData("LAST");
    List<String> fullNames = new ArrayList<>();

    for (int i = 0; i < firstNames.size(); i++) {
      String fullName = firstNames.get(i) + "  " + lastNames.get(i);
      fullNames.add(fullName);
    }
    return fullNames;
  }

  public List<String> getPatientIDs() {
    return allData.getColumnData("ID");
  }

  public List<String> getColumnNames() {
    return allData.getColumnNames();
  }

  public List<String> getPatientDetails(String patientId) {
    for (int row = 0; row < allData.getRowCount(); row++) {
      if (allData.getValue("ID", row).equals(patientId)) {
        List<String> patientDetails = new ArrayList<>();
        for (String columnName : allData.getColumnNames()) {
          String cellValue = allData.getValue(columnName, row);
          String result = columnName + ":  " + cellValue;
          patientDetails.add(result);
        }
        return patientDetails;
      }
    }
    return null;
  }
  public Map<String, String> searchFor(String keyword) {
    Map<String, String> searchResults = new HashMap<>();

    for (int row = 0; row < allData.getRowCount(); row++) {
      for (String columnName : allData.getColumnNames()) {
        String cellValue = allData.getValue(columnName, row);
        if (cellValue != null && cellValue.toLowerCase().contains(keyword.toLowerCase())) {
          // Include relevant information in the search results
          String result = columnName + ": " + cellValue;
          String patientId = allData.getValue("ID", row);
          searchResults.put(result, patientId);
        }
      }
    }
    return searchResults;
  }

  public Map<String, List<String>> getPatientsSortedByColumn(String column, String order) {
    List<String> columnData = allData.getColumnData(column);
    List<String> patientIDs = allData.getColumnData("ID");
    Map<String, List<String>> sortedPatients = new LinkedHashMap<>();

    // Create a list of indices
    List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < columnData.size(); i++) {
      indices.add(i);
    }

    // Sort the indices list based on the columnData
    if (order.equals("asc")) {
      indices.sort(Comparator.comparing(columnData::get));
    }
    else if (order.equals("desc")) {
      indices.sort(Comparator.comparing(columnData::get).reversed());
    }

    // Create the sortedPatients map
    for (int index : indices) {
      String key = columnData.get(index);
      String value = patientIDs.get(index);

      if (!sortedPatients.containsKey(key)) {
        sortedPatients.put(key, new ArrayList<>());
      }
      sortedPatients.get(key).add(value);
    }

    return sortedPatients;
  }

  public Map<Integer, List<String>> getPatientAgesWithIDs() {
    List<String> birthDates = allData.getColumnData("BIRTHDATE");
    List<String> deathDates = allData.getColumnData("DEATHDATE");
    List<String> patientIDs = allData.getColumnData("ID");
    Map<Integer, List<String>> agesWithIDs = new HashMap<>();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (int i = 0; i < birthDates.size(); i++) {
      try {
        LocalDate birthDate = LocalDate.parse(birthDates.get(i), formatter);
        int age;

        if (Objects.equals(deathDates.get(i), "")) { //get ages of alive patients
          age = Period.between(birthDate, LocalDate.now()).getYears();
        }
        else {
          LocalDate endDate = LocalDate.parse(deathDates.get(i), formatter);
          age = Period.between(birthDate, endDate).getYears();
        }

        if (!agesWithIDs.containsKey(age)) {
          agesWithIDs.put(age, new ArrayList<>());
        }
        agesWithIDs.get(age).add(patientIDs.get(i));

      } catch (DateTimeParseException e) {
        // If the birthdate string is invalid, set the age as null
        if (!agesWithIDs.containsKey(null)) {
          agesWithIDs.put(null, new ArrayList<>());
        }
        agesWithIDs.get(null).add(patientIDs.get(i));
      }
    }
    return agesWithIDs;
  }
  public Map<String, List<String>> getPatientsSortedByAges(String order) {
    Map<Integer, List<String>> agesWithIDs = getPatientAgesWithIDs();
    Map<String, List<String>> sortedAgesWithIDs = new LinkedHashMap<>();
    List<Integer> ages = new ArrayList<>(agesWithIDs.keySet());

    // Sort the ages list based on the order
    if (order.equalsIgnoreCase("asc")) {
      ages.sort(Comparator.nullsLast(Comparator.naturalOrder()));
    }
    else if (order.equalsIgnoreCase("desc")) {
      ages.sort(Comparator.nullsLast(Comparator.reverseOrder()));
    }

    for (Integer age : ages) {
      sortedAgesWithIDs.put(age == null ? "Invalid Age" : String.valueOf(age), agesWithIDs.get(age));
    }

    return sortedAgesWithIDs;
  }

  public List<Integer> getAllAges() {
    Map<Integer, List<String>> agesWithIDs = getPatientAgesWithIDs();
    List<Integer> allAges = new ArrayList<>();

    for (Map.Entry<Integer, List<String>> entry : agesWithIDs.entrySet()) {
      Integer age = entry.getKey();
      int count = entry.getValue().size();

      for (int i = 0; i < count; i++) {
        allAges.add(age);
      }
    }

    return allAges;
  }

  public void addPatient(List<String> patientDetails) {
    allData.addRow(patientDetails);
  }

  public void deletePatient(String patientID) {
    if (patientID == null) {
      return;
    }
    for (int row = 0; row < allData.getRowCount(); row++) {
      if (allData.getValue("ID", row).equals(patientID)) {
        allData.removeRow(row);
        break;
      }
    }
  }
  public void updatePatient(List<String> newPatientDetails, String patientID) {
    // Find the patient in the data frame using the patient ID
    if (patientID == null) {
      return;
    }
    for (int row = 0; row < allData.getRowCount(); row++) {
      if (allData.getValue("ID", row).equals(patientID)) {
        // Retrieve column names
        List<String> columnNames = allData.getColumnNames();
        // Iterate over the new patient details
        for (int i = 0; i < newPatientDetails.size(); i++) {
          // Update the corresponding attribute in the data frame
          allData.setValue(columnNames.get(i), row, newPatientDetails.get(i));
        }
        return;
      }
    }
  }

  public void saveDataAsCSV(String filePath) throws IOException {
    try (PrintWriter writer = new PrintWriter(filePath)) {
      // Write column names
      List<String> columnNames = allData.getColumnNames();
      writer.println(String.join(",", columnNames));

      // Write rows
      for (int i = 0; i < allData.getRowCount(); i++) {
        List<String> row = new ArrayList<>();
        for (String columnName : columnNames) {
          row.add(allData.getValue(columnName, i));
        }
        writer.println(String.join(",", row));
      }
    }
  }

  public void saveDataAsJSON(String filePath) {
    JSONWriter jsonWriter = new JSONWriter(allData);
    jsonWriter.writeToJson(filePath);
  }

  public void saveDataToFile(String filename, String filetype) throws IOException {
    String appDirectory = System.getProperty("user.dir");
    String filePath;

    if (filetype == null || filetype.trim().isEmpty() || filetype.equals("csv")) {
      if (filename == null || filename.trim().isEmpty()) {
        filename = "data";  // Default filename
      }
      filename += ".csv";  // Append .csv extension
      filePath = appDirectory + "/" + filename;
      saveDataAsCSV(filePath);
    } else if (filetype.equals("json")) {
      if (filename == null || filename.trim().isEmpty()) {
        filename = "data";
      }
      filename += ".json";
      filePath = appDirectory + "/" + filename;
      saveDataAsJSON(filePath);
    } else {
      throw new IllegalArgumentException("Invalid filetype: " + filetype);
    }
  }
}
