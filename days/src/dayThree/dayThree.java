package dayThree;

import reader.Reader;

public class dayThree {

  public static void main(String[] args) {
    Counter counter = new Counter();
    System.out.println(Reader.readInput("/resources/inputD3.txt")
        .map(line -> line.split(""))
        .filter(line -> line[counter.getAndIncrement(3, line.length)].equals("."))
        .count());

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
    count = getAndIncrement(value) % mod;
    return count;
  }
}
