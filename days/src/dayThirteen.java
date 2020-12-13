import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import reader.Reader;

public class dayThirteen {

  public static void main(String[] args) {
    Reader.readInput("/resources/inputd13.txt", dayThirteen.class)
        .reduce(dayThirteen::reduceStrings).ifPresent(System.out::println);
    System.out.println(
        findSolution2(
            Arrays.stream(Reader.readInput("/resources/inputd13.txt", dayThirteen.class)
                .toArray()[1]
                .toString()
                .split(","))
            .collect(Collectors.toList())));
  }

  private static String reduceStrings(String time, String busLines) {
    return Arrays.stream(busLines.split(",[x,]*")) //split with commas and ignore x
        .sorted(Comparator.comparingInt((String line) -> calcTimeDif(time, line)))
        .map((String line) -> calcTimeDif(time, line) * Integer.parseInt(line))
        .findFirst().get().toString();
  }

  private static int calcTimeDif(String time, String line) {
    return (Integer.parseInt(time) + Integer.parseInt(line))
        - (Integer.parseInt(time) % Integer.parseInt(line))
        - Integer.parseInt(time);
  }

  // chinese remainder theorem
  private static long findSolution2(List<String> busTime) {
    long N = busTime.stream().filter(elem -> !elem.equals("x")).map(Long::parseLong).reduce((a,b) -> a*b).get();
    long ans = 0;
    for (int i = 0; i < busTime.size(); i++) {
      if(!busTime.get(i).equals("x")) {
        int b = Integer.parseInt(busTime.get(i));
        int ai = (b-i)%b;
        long ni = N/b;
        // mi * ni mod b = 1 mod b => find mod inverse
        long mi = modInverse(ni, b);
        ans += ai*mi*ni;
      }
    }
    return ans%N;
  }

  // https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
  private static long modInverse(long a, long m) {
    a = a % m;
    for (int x = 1; x < m; x++)
      if ((a * x) % m == 1)
        return x;
    return 1;
  }
}
