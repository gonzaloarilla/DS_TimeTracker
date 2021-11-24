import java.util.ArrayList;
import java.util.List;

public class SearchTagVisitor implements NodeVisitor {

  private String tag;
  private Boolean tagFound;
  private List<Node> nodeList;

  SearchTagVisitor(String tag) {
    this.tagFound = false;
    this.nodeList = new ArrayList<>();
    this.tag = tag.toLowerCase();
  }

  public Boolean getTagFound() {
    return tagFound;
  }

  public List<Node> getNodeList() {
    return nodeList;
  }
  @Override
  public void visit(Node node) {

  }

  @Override
  public void visit(Interval interval) {

  }
}
