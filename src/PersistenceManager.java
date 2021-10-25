import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;


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
    FileReader reader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(reader);
    JSONObject obj = new JSONObject(tokener);
    System.out.println("nodes: " + obj.get("nodes"));
    System.out.println("Id: " + obj.getString("id"));
    System.out.println("Name: " + obj.get("Name"));

//    JSONTokener tokener = new JSONTokener(reader);
//    JSONObject object = new JSONObject(tokener);
  }
}
