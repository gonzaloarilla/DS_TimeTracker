import java.util.*;

public class Task extends Node {

  private List<Interval> intervals;

  private boolean canInterval;
  private boolean stopped, started;
  // canInterval, stopped i started ho podem controlar des del "isActive" del Node, no?


  public Task(String id, String name, Node parent) {
    super(id, name, parent);

    this.nodeList = new ArrayList<Node>();
    this.intervals = new ArrayList<>();

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



  //Gonzalo: aquests dos metodes no han de ser aixi,
  //han de buscar una tasca pel seu ID

  public void startTask() {
    if (!this.isActive) {
      Interval newInterval = new Interval(this);
      intervals.add(newInterval);
      this.isActive = true;
      //parent.setActive();
      System.out.println("Task " + this.name + " started");
    }
  }

  public void stopTask() {
    if (this.isActive) {
      for (Interval interval : intervals){
        interval.finish();
      }
      this.isActive = false;
      //parent.setNotActive();
      System.out.println("Task " + this.name + " stopped");
    }
  }
}