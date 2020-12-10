import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import reader.Reader;

public class dayTen {

  private static Map<Integer, Long> dp = new HashMap<>();
  private static Integer[] ls;

  public static void main(String[] args) {
  Arrays.stream(Reader.readInput("/resources/inputd10.txt", dayTen.class)
        .map(Integer::parseInt)
        .sorted()
        .collect(JoltsCountCollector.collector())).reduce((a,b) -> a*b)
        .ifPresent(System.out::println);
  List<Integer> tmp = Reader.readInput("/resources/inputd10.txt", dayTen.class).map(Integer::parseInt)
      .sorted()
      .collect(Collectors.toList());
      tmp.add(0, 0);
      tmp.add(tmp.size(), tmp.get(tmp.size()-1) + 3);
    ls = tmp.toArray(new Integer[0]);

    System.out.println(findWays(0));
  }

  private static long findWays(int i) {
    if(i == ls.length - 1)
      return 1;
    if(dp.containsKey(i)) return dp.get(i);
    long ans = 0;
    for (int j = i+1; j < ls.length; j++) {
      if(ls[j]-ls[i] <= 3)
        ans += findWays(j);
    }
    dp.put(i, ans);
    return ans;
  }

  private static final class JoltsCountCollector {

    private int[] joltsDifferences = new int[]{0,1};
    private int lastJolt = 0;

    public void accept(Integer nextConnector) {
      if(nextConnector - lastJolt == 1) joltsDifferences[0]++;
      else if (nextConnector - lastJolt == 3)
        joltsDifferences[1]++;
      else {
        System.out.println("Something went wrong.");
      }
      lastJolt = nextConnector;
    }

    public dayTen.JoltsCountCollector combine(dayTen.JoltsCountCollector other) {
      throw new UnsupportedOperationException("Parallel Stream not supported");
    }

    public int[] finish() {
      return joltsDifferences;
    }

    public static Collector<Integer, ?, int[]> collector() {
      return Collector
          .of(dayTen.JoltsCountCollector::new, dayTen.JoltsCountCollector::accept, dayTen.JoltsCountCollector::combine,
              dayTen.JoltsCountCollector::finish);
    }

  }

}
