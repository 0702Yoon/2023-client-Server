/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.Course.PreLectureListFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;
import merge.MergeFilter;

public class LifeCycleManager4 extends ManagerSkill {

  public static void main(String[] args) {
    try {
      CommonYoonFilter studentFilter = new SourceFilter("Students.txt");

      CommonYoonFilter courseFilter = new SourceFilter("Courses.txt");

      CommonYoonFilter preLectureListFilter = new PreLectureListFilter();

      CommonYoonFilter outOkFilter = new SinkFilter("okayStudent.txt");
      CommonYoonFilter outNoOkFilter = new SinkFilter("noOkStudent.txt");

      CommonYoonFilter mergeFilter = new MergeFilter();

      courseFilter.connectOutputTo(preLectureListFilter.getPipedInputStreamVector());

      mergeFilter.connectInputTo(makeOutputStreamVector(preLectureListFilter, studentFilter));
      // preFilter의 아웃이랑 연결
      mergeFilter.connectOutputTo(makeInputStreamVector(outOkFilter, outNoOkFilter));
      // outOkFilter의 입력으로 연결


      Thread courseThread = new Thread(courseFilter);
      Thread preCourseListThread = new Thread(preLectureListFilter);
      Thread studentListThread = new Thread(studentFilter);
      Thread mergeThread = new Thread(mergeFilter);
      Thread writeOkThread = new Thread(outOkFilter);
      Thread writeNoOkThread = new Thread(outNoOkFilter);

      courseThread.start();
      preCourseListThread.start();
      studentListThread.start();
      mergeThread.start();
      writeNoOkThread.start();
      writeOkThread.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
