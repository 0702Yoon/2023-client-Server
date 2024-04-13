package merge;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Framework.CommonYoonFilterImpl;

public class MergeFilter extends CommonYoonFilterImpl {

  public boolean specificComputationForFilter() throws IOException {
    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    boolean isOK = false;
    int byte_read = 0;
    StringBuilder sb = new StringBuilder();
    StringBuilder preSb = new StringBuilder();
    StringBuilder studentSb = new StringBuilder();

    Map<String, List<String>> courseMap = new HashMap<>();
    String course;
    List<String> preCourse;
    List<String> studentCourseList;

    PipedInputStream preCourseStream = pipedInputStreamsStore.get(0);
    PipedInputStream studentStream = pipedInputStreamsStore.get(1);
    PipedOutputStream satisfiedStream = pipedOutputStreamsStore.get(0);
    PipedOutputStream notSatisfiedStream = pipedOutputStreamsStore.get(1);


    while (true) {
      // check "CS" on byte_read from student information

      while (byte_read != '\n' && byte_read != -1) {
        byte_read = preCourseStream.read();
        if (byte_read == ' ')
          numOfBlank++;
        if (byte_read != -1)
          idx++;
        if (idx < 6)
          sb.append((char) byte_read);
        if (numOfBlank > 2 && byte_read != -1) {
          preSb.append((char) byte_read);
        }
      }

      course = sb.toString().trim();
      sb.setLength(0);

      preCourse = new ArrayList<>(Arrays.asList(preSb.toString().trim().split(" ")));
      courseMap.put(course, preCourse);
      preSb.setLength(0);

      if (byte_read == -1)
        break;
      idx = 0;
      numOfBlank = 0;
      byte_read = '\0';
    }
    idx = 0;
    numOfBlank = 0;
    byte_read = 0;

    while (true) {

      while (byte_read != '\n' && byte_read != -1) {
        byte_read = studentStream.read();
        if (byte_read == ' ')
          numOfBlank++;
        if (byte_read != -1)
          buffer[idx++] = (byte) byte_read;
        if (numOfBlank > 3 && byte_read != -1) {
          studentSb.append((char) byte_read);
        }
        if (byte_read == -1)
          break;
      }
      studentCourseList = new ArrayList<>(Arrays.asList(studentSb.toString().trim().split(" ")));
      studentSb.setLength(0);
      loop: for (Map.Entry<String, List<String>> entry : courseMap.entrySet()) {
        if (studentCourseList.contains(entry.getKey())) {
          for (String preCourseMember : entry.getValue()) {
            if (studentCourseList.contains(preCourseMember)) {
              isOK = true;
            } else {
              isOK = false;
              break loop;
            }
          }
        }
      }
      if (buffer[0] != 13) {
        if (isOK) {
          for (int i = 0; i < idx; i++)
            satisfiedStream.write((char) buffer[i]);
        } else {
          for (int i = 0; i < idx; i++) {
            notSatisfiedStream.write((char) buffer[i]);

          }
        }
        isOK = false;
      }
      if (byte_read == -1)
        return true;
      idx = 0;
      numOfBlank = 0;
      byte_read = '\0';
    }
  }

}
