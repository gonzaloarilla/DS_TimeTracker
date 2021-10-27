import org.json.JSONArray;
import org.json.JSONException;

public class NodePersistenceVisitor implements NodeVisitor{

  public void visit(Node node) {
    try {
      node.getJSONObject().put("id", node.getId());
      node.getJSONObject().put("name", node.getName());
      node.getJSONObject().put("initialDate", node.getStartDate());
      node.getJSONObject().put("finalDate", node.getLastDate());
      node.getJSONObject().put("duration", node.getExactTime().getSeconds()); // JSON no guarda objectes de tipus Duration -> getSeconds()
      node.getJSONObject().put("type", node.getType());
    }catch (JSONException jsonException) {
      System.out.println(jsonException);
    }

    // if has root / pare
    if (node.parent != null) {
      JSONArray array = node.parent.getJSONObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        node.parent.getJSONObject().put("nodes", array);
      }
      array.put(node.getJSONObject());
    }

  }
}