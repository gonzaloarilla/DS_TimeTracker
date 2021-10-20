import java.util.UUID;


public class Client {


  private static String createId() {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    return uuidAsString;
  }


/*  private static void testProjectWithTaskAndInterval() throws InterruptedException {
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

  }*/

  private static void testB() throws InterruptedException {

    // Root
    String rootID = createId();
    Project root = new Project(rootID,"root", null);

    // Projects setup
    String softwareDesignID = createId();
    Project softwareDesign = new Project(softwareDesignID,"software design", root);
    root.addNode(softwareDesign);

    String softwareTestingID = createId();
    Project softwareTesting = new Project(softwareTestingID,"software testing", root);
    root.addNode(softwareTesting);

    String databasesID = createId();
    Project databases = new Project(databasesID,"databases", root);
    root.addNode(databases);

    String problemsID = createId();
    Project problems = new Project(problemsID,"problems", softwareDesign);
    softwareDesign.addNode(problems);

    String projectTimeTrackerID = createId();
    Project projectTimeTracker = new Project(projectTimeTrackerID,"project time tracker", softwareDesign);
    softwareDesign.addNode(projectTimeTracker);

    // Tasks Setup
    String transportationID = createId();
    Task transportation = new Task(transportationID,"transportation", root);
    root.addNode(transportation);

    String firstListID = createId();
    Task firstList = new Task(firstListID,"first list", problems);
    problems.addNode(firstList);

    String secondListID = createId();
    Task secondList = new Task(secondListID,"second list", problems);
    problems.addNode(secondList);

    String readHandoutID = createId();
    Task readHandout = new Task(readHandoutID,"read handout", projectTimeTracker);
    projectTimeTracker.addNode(readHandout);

    String firstMilestoneID = createId();
    Task firstMilestone = new Task(firstMilestoneID,"first milestone", projectTimeTracker);
    projectTimeTracker.addNode(firstMilestone);


    // Test Execution
    System.out.println("\nTest B starts:\n");

    transportation.startTask(transportationID);
    Thread.sleep(4000);
    transportation.stopTask(transportationID);
    Thread.sleep(2000);

    firstList.startTask(firstListID);
    Thread.sleep(6000);
    secondList.startTask(secondListID);
//    System.out.println("Number of observers: " + Clock.getRunningClock().countObservers());
    Thread.sleep(4000);
//    System.out.println("Number of observers: " + Clock.getRunningClock().countObservers());
    firstList.stopTask(firstListID);
//    System.out.println("Number of observers: " + Clock.getRunningClock().countObservers());
    Thread.sleep(2000);
    secondList.stopTask(secondListID);
    Thread.sleep(2000);
    transportation.startTask(transportationID);
    Thread.sleep(4000);
    transportation.stopTask(transportationID);

    Clock.stopClock();

    System.out.println("Test B finished");


  }


  public static void main(String[] args) throws InterruptedException {
    testB();
  }

}
