import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import reader.Reader;

public class dayFour {

  public static void main(String[] args) {
    // warum einer zu viel, zu wenig?
    System.out.println(Reader.readInput("/resources/inputD4.txt", dayFour.class)
        .collect(PassportsCollector.collector()).size() + 1);
    System.out.println((int) Reader.readInput("/resources/inputD4.txt", dayFour.class)
        .collect(PassportsCollector.collector()).stream().filter(dayFour::isValidPassport).count()
        - 1);
  }

  public static boolean isValidPassport(String passport) {
    for (String data : passport.split(", ")) {
      if (isValidData(data)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidData(String data) {
    String dataValue = data.substring(4);
    return ((data.startsWith("byr") && !dataValue.matches("(19[2-9][0-9]|200[0-2])"))
        || (data.startsWith("iyr") && !dataValue.matches("(201[0-9]|2020)"))
        || (data.startsWith("eyr") && !dataValue.matches("(202[0-9]|2030)"))
        || (data.startsWith("hgt") &&
        ((data.endsWith("cm") && !data.substring(4, 7).matches("(1[5-8][0-9]|19[0-3])"))
            || (data.endsWith("in") && !data.substring(4, 6).matches("(59|6[0-9]|7[0-6])")))
        || (data.startsWith("hcl") && !dataValue.matches("#([a-f]|[0-9]){6}"))
        || (data.startsWith("ecl") && !dataValue.matches("(amb|blu|brn|gry|grn|hzl|oth)"))
        || (data.startsWith("pid") && !dataValue.matches("[0-9]{9}"))));
  }

  private static final class PassportsCollector {

    private List<String> passports = new ArrayList<>();
    private String passport = "";

    public void accept(String line) {
      passport += " " + line;
      if (line.equals("")) {
        // part 1
        if (passport.contains("byr:") && passport.contains("iyr:") && passport.contains("eyr:")
            && passport.contains("hgt:") && passport.contains("hcl:") && passport.contains("ecl:")
            && passport.contains("pid:")) {
          passports.add(passport.stripLeading().replace(" ", ", "));
        }
        passport = "";
      }
    }

    public PassportsCollector combine(PassportsCollector other) {
      throw new UnsupportedOperationException("Parallel Stream not supported");
    }

    public List<String> finish() {
      return passports;
    }

    public static Collector<String, ?, List<String>> collector() {
      return Collector
          .of(PassportsCollector::new, PassportsCollector::accept, PassportsCollector::combine,
              PassportsCollector::finish);
    }

  }
}
