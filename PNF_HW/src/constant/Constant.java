package constant;

public enum Constant {
  STUDENT_ID(0), FIRST_NAME(1), LAST_NAME(2), MAJOR(3), TAKE_COURSE(4), STUDENT_ID_LENGTH(
      4), PRELECTURE(3);

  private final int indexNumber;

  Constant(int indexNumber) {
    this.indexNumber = indexNumber;
  }

  public int getIndex() {
    return indexNumber;

  }

}
