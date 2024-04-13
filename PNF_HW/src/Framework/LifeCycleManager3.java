/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import Components.Course.DeleteCourse;
import Components.Middle.IdFilter;
import Components.Middle.exculdeMajorFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager3 {
  public static void main(String[] args) {
    try {
      CommonYoonFilter filter1 = new SourceFilter("Students.txt");
      CommonYoonFilter filter2 = new SinkFilter("Output4.txt");

      CommonYoonFilter idFilter = new IdFilter("2013");
      CommonYoonFilter notCs = new exculdeMajorFilter("CS");
      CommonYoonFilter delete17651 = new DeleteCourse("17651");
      CommonYoonFilter delete17652 = new DeleteCourse("17652");

      filter1.connectOutputTo(idFilter.getPipedInputStreamVector());
      idFilter.connectOutputTo(notCs.getPipedInputStreamVector());
      notCs.connectOutputTo(delete17651.getPipedInputStreamVector());
      delete17651.connectOutputTo(delete17652.getPipedInputStreamVector());
      delete17652.connectOutputTo(filter2.getPipedInputStreamVector());

      Thread thread1 = new Thread(filter1);
      Thread thread2 = new Thread(filter2);
      Thread thread3 = new Thread(idFilter);
      Thread thread4 = new Thread(notCs);
      Thread thread5 = new Thread(delete17651);
      Thread thread6 = new Thread(delete17652);

      thread1.start();
      thread2.start();
      thread3.start();
      thread4.start();
      thread5.start();
      thread6.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
