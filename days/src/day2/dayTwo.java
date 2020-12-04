package day2;

import reader.Reader;

public class dayTwo {

  // regex 1: [^$char]*($char[^$char]*){$min,$max}
  //regex 2:
  public static void main(String[] args) {
    System.out.println(Reader.readInput("/resources/inputD2.txt", dayTwo.class)
        .map(line -> line.split(" "))
        .filter(line -> line[2]
            .matches(
                "[^" + line[1].replace(":", "") + "]*("
                    + line[1].replace(":", "") + "[^"
                    + line[1].replace(":", "") + "]*){"
                    + line[0].replace("-", ",") + "}"))
        .count());

    /*System.out.println(Reader.readInput("/resources/inputD2.txt")
        .map(line -> line.split(" "))
        .filter(line ->
            Pattern.compile(line[1].replace(":","")).matcher(line[2]).groupCount()));*/
  }


}
