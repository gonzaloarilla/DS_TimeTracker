package webserver;

import firstmilestone.*;

public class MainWebServer {


  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Node root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock
    Clock.getRunningClock();
    new WebServer(root);
  }

  public static Node makeTreeCourses() {
    // Root
    Project root = new Project(0, "root", null);

    // Projects setup
    Project softwareDesign = new Project(1, "software design", root);
    softwareDesign.addTag("java");
    softwareDesign.addTag("flutter");
    // description:
    softwareDesign.setDescription("Descripció del project software design... Disseny del software.");
    root.addNode(softwareDesign);

    Project softwareTesting = new Project(2, "software testing", root);
    softwareTesting.addTag("c++");
    softwareTesting.addTag("Java");
    softwareTesting.addTag("python");
    root.addNode(softwareTesting);

    Project databases = new Project(3, "databases", root);
    databases.addTag("SQL");
    databases.addTag("python");
    databases.addTag("C++");
    root.addNode(databases);

    Project problems = new Project(4, "problems", softwareDesign);
    softwareDesign.addNode(problems);

    Project projectTimeTracker = new Project(5,
        "project time tracker", softwareDesign);
    softwareDesign.addNode(projectTimeTracker);

    // Tasks Setup
    Task transportation = new Task(6, "transportation", root);
    root.addNode(transportation);

    Task firstList = new Task(7, "first list", problems);
    firstList.addTag("java");
    problems.addNode(firstList);

    Task secondList = new Task(8, "second list", problems);
    secondList.addTag("Dart");
    problems.addNode(secondList);

    Task readHandout = new Task(9, "read handout", projectTimeTracker);
    projectTimeTracker.addNode(readHandout);

    Task firstMilestone = new Task(10, "first milestone", projectTimeTracker);
    firstMilestone.addTag("Java");
    firstMilestone.addTag("IntelliJ");
    projectTimeTracker.addNode(firstMilestone);

    return root;
  }

  }