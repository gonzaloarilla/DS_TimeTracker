import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {

  private String name;
  private LocalDateTime initialDate;
  private LocalDateTime lastDate;
  private Duration duration;
  private boolean isActive;
  private ArrayList tags;
  private Node parent;
  private List<Node> nodeList;


  void setName(String name) {
    this.name = name;
  }

  String getName() {
    return name;
  }

  public boolean startTask(String name) {
    return true;
  }

  public boolean stopTask(String name) {
    return true;
  }

  Duration getDuration() {
    return duration;
  }

  LocalDateTime getLastDate() {
    return lastDate;
  }

  LocalDateTime getStartDate() {
    return initialDate;
  }

  boolean isActive() {
    return isActive;
  }

  // Updates its duration value by adding each child duration
  void updateDuration() {

    this.duration = Duration.ZERO;
    for (Node node : nodeList) {
      this.duration = this.duration.plus(node.getDuration());
    }
  }

  // Updates its lastDate and startDate (this last if it wasn't initialized)
  void update(LocalDateTime lastDate, LocalDateTime initialDate) {
    if (this.isActive) {
      if (this.initialDate == null) {
        this.initialDate = initialDate;
      }
      this.lastDate = lastDate;
      this.updateDuration();
      parent.update(lastDate, this.initialDate);
    }


  }



}
