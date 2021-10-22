import java.util.*;

public class Project extends Node {

	private List<Node> nodeList; // Might be Project or Task


	// Project Constructor
	public Project(String id, String name, Node parent) {
		super(id, name, parent);
    this.nodeList = new ArrayList<>();
	}

	// Add a new node to its list
	public void addNode(Node node) {
		nodeList.add(node);
	}

  // Start task with name, if node is project code gets back here until task instance reached
  @Override
  public boolean startTask(String id) {

		// Search for the task with matching name
		boolean started = false;
		int i = 0;
		while (!started && i < nodeList.size()) {
		  started = nodeList.get(i).startTask(id);
		  i++;
		}

		return started;
	}

  // Stop task with name, if node is project code gets back here until task instance reached
  @Override
  public boolean stopTask(String id) {

		// Search for the task with matching name
		boolean stopped = false;
		int i = 0;
		while (!stopped && i < nodeList.size()) {
		  stopped = nodeList.get(i).stopTask(name);
		  i++;
		}

		return stopped;
	}


}
