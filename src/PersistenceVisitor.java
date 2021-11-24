import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Class which implements the visit() methods from NodeVisitor
 */
public class PersistenceVisitor implements NodeVisitor {

  private static Logger logger = LoggerFactory.getLogger("NodePersistenceVisitor.class");

  private boolean invariant() {
    return logger != null;
  }

  // Visit method for Projects and Tasks. It puts the node data into its own JSONObject attribute
  public void visit(Node node) {

    assert invariant();

    try {
      // Set Node data into JSONObject
      node.getJsonObject().put("id", node.getId());
      node.getJsonObject().put("name", node.getName());
      node.getJsonObject().put("initialDate", node.getStartDate());
      node.getJsonObject().put("lastDate", node.getLastDate());
      node.getJsonObject().put("duration", node.getDurationSeconds());
      node.getJsonObject().put("type", node.getType());
    } catch (JSONException jsonException) {
      logger.warn(jsonException.toString());
    }

    // Checks if parent has a JSONArray object created inside its JSONObject
    // In case it's not created a new one is created

    // assert node.parent != null;
    if (node.parent != null) {
      JSONArray array = node.parent.getJsonObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        node.parent.getJsonObject().put("nodes", array);
        logger.trace("New JSONArray added to parent Project " + node.parent.getName());
      }

      // The node's JSONObject is put inside parent's JSONArray
      array.put(node.getJsonObject());
    }
  }

  // Visit method for Intervals
  public void visit(Interval interval) {

    assert invariant();

    try {
      // Set Interval data into JSONObject
      interval.getJsonObject().put("initialDate", interval.getStartDate());
      interval.getJsonObject().put("lastDate", interval.getLastDate());
      interval.getJsonObject().put("duration", interval.getDurationSeconds());
      interval.getJsonObject().put("type", interval.getType());

    } catch (JSONException jsonException) {
      logger.warn(jsonException.toString());
    }

    // Checks if parent has a JSONArray object created inside its JSONObject
    // In case it's not created a new one is created
    if (interval.parent != null) {
      JSONArray array = interval.parent.getJsonObject().optJSONArray("intervals");
      if (array == null) {
        array = new JSONArray();
        interval.parent.getJsonObject().put("intervals", array);
        logger.trace("New JSONArray added to parent Task " + interval.parent.getName());
      }

      // The node's JSONObject is put inside parent's JSONArray
      array.put(interval.getJsonObject());
    }
  }
}
