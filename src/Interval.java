import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

  private static Node parent;
  private boolean isActive;
  private LocalDateTime initialDate;
  private LocalDateTime lastDate;
  private Duration duration;
  protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private String timeConversion; // s'ha d'utilitzar la classe DateTimeFormatter


  public Interval(Node parent) {
    this.parent = parent;
    isActive = true;
    initialDate = LocalDateTime.now();
    //initialDate = initialDate.minus(Duration.ofMillis(Clock.getRunningClock().getPeriod()));
    lastDate = initialDate;
    this.timeConversion = "";

    Clock.getRunningClock().addObserver(this);
    //System.out.println("Interval created and running");
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
    String info = "Interval:    " + this.initialDate.format(dateTimeFormatter) + "   "
        + this.lastDate.format(dateTimeFormatter) + "   " + this.duration.toSeconds();
    return info;
  }

  @Override
  public void update(Observable o, Object arg) {
    System.out.println("Updating interval of " + this.parent.getName());


    Duration durationToSum = Duration.between(lastDate, (LocalDateTime) arg);
    this.lastDate = (LocalDateTime) arg;
    this.duration = Duration.between(initialDate, lastDate);
    setTimeConversion(duration);
    System.out.println(this);
    parent.update(lastDate, initialDate, durationToSum);
  }

  public void finish() {
    if (this.isActive) {
      isActive = false;
      Duration durationToSum = Duration.between(lastDate, LocalDateTime.now());
      duration = Duration.between(initialDate, LocalDateTime.now());
      setTimeConversion(duration);
      Clock.getRunningClock().deleteObserver(this);
      System.out.println("Interval finished");
      //System.out.println(this);
      //parent.update(lastDate, initialDate, durationToSum);
    }
  }

}
