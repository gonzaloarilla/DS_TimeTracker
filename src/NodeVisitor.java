// Setting the classes that can visit
public interface NodeVisitor {
  void visit(Node node);
  void visit(Interval interval);
}