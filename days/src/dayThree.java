import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import reader.Reader;

public class dayThree {

  public static void main(String[] args) {
    Counter counter = new Counter();
    int[][] slopesArray = {{1,1}, {3,1}, {5,1}, {7,1}, {1,1}};
    Stream<String> stream = Reader.readInput("/resources/inputD3.txt", dayThree.class);
    System.out.println((int) stream
        .map(line -> line.split(""))
        .filter(line -> line[counter.getAndIncrement(3, line.length)].equals("#")).count());
    Arrays.asList(slopesArray).parallelStream()
        .forEach(slope -> IntStream
            .range(0, (int)Reader.readInput("/resources/inputD3.txt", dayThree.class).count())
            .filter(i -> i % slope[1] == 0)
            .mapToObj(i -> Reader.readInput("/resources/inputD3.txt", dayThree.class).toArray(String[]::new)[i])
            .forEach(streamSlopes -> System.out.println((int) streamSlopes.lines()
                .map(line -> line.split(""))
                .filter(line -> line[counter.getAndIncrement(slope[0], line.length)].equals("#"))
                .count())));
  }
}

class Counter{
  public int count;
  public Counter() {
    count = 0;
  }

  public int getAndIncrement(int value) {
    count += value;
    return count;
  }

  public int getAndIncrement(int value, int mod) {
    int countGet = count;
    count = (count + value) % mod;
    return countGet;
  }
}
