package webserver;

import firstmilestone.Node;
import firstmilestone.Clock;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Node root = null;
    root.makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock

    new WebServer(root);
  }
}