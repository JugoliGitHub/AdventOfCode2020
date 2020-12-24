import java.util.Arrays;

public class dayTwentyThree {

  public static void main(String[] args) {
    String input = "364289715";
    int currentCup = 0;
    for (int i = 0; i < 100; i++) {
      int destination = decrement(Integer.parseInt(String.valueOf(input.charAt(currentCup))));
      char current = input.charAt(currentCup);
      char[] pickUp = new char[]{input.charAt((currentCup + 1) % input.length()),
          input.charAt((currentCup + 2) % input.length()), input.charAt((currentCup + 3) % input.length())};
      for (char c : pickUp) {
        input = input.replace(String.valueOf(c), "");
      }
      while (contains(pickUp, destination)) {
        destination = decrement(destination);
      }
      for (int j = 0; j < input.length(); j++) {
        if(input.charAt(j) == destination + 48) {
          input = input.substring(0, j+1) + pickUp[0] + pickUp[1] + pickUp[2] + input.substring(j+1);
          break;
        }
      }
      System.out.println(input);
      currentCup = (input.indexOf(current)+1) % input.length();
    }
  }

  private static boolean contains(char[] arr, int c) {
    boolean bool = false;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == c + 48) {
        bool = true;
        break;
      }
    }
    return bool;
  }

  private static int decrement(int value) {
    if (value - 1 <= 0) return 9;
    return value - 1;
  }

  private static String makeMove(String[] input) {

    return "";
  }

}
