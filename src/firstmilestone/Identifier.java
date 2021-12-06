package firstmilestone;

import java.util.List;

public class Identifier {
  public int id;
  private List<Integer> ids;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Identifier() {
  }

  public int getNextId() {
    var newId = getId() + 1;
    ids.add(newId);
    return newId;
  }

  private boolean checkId(int id) {
    for (int i : ids) {
      if (i == id) {
        // id exists
        return true;
      }
      // id does not exists
      return false;
    }
    return false;
  }
}
