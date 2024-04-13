/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.util.ArrayList;
import Components.AddFilter.AddCourseFilter;
import Components.Middle.MajorFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager extends ManagerSkill {
  public static void main(String[] args) {
    try {
      CommonYoonFilter getStudentList = new SourceFilter("Students.txt");
      CommonYoonFilter writeAddCourse = new SinkFilter("Output2.txt");
      CommonYoonFilter selectCS = new MajorFilter("CS");

      ArrayList<String> courseList = new ArrayList<>();
      courseList.add("12345");
      courseList.add("23456");

      CommonYoonFilter addFilter = new AddCourseFilter(courseList);

      getStudentList.connectOutputTo(selectCS.getPipedInputStreamVector());
      selectCS.connectOutputTo(addFilter.getPipedInputStreamVector());
      addFilter.connectOutputTo(writeAddCourse.getPipedInputStreamVector());


      Thread getStudentListTh = new Thread(getStudentList);
      Thread writeAddCourseTh = new Thread(writeAddCourse);
      Thread addFilterTh = new Thread(addFilter);
      Thread selectCSTh = new Thread(selectCS);


      getStudentListTh.start();
      writeAddCourseTh.start();
      addFilterTh.start();
      selectCSTh.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
