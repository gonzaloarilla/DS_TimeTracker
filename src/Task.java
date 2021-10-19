import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Task extends Node {

  private String name;
  private static String id;
  private Node parent;
  private Duration duration;
  private LocalDateTime localDateTime;
  private boolean isActive;
  private boolean canInterval;
  private boolean stopped, started;
  protected List<Node> nodeList; // millor intervals crec, ja que nomes podra tenir intervals
  protected List<Interval> intervals;

  // No del tot segur
  // Gonzalo: crec que no, task no té altres tasks, només intervals
  protected List<Task> taskList;


  public Task(String id, String name, Node parent) {
    // Inicialitzem els atributs
    this.name = name;
    this.id = id;
    this.parent = parent;
    this.isActive = false;
    this.localDateTime = getLocalDateTime();
    this.duration = getDuration();
    this.canInterval = false;

    this.nodeList = new ArrayList<Node>();
    this.intervals = new ArrayList<>();
    // PARÀMETRE TASK (name) CAL?

  }

  public String getId() {
    return id;
  }

  public boolean isStopped() {
    return stopped;
  }

  public boolean isStarted() {
    return started;
  }

  public boolean isCanInterval() {
    if (!this.isStarted() || this.isStopped()) {
      this.canInterval = false;
    }
    this.canInterval = true;

    // Assegurar que sigui poguem començar la tasca
    //assert canInterval;
    return canInterval;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public boolean isActive() {
    return isActive;
  }

  //Gonzalo: no caldria. Com la task no tindra mes tasks,
  //quan fa start ja es d'ella mateixa (no cal l'String name)
  public boolean startTask(String name)
  {
    this.started = false;
    for (int i = 0; i < nodeList.size(); i++)
    {
      var nodeNames = nodeList.get(i).getName();
      if (nodeNames == name)
      {
        // starts the matching task
        this.started = nodeList.get(i).startTask(name);
      }
    }
    return this.started;
  }

  public void startTask() {
    if (!this.isActive) {
      Interval newInterval = new Interval(this);
      intervals.add(newInterval);
      this.isActive = true;
      System.out.println("Task " + this.name + " started");
    }
  }

  public void stopTask() {
    if (this.isActive) {
      for (Interval interval : intervals){
        interval.finish();
      }
      this.isActive = false;
      System.out.println("Task " + this.name + " stopped");
    }
  }

  public boolean stopTask(String name) {
    this.stopped = false;

    for (int i = 0; i < nodeList.size(); i++)
    {
      var nodeNames = nodeList.get(i).getName();
        if (nodeNames == name)
        {
          // stops the matching task
          this.stopped = nodeList.get(i).stopTask(name);
        }
    }
    return this.stopped;
  }


  // Gonzalo: Task no pot contenir mes tasks
  // Guardem totes les tasques en una llista (Maybe nodeList works?)
  public void addTask(String name)
  {
    this.setName(name);
    taskList.add(this);
  }

  // Gonzalo: Task no pot contenir mes tasks o projects
  // Afegim un node nou (en aquest cas una tasca doncs n = task)
  public void addNode(Node n)
  {
    nodeList.add(n);
  }
}