package Components.AddFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Framework.CommonYoonFilterImpl;
import constant.Constant;

public class AddCourseFilter extends CommonYoonFilterImpl {

  private ArrayList<String> courseNumber;

  public AddCourseFilter(ArrayList<String> courseList) {
    this.courseNumber = courseList;
  };

  @Override
  public boolean specificComputationForFilter() throws IOException {

    int numOfBlank = 0;
    int idx = 0;
    byte[] buffer = new byte[64];
    int byte_read = 0;

    StringBuilder studentSb = new StringBuilder();
    List<String> studentCourseList;

    while (true) {
      // check "CS" on byte_read from student information
      while (byte_read != '\n' && byte_read != -1) {
        byte_read = pipedInputStreamsStore.get(0).read();
        if (byte_read == ' ')
          numOfBlank++;
        if (byte_read != -1)
          buffer[idx++] = (byte) byte_read;
        if (numOfBlank >= Constant.TAKE_COURSE.getIndex()) {
          studentSb.append((char) byte_read);
        }
      }

      studentCourseList = Arrays.asList(studentSb.toString().trim().split(" "));
      studentSb.setLength(0);

      if (buffer[0] != '\0') {
        for (int i = 0; i < idx - 2; i++)
          pipedOutputStreamsStore.get(0).write((char) buffer[i]);
        for (String courseId : courseNumber) {
          if (!studentCourseList.contains(courseId)) {
            pipedOutputStreamsStore.get(0).write(' ');
            for (char courseIdChar : courseId.toCharArray()) {
              pipedOutputStreamsStore.get(0).write(courseIdChar);
            }
          } else {
          }
        }
        pipedOutputStreamsStore.get(0).write('\n');
      }

      if (byte_read == -1)
        return true;

      idx = 0;
      numOfBlank = 0;
      byte_read = 0;
      Arrays.fill(buffer, (byte) 0);

    }

  }

}
