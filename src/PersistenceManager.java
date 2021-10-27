import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.plaf.ToolBarUI;
import java.io.*;
import java.time.Duration;


public class PersistenceManager {


  public static void saveData(Node root, String filename) throws IOException {

    // Saving all attributes while visit
    NodeVisitor nodePersistence = new NodePersistenceVisitor();
    root.acceptVisit(nodePersistence);

    // Indent = 2 --> pretty print
    FileWriter fileWriter = new FileWriter("./"+filename);
    fileWriter.write(root.getJSONObject().toString(2));
    fileWriter.close();
  }

  public static void loadData(Node root, String filename) throws IOException {

    FileReader fileReader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(fileReader);
    JSONObject object = new JSONObject(tokener);

    root = restoreNodeStructure(root, object);
  }

  private static Node restoreNodeStructure(Node parent, JSONObject jsonNodeObject) {

    String id = jsonNodeObject.getString("id");
    String name = jsonNodeObject.getString("name");
    String initialDate = jsonNodeObject.getString("initialDate");
    String lastDate = jsonNodeObject.getString("lastDate");
    long duration = jsonNodeObject.getLong("duration"); // JSON no guarda objectes de tipus Duration -> long pels segons

    String type = jsonNodeObject.getString("type");
    JSONArray array;

    switch (type) {
      case "project" :
        Project project = new Project(id, name, parent);
        // TODO: guardar dades que falten a project (initialDate, lastDate, Duration)
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
        // TODO: guardar dades que falten a task (initialDate, lastDate, Duration)

        array = jsonNodeObject.optJSONArray("nodes");
        if (array != null) {
          for (int i = 0; i < array.length(); i++) {
            jsonNodeObject = array.getJSONObject(i);
            Node node = (Interval) restoreNodeStructure(task, jsonNodeObject);
            // TODO: task.addInterval(node);
          }
        }

        return task;
      case "interval" :
        Interval interval = new Interval(parent);
        // TODO:
        return interval;
      default:
        return null;
    }

  }
}
