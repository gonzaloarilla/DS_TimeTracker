package firstmilestone;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Node class represents the Component class in the Composite pattern.
It has the common attributes and methods in Project and Task
 */
public abstract class Node {

  protected int id;
  public String name;
  protected String description;
  protected LocalDateTime initialDate;
  protected LocalDateTime lastDate;
  protected Duration duration;
  protected boolean isActive;
  protected List<String> tagList;
  protected Node parent;
  protected DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected JSONObject nodeJsonObject;
  private static Logger logger = LoggerFactory.getLogger(firstmilestone.Node.class);


  protected Node(int id, String name, Node parent) {
    this.name = name;
    this.id = id;
    this.parent = parent;
    this.isActive = false;
    this.duration = Duration.ZERO;
    this.initialDate = LocalDateTime.now();
    this.lastDate = this.initialDate;
    this.nodeJsonObject = new JSONObject();
    this.tagList = new ArrayList<>();
    this.description = "";

    // pre-conditions
    //assert !id.isEmpty() && !name.isEmpty() && parent != null;

    // logger check
    assert !invariant();
  }

  protected boolean invariant() {
    return logger != null;
  }

  public void addTag(String tag) {
    assert !tag.isEmpty();
    this.tagList.add(tag);
    logger.debug("Tag " + tag + " has been added to " + this.name);
  }

  public void setDescription(String description) {
    assert !description.isEmpty();
    this.description = description;
    logger.debug("Description '" + description + "' has been updated to " + this.name);
  }

  public String getDescription() {
    return description;
  }

  public List<String> getTagList() {
    return this.tagList;
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

  public int getId() {
    return id;
  }

  public boolean startTask(int id) {
    return true;
  }

  public boolean stopTask(int id) {
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
    assert !this.isActive;
  }

  // Set itself and its parent not active
  public void setNotActive() {
    this.isActive = false;
    if (this.parent != null) {
      this.parent.setNotActive();
    }
    assert this.isActive;
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

      logger.debug("Duration of " + this.name
          + " has been updated to " + String.valueOf(this.duration.getSeconds()));
    }
  }

  // Updates its lastDate and duration and call its parent for updating its values too
  void update(LocalDateTime lastDate, Duration durationToSum) {
    this.lastDate = lastDate;
    this.updateDuration(durationToSum);
    //System.out.println(this);
    logger.debug(this.name + " has been updated");
    if (parent != null) {
      parent.update(lastDate, durationToSum);
    }
  }

  // Returns Class name as String
  public String getType() {
    return this.getClass().getSimpleName().toLowerCase();
  }


  public void makeTreeCourses() {
    // implement this method that returns the tree of
    // appendix A in the practicum handout
  }

  public abstract Node findActivityById(int id);

  public abstract JSONObject toJson(int i);
  // The 1 means the desired depth of the tree, root plus its children and no more descendants.
  // Each recursive call to toJson decrements the passed depth value, when received depth is zero do nothing.

}
