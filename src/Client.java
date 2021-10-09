public class Client {

  public Client() {

  }


  private static void testIntervalAndClock() throws InterruptedException {
    //Gonzalo
    Task task = new Task();
    Interval interval = new Interval(task);
    Thread.sleep(7000);
    interval.finish();
  }

  public static void main(String[] args) throws InterruptedException {

    testIntervalAndClock();


  }
}
