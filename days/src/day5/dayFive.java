package day5;

import java.util.List;
import java.util.stream.Collectors;
import reader.Reader;

public class dayFive {

  public static void main(String[] args) {

    List<Integer> seats = Reader.readInput("/resources/inputd5.txt", dayFive.class).map(line -> {
      int row = Integer.parseInt(line.substring(0, 7).replace('F', '0').replace('B', '1'), 2);
      int col = Integer.parseInt(line.substring(7, 10).replace('R', '1').replace('L', '0'), 2);
      return row * 8 + col;
    }).sorted().collect(Collectors.toList());

    //part 1
    seats.stream().max(Integer::compare).ifPresent(System.out::println);

    //part 2
    seats.stream().filter(line -> !(seats.contains(line + 1) && seats.contains(line - 1) ||
        line.equals(seats.get(0)) || line.equals(seats.get(seats.size() - 1))))
        .reduce((line1, line2) -> (line1 + line2) / 2).ifPresent(System.out::println);
  }

}
