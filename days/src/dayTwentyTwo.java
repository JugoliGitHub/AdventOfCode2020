import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import reader.Reader;

public class dayTwentyTwo {

  public static void main(String[] args) {
    LinkedList<Integer> queueP1 = new LinkedList<>();
    LinkedList<Integer> queueP2 = new LinkedList<>();
    Bool bool = new Bool();
    Reader.readInput("/resources/inputd22.txt", dayTwentyTwo.class).forEach(line -> {
      if (line.startsWith("Player 2:")) {
        bool.switchBool();
        return;
      } else if (line.startsWith("Player 1:") || line.equals("\n") || line.equals("")) {
        return;
      }
      if (bool.bool) {
        queueP2.offer(Integer.parseInt(line));
      } else {
        queueP1.offer(Integer.parseInt(line));
      }
    });
    //part 1
    //System.out.println(playCombat(queueP1, queueP2));

    //part 2
    System.out.println(calcPoints(playCombatRecursive(queueP1, queueP2, new HashSet<>()) ? queueP1 : queueP2));
  }

  private static int playCombat(Queue<Integer> player1, Queue<Integer> player2) {
    if (player1.size() > 0 && player2.size() > 0) {
      int p1 = player1.poll();
      int p2 = player2.poll();
      if (p1 > p2) {
        player1.offer(p1);
        player1.offer(p2);
      } else {
        player2.offer(p2);
        player2.offer(p1);
      }
      return playCombat(player1, player2);
    }
    return calcPoints(player1.size() == 0 ? player2 : player1);
  }

  private static boolean playCombatRecursive(LinkedList<Integer> player1,
      LinkedList<Integer> player2, Set<Configuration> previousConfigurations) {
    Configuration currentConfiguration = new Configuration(player1, player2);
    if (previousConfigurations.stream().anyMatch(currentConfiguration::equals)) {
      System.out.println(currentConfiguration.conf);
      return true;
    }
    previousConfigurations.add(currentConfiguration);
    int p1 = player1.poll();
    int p2 = player2.poll();
    boolean winnerP1 = p1 > p2;
    if (player1.size() >= p1 && player2.size() >= p2) {
      LinkedList<Integer> qP1TMP = (LinkedList<Integer>) player1.clone();
      LinkedList<Integer> qP1 = new LinkedList<>();
      while (qP1.size() < p1) {
        qP1.offer(qP1TMP.poll());
      }
      LinkedList<Integer> qP2TMP = (LinkedList<Integer>) player2.clone();
      LinkedList<Integer> qP2 = new LinkedList<>();
      while (qP2.size() < p2) {
        qP2.offer(qP2TMP.poll());
      }
      winnerP1 = playCombatRecursive(qP1, qP2, new HashSet<Configuration>());
    }
    if (winnerP1) {
      player1.offer(p1);
      player1.offer(p2);
    } else {
      player2.offer(p2);
      player2.offer(p1);
    }
    if (player1.size() == 0 || player2.size() == 0) {
      return player1.size() > 0;
    }
    return playCombatRecursive(player1, player2, previousConfigurations);
  }

  private static int calcPoints(Queue<Integer> winner) {
    if (winner.size() == 0) {
      return 0;
    }
    System.out
        .println((winner.size()) + " " + winner.peek() + " " + (winner.size()) * winner.peek());
    return winner.size() * winner.poll() + calcPoints(winner);
  }

  static class Configuration {

    private String conf;

    public Configuration(Queue<Integer> p1, Queue<Integer> p2) {
      StringBuilder stringBuilder = new StringBuilder();
      p1.forEach(stringBuilder::append);
      stringBuilder.append(",");
      p2.forEach(stringBuilder::append);
      conf = stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Configuration)) {
        return false;
      }
      Configuration that = (Configuration) o;
      return conf.equals(that.conf);
    }

    @Override
    public int hashCode() {
      return Objects.hash(conf);
    }
  }

  static class Bool {

    boolean bool = false;

    public void switchBool() {
      bool = true;
    }

    public boolean getBool() {
      return bool;
    }

  }
}
