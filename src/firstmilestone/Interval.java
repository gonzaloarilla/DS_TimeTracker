package firstmilestone;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*Interval class which controls the information of a single interval.
It implements Observer to receive timer data from Clock class.
 */
public class Interval implements Observer {

  protected Node parent;
  private boolean isActive;
  private LocalDateTime initialDate;
  private LocalDateTime lastDate;
  private Duration duration;
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private JSONObject nodeJsonObject;
  private static Logger logger = LoggerFactory.getLogger(firstmilestone.Interval.class);

  public Interval(Node parent, boolean isActive) {
    // precondition
    assert parent != null;

    this.parent = parent;
    this.nodeJsonObject = new JSONObject();
    this.isActive = isActive;
    this.duration = Duration.ZERO;

    if (isActive) {
      this.lastDate = LocalDateTime.now();
      this.initialDate = lastDate.minus(Duration.ofMillis(Clock.getPeriod()));
      Clock.getRunningClock().addObserver(this);
    }
    assert invariant();

    logger.debug("Interval created and running");
  }

  private boolean invariant() {
    return logger != null;
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

  public long getDurationSeconds() {

    return this.duration.getSeconds();
  }

  public LocalDateTime getLastDate() {
    return lastDate;
  }

  public LocalDateTime getStartDate() {
    return initialDate;
  }

  public JSONObject getJsonObject() {
    return nodeJsonObject;
  }

  public Node getParent() {
    return parent;
  }

  public boolean isActive() {
    return isActive;
  }

  // Gets its own class name as String
  public String getType() {
    return this.getClass().getSimpleName().toLowerCase();
  }

  // Method to convert date and duration data into a String
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
    logger.debug("Updating interval of " + this.parent.getName());

    //Duration durationToSum = Duration.between(lastDate, (LocalDateTime) arg);
    Duration durationToSum = Duration.ofMillis(Clock.getPeriod());
    this.lastDate = (LocalDateTime) arg;
    this.duration = this.duration.plus(durationToSum);
    logger.trace("Interval updated duration = " + String.valueOf(this.duration.getSeconds()));
    //System.out.println(this);
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
      logger.debug("Interval finished with duration = " + String.valueOf(this.duration.getSeconds()));

      //parent.update(lastDate, durationToSum);
    }
  }

  // Method to use Visitor pattern design
  public void acceptVisit(NodeVisitor visitor) {
    visitor.visit(this);
    logger.debug("Interval of " + parent.name + " visited");
  }

}
