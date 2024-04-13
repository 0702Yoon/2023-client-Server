/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */
package Framework;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public interface CommonYoonFilter extends Runnable {
  public void connectOutputTo(Vector<PipedInputStream> nextVector) throws IOException;

  public void connectInputTo(Vector<PipedOutputStream> previousFilter) throws IOException;

  // 연결을 시키는 메서드
  public Vector<PipedInputStream> getPipedInputStreamVector();

  public Vector<PipedOutputStream> getPipedOutputStreamVector();


}
