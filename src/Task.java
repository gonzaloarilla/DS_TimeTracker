import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 Task is a type of Node, it uses Composite pattern design.
 It has an Interval list instead of a node List like Project class
 */
public class Task extends Node {

  private List<Interval> intervalList;
  private static Logger logger = LoggerFactory.getLogger(Task.class);

  public Task(String id, String name, Node parent) {
    super(id, name, parent);

    // pre-conditions
    assert !id.isEmpty() && !name.isEmpty() && parent != null;

    this.intervalList = new ArrayList<>();
    this.nodeJsonObject = new JSONObject();
    logger.debug("Task " + name + " has been created.");
  }

  @Override
  protected boolean invariant() { // same as project, intervallist cannot be empty
    return (logger != null) && (this.intervalList != null);
  }

  public JSONObject getJsonObject() {
    return nodeJsonObject;
  }

  // A task will start if it hasn't started yet and has the ID we're looking for
  // It will create and start a new interval and added it its list of intervals
  @Override
  public boolean startTask(String id) {
    assert !id.isEmpty(); // id required

    if (!this.isActive && id.equals(this.id)) {
      Interval newInterval = new Interval(this, true);
      intervalList.add(newInterval);
      this.isActive = true;
      logger.info("Task " + this.name + " started");
      return true;
    }
    return false;
  }

  // A task will be stopped if it's active and has the ID we're looking for
  // It will also finish all its intervals and set the task to not active
  @Override
  public boolean stopTask(String id) {
    assert !id.isEmpty(); // id required

    if (this.isActive && id.equals(this.id)) {
      for (Interval interval : intervalList) {
        interval.finish();
      }
      //intervals.get(intervals.size()-1).finish(); -> stop last interval
      this.isActive = false;
      logger.info("Task " + this.name + " stopped");
      return true;
    }
    return false;
  }

  // Method to use Visitor pattern design
  public void acceptVisit(NodeVisitor visitor) {
    assert invariant();
    visitor.visit(this);
    logger.debug("Task " + this.name + " visited");
    assert invariant(); // intervalList cannot be null
    for (Interval interval : intervalList) {
      interval.acceptVisit(visitor);
    }
  }

  // Add interval to its list (useful when loading from json)
  public void addInterval(Interval interval) {
    intervalList.add(interval);
    logger.debug("New Interval added to Task " + this.name);
  }
}