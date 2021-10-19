import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Project extends Node {

	private String name;
	private static String id;
	private Duration duration;
	private LocalDateTime initialDate;
	private LocalDateTime lastDate;
	
	// Parent project, null if root
	private Node parent;

	// List of tasks/subprojects
	protected List<Node> nodeList;

	// Root Project Constructor
	public Project(String id, String name, Node parent) {
		this.name = name;
		this.id = id;
		this.nodeList = new ArrayList<Node>();
		this.duration = Duration.ZERO;
		this.parent = parent;
	}


	// Add a new node to list
	public void addNode(Node node) {
		nodeList.add(node);
	}

	public String getId() {
		return id;
	}

	// Start task with name, if node is project code gets back here until task instance reached
	public boolean startTask(String name) {

		// Search for the task with matching name
		boolean started = false;
		int i = 0;
		while (!started && i < nodeList.size()) {
		  started = nodeList.get(i).startTask(name);
		  i = i + 1;
		}

		return started;
	}

	// Stop task with name, if node is project code gets back here until task instance reached
	public boolean stopTask(String name) {

		// Search for the task with matching name
		boolean stopped = false;
		int i = 0;
		while (!stopped && i < nodeList.size()) {
		  stopped = nodeList.get(i).stopTask(name);
		  i = i + 1;
		}

		return stopped;
	}
	
	// Updates Project duration data and calls its parent update method
	public void update() {
		if (isActive()) {
			updateDuration();
			if (parent != null) {
				parent.update(this.lastDate, this.initialDate);
			}
		}
	}
	

}
