import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

  private final Node parent;
  private boolean isActive;
  private final LocalDateTime initialDate;
  private LocalDateTime lastDate;
  private Duration duration;
  protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private JSONObject nodeJSONObject;

  public Interval(Node parent) {
    this.parent = parent;
    this.isActive = true;
    this.duration = Duration.ZERO;
    this.lastDate = LocalDateTime.now();
    this.initialDate = lastDate.minus(Duration.ofMillis(Clock.getPeriod()));
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

  public String toString() {
    return "Interval:    "
        + this.initialDate.format(dateTimeFormatter)
        + "   "
        + this.lastDate.format(dateTimeFormatter)
        + "   "
        + this.duration.toSeconds();
  }

  // Using Observer pattern design, this method receives an object
  // every time the Observable has a notification to its observer.
  // It will updated intervals values and its parents (task and then project)
  public void update(Observable o, Object arg) {
    System.out.println("Updating interval of " + this.parent.getName());

    //Duration durationToSum = Duration.between(lastDate, (LocalDateTime) arg);
    Duration durationToSum = Duration.ofMillis(Clock.getPeriod());
    this.lastDate = (LocalDateTime) arg;
    this.duration = this.duration.plus(durationToSum);
    System.out.println(this);
    parent.update(lastDate, durationToSum);
  }

  // When a task is stopped, we need to finish its intervals
  // We set this interval as not active and updates its values
  public void finish() {
    if (this.isActive) {
      this.isActive = false;
      this.lastDate = LocalDateTime.now();
      Duration durationToSum = Duration.ofMillis(Clock.getPeriod());
      this.duration = this.duration.plus(durationToSum);
      Clock.getRunningClock().deleteObserver(this);
      System.out.println("Interval finished");
      //System.out.println(this);
      //parent.update(lastDate, durationToSum);
    }
  }

  // Method to use Visitor pattern design
  public void acceptVisit(NodeVisitor visitor){
    visitor.visit(this);
    System.out.println("Interval of " + parent.name + " visited");
  }

}
