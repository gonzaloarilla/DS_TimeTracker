import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Node class represents the Component class in the Composite pattern.
It has the common attributes and methods in Project and Task
 */
public abstract class Node {

  protected String id;
  protected String name;
  protected LocalDateTime initialDate;
  protected LocalDateTime lastDate;
  protected Duration duration;
  protected boolean isActive;
  protected List<String> tags;
  protected Node parent;
  protected DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected JSONObject nodeJsonObject;
  static private Logger logger = LoggerFactory.getLogger("Node.class");


  protected Node(String id, String name, Node parent) {
    this.name = name;
    this.id = id;
    this.parent = parent;
    this.isActive = false;
    this.duration = Duration.ZERO;
    this.initialDate = LocalDateTime.now();
    this.lastDate = this.initialDate;
    this.nodeJsonObject = new JSONObject();
  }

  public JSONObject getJsonObject() {
    return nodeJsonObject;
  }

  void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public boolean startTask(String id) {
    return true;
  }

  public boolean stopTask(String id) {
    return true;
  }

  public void acceptVisit(NodeVisitor op){}

  public Duration getDuration() {
    return duration;
  }

  public long getDurationSeconds() {

    return this.duration.getSeconds();
  }

  public LocalDateTime getLastDate() {
    return lastDate;
  }

  public LocalDateTime getStartDate() {
    return initialDate;
  }

  public boolean isActive() {
    return isActive;
  }

  // Set itself and its parent active
  public void setActive() {
    this.isActive = true;
    if (this.parent != null) {
      this.parent.setActive();
    }
  }

  // Set itself and its parent not active
  public void setNotActive() {
    this.isActive = false;
    if (this.parent != null) {
      this.parent.setNotActive();
    }
  }

  public void setInitialDate(LocalDateTime initialDate) {
    this.initialDate = initialDate;
  }

  public void setLastDate(LocalDateTime lastDate) {
    this.lastDate = lastDate;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  // Converts dates and duration data to a formatted string
  public String toString() {
    return this.name
        + ":    "
        + this.initialDate.format(dateTimeFormatter)
        + "   "
        + this.lastDate.format(dateTimeFormatter)
        + "   "
        + this.duration.toSeconds();
  }

  // Updates its duration value
  void updateDuration(Duration durationToSum) {
    if (this.duration != null) {
      this.duration = this.duration.plus(durationToSum);
    }
  }

  // Updates its lastDate and duration and call its parent for updating its values too
  void update(LocalDateTime lastDate, Duration durationToSum) {
    this.lastDate = lastDate;
    this.updateDuration(durationToSum);
    System.out.println(this);
    if (parent != null) {
      parent.update(lastDate, durationToSum);
    }
  }

  // Returns Class name as String
  public String getType() {
    return this.getClass().getSimpleName().toLowerCase();
  }


}
