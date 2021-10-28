import org.json.JSONArray;
import org.json.JSONException;

// TODO comments
public class NodePersistenceVisitor implements NodeVisitor{

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

    if (node.parent != null) {
      JSONArray array = node.parent.getJSONObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        node.parent.getJSONObject().put("nodes", array);
      }
      array.put(node.getJSONObject());
    }
  }

  public void visit (Interval interval) {

    try {

      interval.getJSONObject().put("initialDate", interval.getStartDate());
      interval.getJSONObject().put("lastDate", interval.getLastDate());
      interval.getJSONObject().put("duration", interval.getDurationSeconds());
      interval.getJSONObject().put("type", interval.getType());

      if (interval.parent != null) {
        JSONArray array = interval.parent.getJSONObject().optJSONArray("intervals");
        if (array == null) {
          array = new JSONArray();
          interval.parent.getJSONObject().put("intervals", array);
        }
        array.put(interval.getJSONObject());
      }

    }catch (JSONException jsonException) {
      System.out.println(jsonException);
    }

  }
}
