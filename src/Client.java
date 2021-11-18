import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Client {

  static private Logger logger = LoggerFactory.getLogger("Client.class");

  private static synchronized String createId() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }


  // Test B from project document
  private static void testB() throws InterruptedException, IOException {

    // Root
    String rootId = createId();
    Project root = new Project(rootId, "root", null);

    // Projects setup
    String softwareDesignId = createId();
    Project softwareDesign = new Project(softwareDesignId, "software design", root);
    root.addNode(softwareDesign);

    String softwareTestingId = createId();
    Project softwareTesting = new Project(softwareTestingId, "software testing", root);
    root.addNode(softwareTesting);

    String databasesId = createId();
    Project databases = new Project(databasesId, "databases", root);
    root.addNode(databases);

    String problemsId = createId();
    Project problems = new Project(problemsId, "problems", softwareDesign);
    softwareDesign.addNode(problems);

    String projectTimeTrackerId = createId();
    Project projectTimeTracker = new Project(projectTimeTrackerId,
        "project time tracker", softwareDesign);
    softwareDesign.addNode(projectTimeTracker);

    // Tasks Setup
    String transportationId = createId();
    Task transportation = new Task(transportationId, "transportation", root);
    root.addNode(transportation);

    String firstListId = createId();
    Task firstList = new Task(firstListId, "first list", problems);
    problems.addNode(firstList);

    String secondListId = createId();
    Task secondList = new Task(secondListId, "second list", problems);
    problems.addNode(secondList);

    String readHandoutId = createId();
    Task readHandout = new Task(readHandoutId, "read handout", projectTimeTracker);
    projectTimeTracker.addNode(readHandout);

    String firstMilestoneId = createId();
    Task firstMilestone = new Task(firstMilestoneId, "first milestone", projectTimeTracker);
    projectTimeTracker.addNode(firstMilestone);


    // Test Execution
    System.out.println("\nTest B starts:\n");

    transportation.startTask(transportationId);
    Thread.sleep(4000);
    transportation.stopTask(transportationId);
    Thread.sleep(2000);

    firstList.startTask(firstListId);
    Thread.sleep(6000);
    secondList.startTask(secondListId);
    Thread.sleep(4000);

    firstList.stopTask(firstListId);
    Thread.sleep(2000);
    secondList.stopTask(secondListId);
    Thread.sleep(2000);

    transportation.startTask(transportationId);
    Thread.sleep(4000);
    transportation.stopTask(transportationId);

    Clock.stopClock();

    System.out.println("Test B finished");

    PersistenceManager.saveData(root, "NodeData.json");

    System.out.println("Data saved");

  }


  // Test B from project document using persistence and adding some more seconds
  private static void testBWithPersistence() throws InterruptedException, IOException {

    // Root
    String rootId = createId();
    Project root = new Project(rootId, "root", null);

    // Projects setup
    String softwareDesignId = createId();
    Project softwareDesign = new Project(softwareDesignId, "software design", root);
    root.addNode(softwareDesign);

    String softwareTestingId = createId();
    Project softwareTesting = new Project(softwareTestingId, "software testing", root);
    root.addNode(softwareTesting);

    String databasesId = createId();
    Project databases = new Project(databasesId, "databases", root);
    root.addNode(databases);

    String problemsId = createId();
    Project problems = new Project(problemsId, "problems", softwareDesign);
    softwareDesign.addNode(problems);

    String projectTimeTrackerId = createId();
    Project projectTimeTracker = new Project(projectTimeTrackerId,
        "project time tracker", softwareDesign);
    softwareDesign.addNode(projectTimeTracker);

    // Tasks Setup
    String transportationId = createId();
    Task transportation = new Task(transportationId, "transportation", root);
    root.addNode(transportation);

    String firstListId = createId();
    Task firstList = new Task(firstListId, "first list", problems);
    problems.addNode(firstList);

    String secondListId = createId();
    Task secondList = new Task(secondListId, "second list", problems);
    problems.addNode(secondList);

    String readHandoutId = createId();
    Task readHandout = new Task(readHandoutId, "read handout", projectTimeTracker);
    projectTimeTracker.addNode(readHandout);

    String firstMilestoneId = createId();
    Task firstMilestone = new Task(firstMilestoneId, "first milestone", projectTimeTracker);
    projectTimeTracker.addNode(firstMilestone);



    // Test Execution
    System.out.println("\nTest B starts:\n");

    transportation.startTask(transportationId);
    Thread.sleep(4000);
    transportation.stopTask(transportationId);
    Thread.sleep(2000);

    firstList.startTask(firstListId);
    Thread.sleep(6000);
    secondList.startTask(secondListId);
    Thread.sleep(4000);

    firstList.stopTask(firstListId);
    Thread.sleep(2000);
    secondList.stopTask(secondListId);
    Thread.sleep(2000);

    transportation.startTask(transportationId);
    Thread.sleep(4000);
    transportation.stopTask(transportationId);

    Clock.stopClock();

    System.out.println("Test B finished");


    // SAVE DATA
    System.out.println("\n");
    System.out.println("\nSaving Data:\n");
    PersistenceManager.saveData(root, "NodeData.json");
    System.out.println("\n");

    Thread.sleep(1000);

    root = new Project(rootId, "root", null);

    // LOAD DATA
    System.out.println("\nLoading Data:\n");
    root = (Project) PersistenceManager.loadData("NodeData.json");
    System.out.println("\n");


    // START AND STOP ONE TASK AGAIN
    root.startTask(transportationId);
    Thread.sleep(4000);
    root.stopTask(transportationId);
    Clock.stopClock();

    PersistenceManager.saveData(root, "NodeData.json");

    // Showing the menu to the user
    jsonMenu(root, "NodeData.json");

  }

  // Menu that let the user save or load data using JSON
  public static void jsonMenu(Node root, String filename) throws IOException {
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
    //testLoad();

    //testB();
    testBWithPersistence();
  }


  private static void testLoad() throws IOException {

    Project root = (Project) PersistenceManager.loadData("NodeData.json");
    PersistenceManager.saveData(root, "LoadedData.json");
  }
}
