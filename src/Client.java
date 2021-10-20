import java.util.UUID;


public class Client {

  private int lastId;

  public Client() {
    lastId = 0;
  }

  private static String createId() {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    return uuidAsString;
  }


  private static void testIntervalAndClock() throws InterruptedException {
    //Gonzalo
    Project root = new Project(createId(), "root", null);
    Task task = new Task(createId(),"Projecte DS", root);
    Interval interval = new Interval(task);
    Thread.sleep(7000);
    interval.finish();
  }

  private static void testProjectWithTaskAndInterval() throws InterruptedException {
    //Gonzalo
    Project root = new Project(createId(),"root", null); //creem root

    Project project1 = new Project(createId(),"DS", root); //creem projecte1
    Task task1 = new Task(createId(), "Projecte DS", project1); //creem task1

    project1.addNode(task1); //diem que projecte1 que conte la task1

    task1.startTask();
    Thread.sleep(3000);
    task1.stopTask();
    Clock.getRunningClock().stopClock();

    System.out.println("Id project 1: " + project1.getId());
    System.out.println("Id task 1: " + task1.getId());

  }

  private static void testB() throws InterruptedException {
    Project root = new Project(createId(),"root", null);

    Project softwareDesign = new Project(createId(),"software design", root);
    root.addNode(softwareDesign);
    Project softwareTesting = new Project(createId(),"software testing", root);
    root.addNode(softwareTesting);
    Project databases = new Project(createId(),"databases", root);
    root.addNode(databases);
    Task transportation = new Task(createId(),"transportation", root);
    root.addNode(transportation);

    Project problems = new Project(createId(),"problems", softwareDesign);
    softwareDesign.addNode(problems);
    Project projectTimeTracker = new Project(createId(),"project time tracker", softwareDesign);
    softwareDesign.addNode(projectTimeTracker);

    Task firstList = new Task(createId(),"first list", problems);
    problems.addNode(firstList);
    Task secondList = new Task(createId(),"second list", problems);
    problems.addNode(secondList);

    Task readHandout = new Task(createId(),"read handout", projectTimeTracker);
    projectTimeTracker.addNode(readHandout);
    Task firstMilestone = new Task(createId(),"first milestone", projectTimeTracker);
    projectTimeTracker.addNode(firstMilestone);


    System.out.println("\nTest B starts:\n");

    transportation.startTask();
    Thread.sleep(4000);
    transportation.stopTask();
    Thread.sleep(2000);
    firstList.startTask();
    Thread.sleep(6000);
    secondList.startTask();
    Thread.sleep(4000);
    firstList.stopTask();
    Thread.sleep(2000);
    secondList.stopTask();
    Thread.sleep(2000);
    transportation.startTask();
    Thread.sleep(4000);
    transportation.stopTask();

    Clock.getRunningClock().stopClock();

    System.out.println("Test B finished");


  }


  public static void main(String[] args) throws InterruptedException {
    testB();
  }

}
