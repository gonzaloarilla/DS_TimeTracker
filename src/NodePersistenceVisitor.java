import org.json.JSONArray;
import org.json.JSONException;


public class NodePersistenceVisitor implements NodeVisitor{

  // Visit method for Projects and Tasks. It puts the node data into its own JSONObject attribute
  public void visit(Node node) {
    try {
      node.getJSONObject().put("id", node.getId());
      node.getJSONObject().put("name", node.getName());
      node.getJSONObject().put("initialDate", node.getStartDate());
      node.getJSONObject().put("lastDate", node.getLastDate());
      node.getJSONObject().put("duration", node.getDurationSeconds());
      node.getJSONObject().put("type", node.getType());
    }catch (JSONException jsonException) {
      System.out.println(jsonException);
    }

    // Checks if parent has a JSONArray object created inside its JSONObject
    // In case it's not created a new one is created
    if (node.parent != null) {
      JSONArray array = node.parent.getJSONObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        node.parent.getJSONObject().put("nodes", array);
      }

      // The node's JSONObject is put inside parent's JSONArray
      array.put(node.getJSONObject());
    }
  }

  // Visit method for Intervals
  public void visit (Interval interval) {

    try {

      interval.getJSONObject().put("initialDate", interval.getStartDate());
      interval.getJSONObject().put("lastDate", interval.getLastDate());
      interval.getJSONObject().put("duration", interval.getDurationSeconds());
      interval.getJSONObject().put("type", interval.getType());

    }catch (JSONException jsonException) {
      System.out.println(jsonException);
    }

    // Checks if parent has a JSONArray object created inside its JSONObject
    // In case it's not created a new one is created
    if (interval.parent != null) {
      JSONArray array = interval.parent.getJSONObject().optJSONArray("intervals");
      if (array == null) {
        array = new JSONArray();
        interval.parent.getJSONObject().put("intervals", array);
      }

      // The node's JSONObject is put inside parent's JSONArray
      array.put(interval.getJSONObject());
    }
  }
}
