import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;


public class PersistenceManager {


  public static void saveData(Node root, String filename) throws IOException {

    NodeVisitor nodePersistence = new NodePersistenceVisitor();
    root.acceptVisit(nodePersistence);



    FileWriter fileWriter = new FileWriter("./"+filename);
    fileWriter.write(root.getJSONObject().toString());
    fileWriter.close();






  }

  public static void loadData(Node root) throws IOException {

    FileReader reader = new FileReader("jsonfile.txt");
    JSONTokener tokener = new JSONTokener(reader);
    JSONObject object = new JSONObject(tokener);
  }
}
