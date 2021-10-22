import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public abstract class Node {

  protected String id;
  protected String name;
  protected final LocalDateTime initialDate;
  protected LocalDateTime lastDate;
  protected Duration duration;
  protected boolean isActive;
  protected List<String> tags;
  protected Node parent;
  //protected List<Node> nodeList;
  protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  // Constructor
  protected Node(String id, String name, Node parent) {
    this.name = name;
    this.id = id;
    this.parent = parent;
    this.isActive = false;
    this.duration = Duration.ZERO;
    this.initialDate = LocalDateTime.now();
    this.lastDate = this.initialDate;
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

  public void acceptVisit(NodeVisitor op){};

  public Duration getDuration() {
    return duration;
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

  public void setActive() {
    this.isActive = true;
    if (this.parent != null) {
      this.parent.setActive();
    }
  }

  public void setNotActive() {
    this.isActive = false;
    if (this.parent != null) {
      this.parent.setNotActive();
    }
  }

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

  // Updates its lastDate and startDate (this last if it wasn't initialized)
  void update(LocalDateTime lastDate, Duration durationToSum) {
    this.lastDate = lastDate;
    this.updateDuration(durationToSum);
    System.out.println(this);
    if (parent != null) {
      parent.update(lastDate, durationToSum);
    }

  }



}
