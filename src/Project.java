

public class Project extends Node {

	private String name;
	private Duration duration;
	private LocalDateTime dateTime;
	
	// Parent project, null if root
	private Project parent;
	
	// List of tasks/subprojects
	private List<Node> nodeList;
	
	// Root Project Constructor
	public Project(String name) {
		
		this.name = name;
		
		this.nodeList = new ArrayList<Node>();
		this.duration = Duration.ZERO;
		this.parent = null;
	}
	
	// Default Project Constructor
	public Project(String name, Project parent) {
		this.name = name;
		
		this.nodeList = new ArrayList<Node>();
		this.duration = Duration.ZERO;
		this.parent = parent;
	}
	
	// Add a new node to list(task/project)
	public void addNode(Node node) {
		nodeList.add(node);
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
		if (this.isActive) {
			updateDuration();
			
			if (parent != null) {
				parent.update();
			}
		}
	}
	
	// Updates the Project duration value adding each's child duration
	private void updateDuration() {

		this.duration = Duration.ZERO;
		for (Node node : nodeList) {
		  this.duration = this.duration.plus(node.getDuration());
		}
	}
}
