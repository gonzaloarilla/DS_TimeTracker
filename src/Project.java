import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Project is a type of Node, it uses Composite pattern design
 */
public class Project extends Node {
  private List<Node> nodeList; // Might be Project or Task
  private static Logger logger = LoggerFactory.getLogger(Project.class);

  public Project(String id, String name, Node parent) {
    super(id, name, parent);
    // pre-conditions

    this.nodeList = new ArrayList<>();
    this.nodeJsonObject = new JSONObject();

    // Parent assert no required (on root, parent is null)
    assert !id.isEmpty() && !name.isEmpty();

    logger.debug("Project " + name + " has been created.");
  }

  @Override
  protected boolean invariant() {
    // nodelist cannot be null but empty
    return (this.nodeList != null) && (logger != null);
  }

  public JSONObject getJsonObject() {
    return nodeJsonObject;
  }

  // Add a new node to its list
  public void addNode(Node node) {
    assert invariant();   // nodelist must be initialised
    nodeList.add(node);
    logger.info(node.name + "has been added to Project " + this.name);
  }

  // Start task looking for its ID
  // If node is a project, code gets back here until task instance reached
  @Override
  public boolean startTask(String id) {
    assert !id.isEmpty(); // id required

    // Search for the task with matching name
    boolean started = false;
    int i = 0;
    while (!started && i < nodeList.size()) {
      started = nodeList.get(i).startTask(id);
      i++;

      if (started) {
        logger.debug("Project " + this.name + " has started");
      }
    }

    return started;
  }

  // Stop task looking for its ID
  // If node is a project, code gets back here until task instance reached
  @Override
  public boolean stopTask(String id) {
    assert !id.isEmpty(); // id required

    // Search for the task with matching name
    boolean stopped = false;
    int i = 0;
    while (!stopped && i < nodeList.size()) {
      stopped = nodeList.get(i).stopTask(id);
      i++;

      if (stopped) {
        logger.debug("Project " + this.name + " has stopped");
      }
    }

    return stopped;
  }

  // Method to use Visitor pattern design
  public void acceptVisit(NodeVisitor visitor) {
    visitor.visit(this);
    logger.debug("Project " + this.name + " visited");

    assert invariant();   // nodeList cannot be null
    for (Node node : nodeList) {
      node.acceptVisit(visitor);
    }
  }
}