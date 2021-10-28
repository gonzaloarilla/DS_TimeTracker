import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// A Node object might be any activity that may be created (like Project or Task)
public abstract class Node {

  protected String id;
  protected String name;
  protected LocalDateTime initialDate;
  protected LocalDateTime lastDate;
  protected Duration duration;
  protected boolean isActive;
  protected List<String> tags;
  protected Node parent;
  protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected JSONObject nodeJSONObject;


  protected Node(String id, String name, Node parent) {
    this.name = name;
    this.id = id;
    this.parent = parent;
    this.isActive = false;
    this.duration = Duration.ZERO;
    this.initialDate = LocalDateTime.now();
    this.lastDate = this.initialDate;
    this.nodeJSONObject = new JSONObject();
  }

  public JSONObject getJSONObject() {
    return nodeJSONObject;
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

  public Duration getExactTime() {
    return Duration.between(getStartDate(), getLastDate());
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

  public String getType() {
    return this.getClass().getSimpleName().toLowerCase();
  }


}
