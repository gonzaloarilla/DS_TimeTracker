import java.io.IOException;
import java.util.UUID;
import java.util.Scanner;


public class Client {

  private static synchronized String createId() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }


  // Test B from project document
  private static void testB() throws InterruptedException, IOException {

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
    Thread.sleep(4000);

    firstList.stopTask(firstListID);
    Thread.sleep(2000);
    secondList.stopTask(secondListID);
    Thread.sleep(2000);

    transportation.startTask(transportationID);
    Thread.sleep(4000);
    transportation.stopTask(transportationID);

    Clock.stopClock();

    System.out.println("Test B finished");

    PersistenceManager.saveData(root, "NodeData.json");

    System.out.println("Data saved");

  }


  // Test B from project document using persistence and adding some more seconds
  private static void testBWithPersistence() throws InterruptedException, IOException {

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
    Thread.sleep(4000);

    firstList.stopTask(firstListID);
    Thread.sleep(2000);
    secondList.stopTask(secondListID);
    Thread.sleep(2000);

    transportation.startTask(transportationID);
    Thread.sleep(4000);
    transportation.stopTask(transportationID);

    Clock.stopClock();

    System.out.println("Test B finished");


    // PERSISTENCE:
    System.out.println("\n");
    System.out.println("\nSaving Data:\n");
    PersistenceManager.saveData(root, "NodeData.json");
    System.out.println("\n");

    Thread.sleep(1000);

    root = new Project(rootID,"root", null);

    System.out.println("\nLoading Data:\n");
    root = (Project) PersistenceManager.loadData("NodeData.json");
    System.out.println("\n");

    root.startTask(transportationID);
    Thread.sleep(4000);
    root.stopTask(transportationID);
    Clock.stopClock();

    PersistenceManager.saveData(root, "NodeData.json");

    // Showing the menu to the user
    JSONMenu(root, "NodeData.json");

  }



  // Menu that let the user save or load data using JSON
  public static void JSONMenu(Node root, String filename) throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\nWhat do you want to do?\n");
    System.out.println("1. Save JSON");
    System.out.println("2. Load JSON\n");

    System.out.print("Option (1 or 2): ");
    int option = scanner.nextInt();

    switch (option) {
      case 1: // Save
        PersistenceManager.saveData(root, filename);
        System.out.println("Data has been saved correctly");
        break;
      case 2: // Load
        PersistenceManager.loadData(filename);
        System.out.println("Data has been loaded correctly");
        break;
      default:
        System.out.println("Error, invalid input!\n");
        break;
    }
    scanner.close();
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    testB();
    //testBWithPersistence();
  }

}
