import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Search {

  static private Logger logger = LoggerFactory.getLogger(Search.class);


  public static List<Node> searchByTag(Project root, String tag) {
    SearchTagVisitor search = new SearchTagVisitor(tag);

    logger.debug("Searching nodes with tag " + tag);
    root.acceptVisit(search);

    if (!search.getNodeList().isEmpty()) {
      logger.debug("Nodes found with tag " + tag);
    }

    return search.getNodeList();
  }
}
