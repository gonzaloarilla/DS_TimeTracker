import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Class which will search Nodes based on tags or future implementations
 */
public class Search {

  private static Logger logger = LoggerFactory.getLogger(Search.class);

  // This methods allows to search Nodes by its tags. It uses Visitor design pattern.
  public static List<Node> searchByTag(Project root, String tag) {
    SearchTagVisitor search = new SearchTagVisitor(tag);
    logger.debug("Searching nodes with tag " + tag);
    root.acceptVisit(search);
    if (!search.getNodeList().isEmpty()) {
      logger.debug("Nodes found with tag " + tag);
    } else {
      logger.debug("No nodes found with tag " + tag);
    }
    return search.getNodeList();
  }
}
