package Components.Course;

import java.io.IOException;
import Framework.CommonYoonFilterImpl;

public class DeleteCourse extends CommonYoonFilterImpl {
  private char[] deleteCourse;

  public DeleteCourse(String deleteCourse) {
    this.deleteCourse = deleteCourse.toCharArray();
  }

  @Override
  public boolean specificComputationForFilter() throws IOException {

    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    boolean is17651 = false;
    int byte_read = 0;
    int startIdx = 0;
    int endIdx = 0;

    while (true) {
      // check "CS" on byte_read from student information
      while (byte_read != '\n' && byte_read != -1) {
        byte_read = pipedInputStreamsStore.get(0).read();
        if (byte_read == ' ')
          numOfBlank++;
        if (byte_read != -1)
          buffer[idx++] = (byte) byte_read;
        if (numOfBlank > 4 && buffer[idx - 6] == deleteCourse[0]
            && buffer[idx - 5] == deleteCourse[1] && buffer[idx - 4] == deleteCourse[2]
            && buffer[idx - 3] == deleteCourse[3] && buffer[idx - 2] == deleteCourse[4]) {
          is17651 = true;
          startIdx = idx - 6;
          endIdx = idx - 2;
        }
      }
      if (is17651 == true) {
        for (int i = 0; i < startIdx - 1; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
        for (int i = endIdx + 1; i < idx; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
        is17651 = false;
      }
      if (byte_read == -1)
        return true;
      idx = 0;
      numOfBlank = 0;
      byte_read = '\0';
    }

  }

}

