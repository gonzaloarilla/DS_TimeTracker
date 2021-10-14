import com.sun.jdi.PrimitiveValue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {

  private static Node parent;
  private boolean isActive;
  private LocalDateTime initialDate;
  private LocalDateTime currentDate;
  private Duration duration;

  private String timeConversion;


  public Interval(Node parent) {
    this.parent = parent;
    isActive = true;
    initialDate = LocalDateTime.now();
    this.timeConversion = "";

    //currentDate = initialDate.minus(Duration.ofMillis(Clock.getRunningClock().getPeriod()));
    Clock.getRunningClock().addObserver(this);
    System.out.println("Interval created and running");
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


  @Override
  public void update(Observable o, Object arg) {
    duration = Duration.between(initialDate, (LocalDateTime) arg);
    setTimeConversion(duration);
    //date = (LocalDateTime) arg;
    System.out.println("Interval updated");
    System.out.println("Total duration: " + getTimeConversion());


    //TODO: update Parent info
  }

  public void finish() {
    isActive = false;
    duration = Duration.between(initialDate, LocalDateTime.now());
    setTimeConversion(duration);
    Clock.getRunningClock().deleteObserver(this);
    System.out.println("Interval finished");
    System.out.println("Total duration: " + getTimeConversion());

  }

}
