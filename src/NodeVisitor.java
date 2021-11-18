/*
Visitor Interface with visit methods for Nodes and Intervals
 */
public interface NodeVisitor {
  void visit(Node node);

  void visit(Interval interval);
}