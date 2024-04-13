package Components.Course;

import java.io.IOException;
import Framework.CommonYoonFilterImpl;
import constant.Constant;

public class PreLectureListFilter extends CommonYoonFilterImpl {
  @Override
  public boolean specificComputationForFilter() throws IOException {
    int checkBlank = Constant.PRELECTURE.getIndex();
    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    boolean isExistPreLecture = false;
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
        if (numOfBlank == checkBlank) {
          isExistPreLecture = true;
        }
      }
      if (isExistPreLecture == true) {
        for (int i = 0; i < idx; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
        isExistPreLecture = false;
      }
      if (byte_read == -1)
        return true;

      idx = 0;
      numOfBlank = 0;
      byte_read = '\0';
    }
  }
}
