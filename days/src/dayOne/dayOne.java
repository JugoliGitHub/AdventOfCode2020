package dayOne;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class dayOne {

  public static void main(String[] args) {
    long start = System.nanoTime();
    List<Integer> list = readInput();
    int erg1 = iterateOverAll(list);
    long dif1 = System.nanoTime() - start;
    start = System.nanoTime();
    int erg2 = splitLists(list);
    long dif2 = System.nanoTime() - start;
    start = System.nanoTime();
    int erg3 = splitListInThree(list);
    long dif3 = System.nanoTime() - start;
    System.out.println("time 1: " + dif1 + " erg1: " + erg1
        + "\ntime 2: " + dif2 + " erg2: " + erg2
        + "\ntime 3: " + dif3 + " erg3: " + erg3);
  }

  public static List<Integer> readInput() {
    InputStream stream = dayOne.class.getResourceAsStream("/resources/input.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
  }

  public static int iterateOverAll(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      for (int j = i; j < list.size(); j++) {
        if (list.get(i) + list.get(j) == 2020) {
          return list.get(i) * list.get(j);
        }
      }
    }
    return 0;
  }

  public static int splitLists(List<Integer> list) {
    List<Integer> l1 = list.stream().filter(i -> i <= 1010).collect(Collectors.toList());
    List<Integer> l2 = list.stream().filter(i -> i > 1010).collect(Collectors.toList());
    for (Integer value : l1) {
      for (Integer integer : l2) {
        if (value + integer == 2020) {
          return value * integer;
        }
      }
    }
    return 0;
  }

  public static int splitListInThree(List<Integer> list) {
    for (Integer item : list) {
      for (Integer value : list) {
        for (Integer integer : list) {
          if (item + value + integer == 2020) {
            return item * value * integer;
          }
        }

      }
    }
    return 0;
  }

}
