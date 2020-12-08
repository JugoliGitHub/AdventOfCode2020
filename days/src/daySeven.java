import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import reader.Reader;

public class daySeven {

  private static Map<String, List<Entry<String, Integer>>> bags = new HashMap<>();

  public static void main(String[] args)  {
    Reader.readInput("/resources/inputd7.txt", daySeven.class).forEach(line -> {
      String[] bagsAndBags = line.substring(0, line.length()-1).split("s contain ");
      List<Entry<String, Integer>> list = new ArrayList<>();
      String bag = bagsAndBags[0];
      if(!bagsAndBags[1].startsWith("no other bags")) {
        Arrays.stream(bagsAndBags[1].split(", ")).forEach(innerBag -> {
          Integer num = Integer.parseInt(innerBag.substring(0,1));
          String innerB = innerBag.substring(2);
          System.out.println(innerB + " " + num);
          if (num > 1) {
            innerB = cutLastChar(innerB);
          }
          list.add(new SimpleEntry<>(innerB, num));
        });
      }
      bags.put(bag, list);
    });
    System.out.println((int)bags.keySet().stream().filter(daySeven::canHoldShinyGold).count());
    System.out.println(bags.get("shiny gold bag").stream().map(daySeven::countBags).reduce(Integer::sum));
  }

  private static int countBags(Entry<String, Integer> entry){
    return  entry.getValue() + entry.getValue() * bags.get(entry.getKey()).stream()
        .map(daySeven::countBags)
        .reduce(Integer::sum)
        .orElseGet(() -> 0);
  }

  private static int reduce(int a, int b) {
    return 0;
  }

  private static boolean canHoldShinyGold(String bag) {
    if(bags.get(bag).size() == 0) return false;
    else if(bags.get(bag).stream().map(Entry::getKey).anyMatch(b -> b.equals("shiny gold bag"))) return true;
    else {
      return bags.get(bag).stream().map(Entry::getKey).anyMatch(daySeven::canHoldShinyGold);
    }
  }

  private static String cutLastChar(String str) {
      return str.substring(0, str.length()-1);
    }
}
