import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval extends Node implements Observer {

  private final Node parent;
  private boolean isActive;
  private final LocalDateTime initialDate;
  private LocalDateTime lastDate;
  private Duration duration;
  protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private String timeConversion; // s'ha d'utilitzar la classe DateTimeFormatter
  private JSONObject nodeJSONObject;

  public Interval(Node parent) {
    super(parent.id, parent.name, parent.parent);
    this.parent = parent;
    this.isActive = true;
    this.lastDate = LocalDateTime.now();
    this.initialDate = lastDate.minus(Duration.ofMillis(Clock.getPeriod()));
    this.duration = Duration.ZERO;
    this.timeConversion = "";
    this.nodeJSONObject = new JSONObject();

    Clock.getRunningClock().addObserver(this);
    //System.out.println("Interval created and running");
  }

  public JSONObject getJSONObject() {
    return nodeJSONObject;
  }

  public Node getParent() {
    return parent;
  }

  public boolean isActive() {
    return isActive;
  }
  public String getTimeConversion() {
    return timeConversion;
  }

  public void setTimeConversion(Duration dur) {
    // 02d -> dos digits en enter
    this.timeConversion = String.format("%02d:%02d:%02d", dur.toHours(), dur.toMinutesPart(), dur.toSecondsPart());
  }

  public String toString() {
    return "Interval:    "
        + this.initialDate.format(dateTimeFormatter)
        + "   "
        + this.lastDate.format(dateTimeFormatter)
        + "   "
        + this.duration.toSeconds();
  }

  public void update(Observable o, Object arg) {
    System.out.println("Updating interval of " + this.parent.getName());

    //Duration durationToSum = Duration.between(lastDate, (LocalDateTime) arg);
    Duration durationToSum = Duration.ofMillis(Clock.getPeriod());
    this.lastDate = (LocalDateTime) arg;
    this.duration = this.duration.plus(durationToSum);
    setTimeConversion(duration);
    System.out.println(this);
    parent.update(lastDate, durationToSum);
  }

  public void finish() {
    if (this.isActive) {
      this.isActive = false;
      this.lastDate = LocalDateTime.now();
      Duration durationToSum = Duration.ofMillis(Clock.getPeriod());
      this.duration = this.duration.plus(durationToSum);
      setTimeConversion(duration);
      Clock.getRunningClock().deleteObserver(this);
      System.out.println("Interval finished");
      //System.out.println(this);
      //parent.update(lastDate, durationToSum);
    }
  }

  public void acceptVisit(NodeVisitor visitor){

    visitor.visit(this);
    System.out.println("Interval of " + parent.name + " visited");
  }

}
