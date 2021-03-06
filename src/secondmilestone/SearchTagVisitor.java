package secondmilestone;

import firstmilestone.Interval;
import firstmilestone.Node;
import firstmilestone.NodeVisitor;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Class which implements the visit() methods from firstmilestone.SearchTagVisitor
 */
public class SearchTagVisitor implements NodeVisitor {
  private static Logger logger = LoggerFactory.getLogger(secondmilestone.SearchTagVisitor.class);
  private final String tag;
  private List<Node> nodeList;

  // It's important to lower case the tag we're going to search
  public SearchTagVisitor(String tag) {
    this.nodeList = new ArrayList<>();
    this.tag = tag.toLowerCase();
  }

  public List<Node> getNodeList() {
    return nodeList;
  }



  //Intervals don't have tags, so it's not needed to search in them
  @Override
  public void visit(Interval interval) {
    // Do nothing
    logger.debug("Visiting an interval as searching for tag " + this.tag);
  }

  @Override
  public void visit(Node node) {
    for (String tag : node.getTagList()) {
      logger.debug("Visiting a node as searching for tag " + this.tag);
      if (tag.toLowerCase().equals(this.tag)) {
        logger.debug("Node " + node.name + " was found");
        nodeList.add(node);
        break;
      }
    }
  }
}
