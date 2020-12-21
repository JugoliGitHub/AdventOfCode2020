import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import reader.Reader;

public class dayNineteen {

  public static Map<Integer, String> rules = new HashMap<>();

  public static void main(String[] args) {
    Reader.readInput("/resources/inputd19.txt", dayNineteen.class)
        .filter(dayNineteen::getRules)
        .sorted().forEach(System.out::println);
  }

  private static boolean getRules(String line) {
    String[] split = line.split(": ");
    if (split[0].matches("([0-9]+)")) {
      String ruleNum = split[0];
      String rule = split[1].replace("\"", "");
      if (rule.matches("([a|b]+)")) {
        System.out.println(rules);
      } else {
        rules.put(Integer.parseInt(ruleNum),
            Arrays.stream(rule.split(" "))
                .map(num -> num.matches("[0-9]+") ?
                    rule.replace((CharSequence) num,
                        (CharSequence) "(" + rules.getOrDefault(Integer.parseInt(num), num)) + ")"
                    : num)
                .reduce((c, d) -> c + " " + d).get());
      }
      return false;
    }

    return true;
  }

  private static String constructRules(String str) {
    return "";
  }
}
