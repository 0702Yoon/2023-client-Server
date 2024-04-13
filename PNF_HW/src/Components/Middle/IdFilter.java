package Components.Middle;

import java.io.IOException;
import Framework.CommonYoonFilterImpl;
import constant.Constant;

public class IdFilter extends CommonYoonFilterImpl {
  private String id;
  private StringBuilder sb;

  public IdFilter(String id) {
    this.id = id;
    sb = new StringBuilder();
  }

  @Override
  public boolean specificComputationForFilter() throws IOException {
    int checkBlank = 1;
    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    boolean isId = false;
    int byte_read = 0;

    while (true) {
      // check "CS" on byte_read from student information
      while (byte_read != '\n' && byte_read != -1) {
        byte_read = pipedInputStreamsStore.get(0).read();
        if (byte_read == ' ') {
          numOfBlank++;
        }
        if (byte_read != -1)
          buffer[idx++] = (byte) byte_read;
        if (numOfBlank < checkBlank) {
          sb.append((char) byte_read);
        }
      }
      // System.out.println(sb.toString().trim());
      String trimmed = sb.toString().trim();
      if (trimmed.length() >= Constant.STUDENT_ID_LENGTH.getIndex()
          && trimmed.substring(0, Constant.STUDENT_ID_LENGTH.getIndex()).equals(id)) {
        isId = true;
      }
      if (isId == true) {
        for (int i = 0; i < idx; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
        isId = false;
      }
      if (byte_read == -1)
        return true;

      idx = 0;
      numOfBlank = 0;
      byte_read = '\0';
      sb.setLength(0);
    }
  }
}
