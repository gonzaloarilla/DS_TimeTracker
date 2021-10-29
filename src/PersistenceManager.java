import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;


public class PersistenceManager {

  // Given a root project, we visit all its children using Visitor design pattern
  // Once they're visited, we save its JSON objects into a file
  public static void saveData(Node root, String filename) throws IOException {

    // Saving all attributes while visit
    NodeVisitor nodePersistence = new NodePersistenceVisitor();
    root.acceptVisit(nodePersistence);

    // Creating and writing the file
    FileWriter fileWriter = new FileWriter("./"+filename, false);
    fileWriter.write(root.getJSONObject().toString(2));   // indentFactor = tabulates the JSON correctly
    fileWriter.close();
  }

  // Given a json file, we read it and return a root Node
  public static Node loadData(String filename) throws IOException {
    FileReader fileReader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(fileReader);
    JSONObject object = new JSONObject(tokener);
    fileReader.close();
    return restoreNodeStructure(null, object);

  }

  // Recursive function which restores all the node structure from a JSONObject
  // Depending on the node type retrieved (Project or Task) it will create the
  // corresponding object type with the data retrieved. Task objects also
  // create its intervals.
  private static Node restoreNodeStructure(Node parent, JSONObject jsonNodeObject) {

    String id = jsonNodeObject.optString("id");
    String name = jsonNodeObject.optString("name");
    String initialDateString = jsonNodeObject.optString("initialDate");
    String lastDateString = jsonNodeObject.optString("lastDate");
    long duration = jsonNodeObject.optLong("duration"); // JSON no guarda objectes de tipus Duration -> long pels segons

    String type = jsonNodeObject.optString("type");
    JSONArray array;
    LocalDateTime initialDate;
    LocalDateTime lastDate;

    switch (type) {
      case "project":
        Project project = new Project(id, name, parent);

        // Duration
        project.setDuration(Duration.ZERO.plusSeconds(duration));
        // Initial Date
        initialDate = LocalDateTime.parse(initialDateString);
        project.setInitialDate(initialDate);
        // Last Date
        lastDate = LocalDateTime.parse(lastDateString);
        project.setLastDate(lastDate);

        /*
        Comprova els fills del jsonObject i els inclou com a fills del projecte creat
        Es crida restoreNodeStructure() per recuperar les dades de cada node fill
        */
        array = jsonNodeObject.optJSONArray("nodes");
        if (array != null) {
          for (int i = 0; i < array.length(); i++) {
            jsonNodeObject = array.getJSONObject(i);
            Node node = restoreNodeStructure(project, jsonNodeObject);
            project.addNode(node);
          }
        }

        return project;

      case "task":
        Task task = new Task(id, name, parent);

        // Duration
        task.setDuration(Duration.ZERO.plusSeconds(duration));
        // Initial Date
        initialDate = LocalDateTime.parse(initialDateString);
        task.setInitialDate(initialDate);
        // Last Date
        lastDate = LocalDateTime.parse(lastDateString);
        task.setLastDate(lastDate);

        // A new interval is created for each interval object inside Task's JSONArray
        array = jsonNodeObject.optJSONArray("intervals");
        if (array != null) {
          for (int i = 0; i < array.length(); i++) {
            jsonNodeObject = array.getJSONObject(i);
            Interval interval = new Interval(task, false);
            interval = setupNewInterval(interval, jsonNodeObject);
            task.addInterval(interval);
          }
        }

        return task;
      default:
        return null;
    }
  }

  // Retrieves and sets the interval data from a JSONObject
  private static Interval setupNewInterval(Interval interval, JSONObject jsonObject) {

    LocalDateTime initialDate;
    LocalDateTime lastDate;
    String initialDateString = jsonObject.optString("initialDate");
    String lastDateString = jsonObject.optString("lastDate");
    long duration = jsonObject.optLong("duration");

    // Duration
    interval.setDuration(Duration.ZERO.plusSeconds(duration));
    // Initial Date
    initialDate = LocalDateTime.parse(initialDateString);
    interval.setInitialDate(initialDate);
    // Last Date
    lastDate = LocalDateTime.parse(lastDateString);
    interval.setLastDate(lastDate);

    return interval;
  }
}
