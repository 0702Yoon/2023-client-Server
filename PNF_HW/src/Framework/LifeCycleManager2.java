/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.util.ArrayList;
import Components.AddFilter.AddCourseFilter;
import Components.Middle.MajorFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager2 {
  public static void main(String[] args) {
    try {
      CommonYoonFilter filter1 = new SourceFilter("Students.txt");
      CommonYoonFilter filter2 = new SinkFilter("Output3.txt");
      CommonYoonFilter majorfilter = new MajorFilter("EE");

      ArrayList<String> courseList = new ArrayList<>();
      courseList.add("23456");
      CommonYoonFilter addFilter = new AddCourseFilter(courseList);


      filter1.connectOutputTo(majorfilter.getPipedInputStreamVector());
      majorfilter.connectOutputTo(addFilter.getPipedInputStreamVector());
      addFilter.connectOutputTo(filter2.getPipedInputStreamVector());

      Thread thread1 = new Thread(filter1);
      Thread thread2 = new Thread(filter2);
      Thread thread4 = new Thread(addFilter);
      Thread thread5 = new Thread(majorfilter);


      thread1.start();
      thread2.start();

      thread4.start();
      thread5.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
