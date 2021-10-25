import org.json.JSONArray;
import org.json.JSONException;

public class NodePersistenceVisitor implements NodeVisitor{

  public void JSONvisit(Node node) {
    try {
      node.getJSONObject().put("id: ", node.getId());
      node.getJSONObject().put("name: ", node.getName());
      node.getJSONObject().put("initial Date: ", node.getStartDate());
      node.getJSONObject().put("final Date: ", node.getLastDate());
      node.getJSONObject().put("duration: ", node.getExactTime());
      node.getJSONObject().put("type: ", node.getType());
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

  @Override
  public void visit(Project project) {
    JSONvisit(project);
    /*project.getJSONObject().put("id: ", project.getId());
    project.getJSONObject().put("name: ", project.getName());
    project.getJSONObject().put("initial Date: ", project.getStartDate());
    project.getJSONObject().put("final Date: ", project.getLastDate());
    project.getJSONObject().put("duration: ", project.getExactTime());
    project.getJSONObject().put("type: ", project.getType());

    if (project.parent != null) {

      JSONArray array = project.parent.getJSONObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        project.parent.getJSONObject().put("nodes", array);
      }
      array.put(project.getJSONObject());


    }*/
  }

  @Override
  public void visit(Task task) {
    JSONvisit(task);

  }

  @Override
  public void visit(Interval interval){
    JSONvisit(interval);

  }
}
