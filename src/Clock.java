import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
  private static int period = 2000;
  private static int delay = 0;
  private static Clock runningClock;
  private Timer timer;
  private TimerTask timerTask;
  private LocalDateTime date;

  //Private constructor so we just have one instance of Clock
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

  //This method implements Singleton
  public static Clock getRunningClock() {
    if (runningClock == null) {
      runningClock = new Clock();
      System.out.println("Clock created");
    }
    return runningClock;
  }

  public static void stopClock() {
    if (runningClock != null) {
      runningClock.deleteObservers();
      runningClock.timer.cancel();
      runningClock = null;
      System.out.println("Clock stopped");
    }
  }

  //Updates Clock's date and notify to obervers
  private void update() {
    date = LocalDateTime.now();
    System.out.println("Clock updated");
    setChanged();
    notifyObservers(date);
  }

  public int getPeriod(){
    return period;
  }

}
