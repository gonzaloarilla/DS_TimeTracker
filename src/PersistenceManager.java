import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;


public class PersistenceManager {


  public static void saveData(Node root, String filename) throws IOException {

    // Saving all attributes while visit
    NodeVisitor nodePersistence = new NodePersistenceVisitor();
    root.acceptVisit(nodePersistence);

    // Indent = 2 --> pretty print
    FileWriter fileWriter = new FileWriter("./"+filename, false);
    fileWriter.write(root.getJSONObject().toString(2));
    fileWriter.close();
  }

  public static Node loadData(Node root, String filename) throws IOException {

    FileReader fileReader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(fileReader);
    JSONObject object = new JSONObject(tokener);
    fileReader.close();

    return root = restoreNodeStructure(null, object);

  }

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
      case "project" :
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
      case "task" :
        Task task = new Task(id, name, parent);

        // Duration
        task.setDuration(Duration.ZERO.plusSeconds(duration));

        // Initial Date
        initialDate = LocalDateTime.parse(initialDateString);
        task.setInitialDate(initialDate);

        // Last Date
        lastDate = LocalDateTime.parse(lastDateString);
        task.setLastDate(lastDate);

        // NO GUARDEM INTERVALS AL JSON
//        array = jsonNodeObject.optJSONArray("nodes");
//        if (array != null) {
//          for (int i = 0; i < array.length(); i++) {
//            jsonNodeObject = array.getJSONObject(i);
//            Interval interval = (Interval) restoreNodeStructure(task, jsonNodeObject);
//            task.addInterval(interval);
//          }
//        }

        return task;
      default:
        return null;
    }

  }
}
