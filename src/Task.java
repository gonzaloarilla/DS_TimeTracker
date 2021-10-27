import org.json.JSONObject;

import java.util.*;

public class Task extends Node {

  private List<Interval> intervalList;
  private JSONObject nodeJSONObject;


  public Task(String id, String name, Node parent) {
    super(id, name, parent);
    this.intervalList = new ArrayList<>();
    this.nodeJSONObject = new JSONObject();
  }

  public JSONObject getJSONObject() {
    return nodeJSONObject;
  }

  @Override
  public boolean startTask(String id) {
    if (!this.isActive && id.equals(this.id)) {
        Interval newInterval = new Interval(this);
        intervalList.add(newInterval);
        this.isActive = true;
        System.out.println("Task " + this.name + " started");
        return true;
    }
    return false;
  }

  @Override
  public boolean stopTask(String id) {
    if (this.isActive && id.equals(this.id)) {
        for (Interval interval : intervalList){
          interval.finish();
        }
        //intervals.get(intervals.size()-1).finish(); -> stop last interval
        this.isActive = false;
        System.out.println("Task " + this.name + " stopped");
        return true;
      }
    return false;
  }

  public void acceptVisit(NodeVisitor visitor){

    visitor.visit(this);
    System.out.println("Task " + this.name + " visited");

//    for (Interval interval : intervalList) {
//      interval.acceptVisit(visitor);
//    }
  }

  public void addInterval(Interval interval) {
    intervalList.add(interval);
  }
}