package secondmilestone;



import firstmilestone.Node;
import firstmilestone.Project;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Class which will search Nodes based on tags or future implementations
 */
public class Search {

  private static Logger logger = LoggerFactory.getLogger(secondmilestone.Search.class);

  // This methods allows to search Nodes by its tags. It uses Visitor design pattern.
  public static List<Node> searchByTag(Project root, String tag) {
    SearchTagVisitor search = new SearchTagVisitor(tag);
    logger.debug("Searching nodes with tag " + tag);
    root.acceptVisit(search);

    if (!search.getNodeList().isEmpty()) {
      String nodesFound = new String();
      for (Node node : search.getNodeList()) {
        nodesFound += node.name + ", ";
      }
      //Delete the last ", " of the string
      nodesFound = nodesFound.substring(0, nodesFound.length() - 2);
      logger.info(search.getNodeList().size() + " node/s found with tag " + tag + ": " + nodesFound);
    } else {
      logger.info("No nodes found with tag " + tag);
    }
    return search.getNodeList();
  }
}
