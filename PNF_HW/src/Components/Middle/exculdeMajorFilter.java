package Components.Middle;

import java.io.IOException;
import Framework.CommonYoonFilterImpl;
import constant.Constant;

public class exculdeMajorFilter extends CommonYoonFilterImpl {
  private String major;
  private StringBuilder sb;

  public exculdeMajorFilter(String major) {
    this.major = major;
    sb = new StringBuilder();
  }

  @Override
  public boolean specificComputationForFilter() throws IOException {
    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    int byte_read = 0;

    while (true) {
      // check "CS" on byte_read from student information
      while (byte_read != '\n' && byte_read != -1) {
        byte_read = pipedInputStreamsStore.get(0).read();
        if (byte_read == ' ')
          numOfBlank++;
        if (byte_read != -1)
          buffer[idx++] = (byte) byte_read;
        if (numOfBlank == Constant.MAJOR.getIndex())
          sb.append((char) byte_read);
      }

      if (!sb.toString().trim().equals(major)) {
        for (int i = 0; i < idx; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
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
