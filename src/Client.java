public class Client {

  public Client() {

  }


  private static void testIntervalAndClock() throws InterruptedException {
    //Gonzalo
    Task task = new Task("Projecte DS");
    Interval interval = new Interval(task);
    Thread.sleep(7000);
    interval.finish();
  }

  private static void testProjectWithTaskAndIntervals() throws InterruptedException {
    //Gonzalo
    Project project1 = new Project("DS"); //creem projecte1
    Task task1 = new Task("Projecte DS"); //creem task1
    project1.addNode(task1); //diem que projecte1 que conte la task1
    task1.startTask();
    Thread.sleep(7000);

  }


  public static void main(String[] args) throws InterruptedException {

    testIntervalAndClock();
  }

}
