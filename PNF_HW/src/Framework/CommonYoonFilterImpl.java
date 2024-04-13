/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public abstract class CommonYoonFilterImpl implements CommonYoonFilter {
  // 필터는 내가 짜야하는 데 파이프는 자바 라이브러리에서 제공함.



  protected Vector<PipedInputStream> pipedInputStreamsStore = new Vector<>();
  protected Vector<PipedOutputStream> pipedOutputStreamsStore = new Vector<>();

  public CommonYoonFilterImpl() {
    pipedInputStreamsStore.add(new PipedInputStream());
    pipedOutputStreamsStore.add(new PipedOutputStream());

  }

  public void connectOutputTo(Vector<PipedInputStream> nextVector) throws IOException {
    int i = 0;
    PipedOutputStream newPipedOutputStream = null;
    for (PipedInputStream inputStream : nextVector) {
      try {
        newPipedOutputStream = pipedOutputStreamsStore.get(i);
      } catch (ArrayIndexOutOfBoundsException e) {
        pipedOutputStreamsStore.add(new PipedOutputStream());
        newPipedOutputStream = pipedOutputStreamsStore.get(i);
      }
      newPipedOutputStream.connect(inputStream);
      i++;
    }

  }


  public void connectInputTo(Vector<PipedOutputStream> previousFilter) throws IOException {
    int i = 0;
    PipedInputStream newPipedInputStream = null;
    for (PipedOutputStream outputStream : previousFilter) {
      try {
        newPipedInputStream = pipedInputStreamsStore.get(i);
      } catch (ArrayIndexOutOfBoundsException e) {
        pipedInputStreamsStore.add(new PipedInputStream());
        newPipedInputStream = pipedInputStreamsStore.get(i);
      }
      newPipedInputStream.connect(outputStream);
      i++;

    }
  }

  public Vector<PipedInputStream> getPipedInputStreamVector() {
    return pipedInputStreamsStore;
  }

  public Vector<PipedOutputStream> getPipedOutputStreamVector() {
    return pipedOutputStreamsStore;
  }

  abstract public boolean specificComputationForFilter() throws IOException;

  // Implementation defined in Runnable interface for thread
  public void run() {
    try {
      specificComputationForFilter();
    } catch (IOException e) {
      if (e instanceof EOFException)
        return;
      else
        System.out.println(e);
    } finally {
      closePorts();
    }
  }

  private void closePorts() {
    try {
      for (PipedInputStream inputStream : pipedInputStreamsStore) {
        inputStream.close();
      }
      for (PipedOutputStream outputStream : pipedOutputStreamsStore) {
        outputStream.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
