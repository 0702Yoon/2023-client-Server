package Framework;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public class ManagerSkill {
  public static Vector<PipedOutputStream> makeOutputStreamVector(CommonYoonFilter... filters) {
    Vector<PipedOutputStream> outputStreamVector = new Vector<>();
    for (CommonYoonFilter filter : filters) {
      for (PipedOutputStream pipedOutputStream : filter.getPipedOutputStreamVector())
        outputStreamVector.add(pipedOutputStream);
    }
    return outputStreamVector;
  }

  public static Vector<PipedInputStream> makeInputStreamVector(CommonYoonFilter... filters) {
    Vector<PipedInputStream> inputStreamVector = new Vector<>();
    for (CommonYoonFilter filter : filters) {
      for (PipedInputStream pipedInputStream : filter.getPipedInputStreamVector())
        inputStreamVector.add(pipedInputStream);
    }
    return inputStreamVector;
  }
}
