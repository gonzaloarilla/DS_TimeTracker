public interface NodeVisitor {
  void visit(Node node);
  void visit(Interval interval);
}