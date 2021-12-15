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

    return root;
  }

  }