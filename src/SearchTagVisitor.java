import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Class which implements the visit() methods from SearchTagVisitor
 */
public class SearchTagVisitor implements NodeVisitor {
  private static Logger logger = LoggerFactory.getLogger(SearchTagVisitor.class);
  private final String tag;
  private List<Node> nodeList;

  // It's important to lower case the tag we're going to search
  SearchTagVisitor(String tag) {
    this.nodeList = new ArrayList<>();
    this.tag = tag.toLowerCase();
  }

  public List<Node> getNodeList() {
    return nodeList;
  }

  @Override
  public void visit(Node node) {
    for (String tag : node.getTagList()) {
      if (tag.toLowerCase().equals(this.tag)) {
        nodeList.add(node);
        break;
      }
    }
  }

  //Intervals don't have tags, so it's not needed to search in them
  @Override
  public void visit(Interval interval) {
    // Do nothing
  }
}
