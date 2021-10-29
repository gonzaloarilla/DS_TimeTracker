import org.json.JSONObject;
import java.util.*;

/*
 Task is a type of Node, it uses Composite pattern design.
 It has an Interval list instead of a node List like Project class
 */
public class Task extends Node {

  private List<Interval> intervalList;

  public Task(String id, String name, Node parent) {
    super(id, name, parent);
    this.intervalList = new ArrayList<>();
    this.nodeJSONObject = new JSONObject();
  }

  public JSONObject getJSONObject() {
    return nodeJSONObject;
  }

  // A task will start if it hasn't started yet and has the ID we're looking for
  // It will create and start a new interval and added it its list of intervals
  @Override
  public boolean startTask(String id) {
    if (!this.isActive && id.equals(this.id)) {
        Interval newInterval = new Interval(this, true);
        intervalList.add(newInterval);
        this.isActive = true;
        System.out.println("Task " + this.name + " started");
        return true;
    }
    return false;
  }

  // A task will be stopped if it's active and has the ID we're looking for
  // It will also finish all its intervals and set the task to not active
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

  // Method to use Visitor pattern design
  public void acceptVisit(NodeVisitor visitor){

    visitor.visit(this);
    System.out.println("Task " + this.name + " visited");

    for (Interval interval : intervalList) {
      interval.acceptVisit(visitor);
    }
  }

  // Add interval to its list (useful when loading from json)
  public void addInterval(Interval interval) {
    intervalList.add(interval);
  }
}