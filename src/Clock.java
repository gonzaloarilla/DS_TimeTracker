import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
  private static final int period = 2000;
  private static final int delay = 0;
  private static Clock runningClock = null;
  private final Timer timer;
  private final TimerTask timerTask;
  private LocalDateTime lastDate;


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
  }

  // This method implements Singleton
  public static synchronized Clock getRunningClock() {
    if (runningClock == null) {
      runningClock = new Clock();
      System.out.println("Clock created");
    }
    return runningClock;
  }

  // Stops clock and delete all its oberservers
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

  public static int getPeriod(){
    return period;
  }

}
