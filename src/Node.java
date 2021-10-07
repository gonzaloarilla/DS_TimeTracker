import java.time.Duration;
import java.time.LocalDateTime;

public interface Node {

  void setName(String name);
  String getName();
  void startTask();
  void stopTask();
  Duration getDuration();
  LocalDateTime getDate();
  boolean isActive();

}
