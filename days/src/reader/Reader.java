package reader;

import dayOne.dayOne;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Reader {

  public static Stream<String> readInput(String path, Class cl) {
    InputStream stream = cl.getResourceAsStream(path);
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader.lines();
  }

}
