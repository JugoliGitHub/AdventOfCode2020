import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dayFifteen {

  public static void main(String[] args) {
    System.out.println(sayNumbers(Arrays.asList(6, 19, 0, 5, 7, 13, 1), 2020));
    System.out.println(sayNumbers(Arrays.asList(6, 19, 0, 5, 7, 13, 1), 30000000));
  }

  /*private static int part1(List<Integer> startNumbers, int turnOut) {
    ArrayList<Integer> history = new ArrayList<>(startNumbers);

    int lastTurn = history.size();
    while (lastTurn < turnOut) {
      int lastNum = history.get(lastTurn - 1);
      if (history.subList(0, lastTurn - 1).stream().noneMatch(i -> i == lastNum)) {
        history.add(0);
      } else {
        history.add(
            history.lastIndexOf(lastNum) - history.subList(0, lastTurn - 1).lastIndexOf(lastNum));
      }
      // System.out.println("Turn " + lastTurn + ": Last Number was: " + lastNum + " added: " + history.get(history.size()-1));
      lastTurn++;
    }
    return history.get(turnOut - 1);
  } */

  private static int sayNumbers(List<Integer> startNumbers, int turnOut) {
    Map<Integer, Integer> history = new HashMap<>();
    for(int i = 1; i < startNumbers.size(); i++) {
      history.put(startNumbers.get(i-1), i);
    }
    int lastTurn = history.size();
    int currentNum = 0;
    Integer lastNum = startNumbers.get(startNumbers.size() - 1);
    while (lastTurn + 1 < turnOut) {
      if (!history.containsKey(lastNum)) {
        currentNum = 0;
      } else {
        currentNum = lastTurn + 1 - history.get(lastNum);
      }
      // System.out.println("Turn " + lastTurn + ": Last Number was: " + lastNum + " added: " + history.get(history.size()-1));
      history.put(lastNum, lastTurn+1);
      lastNum = currentNum;
      lastTurn++;

    }
    return lastNum;
  }

}
