import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Node {

  private String name;
  private LocalDateTime date;
  private Duration duration;
  private boolean isActive;
  private ArrayList tags;


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

  LocalDateTime getDate() {
    return date;
  }

  boolean isActive() {
    return isActive;
  }

}
