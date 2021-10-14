public interface NodeVisitor {
  void visit(Project project);
  void visit(Task task);
}
