import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTagVisitor implements NodeVisitor {
  private static Logger logger = LoggerFactory.getLogger(SearchTagVisitor.class);
  private String tag;
  private List<Node> nodeList;

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

  @Override
  public void visit(Interval interval) {
    // Do nothing
  }
}
