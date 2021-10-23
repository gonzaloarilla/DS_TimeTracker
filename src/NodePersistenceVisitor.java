import org.json.JSONArray;

public class NodePersistenceVisitor implements NodeVisitor{

  @Override
  public void visit(Project project) {
    project.getJSONObject().put("id", project.id);

    if (project.parent != null) {

      JSONArray array = project.parent.getJSONObject().optJSONArray("nodes");
      if (array == null) {
        array = new JSONArray();
        project.parent.getJSONObject().put("nodes", array);
      }
      array.put(project.getJSONObject());
    }

  }

  @Override
  public void visit(Task task) {

  }

  @Override
  public void visit(Interval interval){

  }
}
