/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Source;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import Framework.CommonYoonFilterImpl;

public class SourceFilter extends CommonYoonFilterImpl {
  private String sourceFile;

  public SourceFilter(String inputFile) {
    this.sourceFile = inputFile;
  }

  @Override
  public boolean specificComputationForFilter() throws IOException {
    int byte_read;
    BufferedInputStream br = new BufferedInputStream(new FileInputStream(new File(sourceFile)));
    while (true) {
      byte_read = br.read();
      if (byte_read == -1)
        return true;
      pipedOutputStreamsStore.get(0).write(byte_read);
    }
  }

}
