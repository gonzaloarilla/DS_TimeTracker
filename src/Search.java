import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Search {

  static private Logger logger = LoggerFactory.getLogger(Search.class);


  public List<Node> searchByTag(Project root, String tag) {
    SearchTagVisitor operation = new SearchTagVisitor(tag);

    logger.debug("Searching nodes with tag " + tag);
    root.acceptVisit(operation);

    if (operation.getTagFound()) {
      logger.debug("Nodes found with tag " + tag);
    }

    return operation.getNodeList();
  }
}
