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

    // Indent = 2 --> pretty print
    FileWriter fileWriter = new FileWriter("./"+filename, false);
    fileWriter.write(root.getJSONObject().toString(2));
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

  // TODO comment
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

        // TODO: comment
        array = jsonNodeObject.optJSONArray("nodes");
//        if (array != null) {
//          for (int i = 0; i < array.length(); i++) {
//            jsonNodeObject = array.getJSONObject(i);
//            Interval interval = (Interval) restoreNodeStructure(task, jsonNodeObject);
//            task.addInterval(interval);
//          }
//        }

        return task;

      case "interval" :
//        Interval interval = new Interval((Task) parent);
//
//        // Duration
//        interval.setDuration(Duration.ZERO.plusSeconds(duration));
//        // Initial Date
//        initialDate = LocalDateTime.parse(initialDateString);
//        interval.setInitialDate(initialDate);
//        // Last Date
//        lastDate = LocalDateTime.parse(lastDateString);
//        interval.setLastDate(lastDate);
//
//        return interval;
      default:
        return null;
    }
  }
}
