import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Clock class is a singleton which keeps track of a timer and uses the observer pattern.
It extends Observable to communicate the time information to its obververs.
 */
public class Clock extends Observable {
  private static final int period = 2000;
  private static final int delay = 0;
  private static Clock runningClock = null;
  private final Timer timer;
  private final TimerTask timerTask;
  private LocalDateTime lastDate;
  static private Logger logger = LoggerFactory.getLogger("Clock.class");

  // Private constructor so we just have one instance of Clock
  private Clock() {
    timer = new Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        update();
      }
    };
    timer.schedule(timerTask, delay, period);
    assert invariant();
  }

  private boolean invariant() {
    return logger != null;
  }

  // This method implements Singleton
  public static synchronized Clock getRunningClock() {
    if (runningClock == null) {
      runningClock = new Clock();
      System.out.println("Clock created");
    }
    return runningClock;
  }

  // Stops clock and delete all its observers
  public static void stopClock() {
    if (runningClock != null) {
      runningClock.deleteObservers();
      runningClock.timer.cancel();
      runningClock = null;
      System.out.println("Clock stopped");
    }
  }

  // Updates Clock's date and notify to observers
  private void update() {
    lastDate = LocalDateTime.now();
    //System.out.println("Clock updated");
    setChanged();
    notifyObservers(lastDate);
  }

  public static int getPeriod() {
    return period;
  }

}
