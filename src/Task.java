import java.util.*;

public class Task extends Node {

  private List<Interval> intervals;


  public Task(String id, String name, Node parent) {
    super(id, name, parent);

    //this.nodeList = new ArrayList<Node>();
    this.intervals = new ArrayList<Interval>();
  }

  @Override
  public boolean startTask(String id) {
    if (!this.isActive) {
      if (id.equals(this.id)) {
        Interval newInterval = new Interval(this);
        intervals.add(newInterval);
        this.isActive = true;
        System.out.println("Task " + this.name + " started");

        return true;
      }

    }
    return false;
  }

  @Override
  public boolean stopTask(String id) {
    if (this.isActive) {
      if (id.equals(this.id)) {
        for (Interval interval : intervals){
          interval.finish();
        }
        //intervals.get(intervals.size()-1).finish(); -> stop last interval
        this.isActive = false;
        System.out.println("Task " + this.name + " stopped");

        return true;
      }
    }
    return false;
  }
}