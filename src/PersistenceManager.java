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
    /*FileReader reader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(reader);
    JSONObject obj = new JSONObject(tokener);
    System.out.println("nodes: " + obj.opt("nodes"));
    System.out.println("Id: " + obj.opt("id"));
    System.out.println("Name: " + obj.opt("Name"));
*/
    /*String fileParsed = "/NodeData.json";
    InputStream is = PersistenceManager.class.getResourceAsStream(fileParsed);
    if (is == null) {
      throw new NullPointerException("Cannot find resource file " + fileParsed);
    }*/

    FileReader fileReader = new FileReader(filename);
    JSONTokener tokener = new JSONTokener(fileReader);
    JSONObject object = new JSONObject(tokener);

    JSONArray nodes = object.getJSONArray("nodes");
    for (int i = 0; i < nodes.length(); i++) {
      System.out.println("  - " + nodes.get(i));
    }

    /*System.out.println("name  : " + object.opt("name"));
    System.out.println("type: " + object.opt("type"));
    System.out.println("id : " + object.opt("id"));*/



//    JSONTokener tokener = new JSONTokener(reader);
//    JSONObject object = new JSONObject(tokener);
  }
}
