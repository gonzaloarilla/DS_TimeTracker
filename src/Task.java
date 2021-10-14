import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Task extends Node {
  private String name;
  private Duration duration;
  private LocalDateTime localDateTime;
  private boolean active;
  private boolean canInterval;
  private boolean stopped, started;
  protected List<Node> nodeList;

  // No del tot segur
  protected List<Task> taskList;


  public Task(String name) {
    // Inicialitzem els atributs
    this.name = name;
    this.active = false;
    this.localDateTime = getLocalDateTime();
    this.duration = getDuration();
    this.canInterval = false;

    this.nodeList = new ArrayList<Node>();
    // PARÀMETRE TASK (name) CAL?

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

  public String getName() {
    return name;
  }

  public Duration getDuration() {
    return duration;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public boolean isActive() {
    return active;
  }

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

  private Duration updateDuration()
  {
    // retornem la suma de la duració de les tasques
    this.duration = Duration.ZERO;
    for (Node node : nodeList) {
      this.duration = this.duration.plus(node.getDuration());
    }

    return this.duration;
  }

  // Guardem totes les tasques en una llista (Maybe nodeList works?)
  public void addTask(String name)
  {
    this.setName(name);
    taskList.add(this);
  }

  // Afegim un node nou (en aquest cas una tasca doncs n = task)
  public void addNode(Node n)
  {
    nodeList.add(n);
  }
}