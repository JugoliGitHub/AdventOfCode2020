import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import reader.Reader;

public class dayEighteen {

  public static void main(String[] args) {
    Reader.readInput("/resources/inputd18.txt", dayEighteen.class)
        .map(line -> line.replace(" ", "")).map(dayEighteen::interpretLine)
        .reduce(Long::sum).ifPresent(System.out::println);

    Reader.readInput("/resources/inputd18.txt", dayEighteen.class)
        .map(line -> line.replace(" ", ""))
        .map(dayEighteen::setParenthesis)
        .map(dayEighteen::interpretWithEval)
        .reduce(Long::sum)
        .ifPresent(System.out::println);


  }

  private static String setParenthesis(String line) {
    int k = 0;
    int pos = 0;
    int lineLength = line.length();
    for (int i = 0; i < lineLength + k; i++) {
      char c = line.charAt(i);
      if (c == '(') {
        pos = i;

      } else if (c == '+') {
        line =
            !(line.charAt(i - 1) == ')') ? line.substring(0, i - 1) + "(" + line.substring(i - 1)
                : line.substring(0, pos) + "(" + line.substring(pos);
        i++;
        k++;
        if (!(line.charAt(i + 1) == '(')) {
          i++;
          line = line.substring(0, i + 1) + ")" + line.substring(i + 1);
          k--;
        }
      } else if (c == ')') {
        if (k > 0) {
          line = line.substring(0, i - 1) + ")" + line.substring(i);
          k--;
          i++;
        }
      }
      lineLength = line.length();
    }
    return line;
  }

  private static long interpretWithEval(String line) {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");
    try {
      Object result = engine.eval(line);
      return (Long) result;
    } catch (ScriptException ignored) {

    }
    return 0;

  }

  private static long interpretLine(String line) {
    String[] split = line.split("");
    long num1 = 0, num2 = 0;
    boolean symbol = false;
    for (int i = 0; i < split.length; i++) {
      String character = split[i];
      if (num1 == 0) {
        if (character.equals("(")) {
          int firstI = i + 1;
          i += findSecondParentheses(line.substring(i));
          num1 = i < line.length() ? interpretLine(line.substring(firstI, i))
              : interpretLine(line.replace(" ", "").substring(firstI));
        } else {
          num1 = Long.parseLong(character);
        }
      } else if (character.equals("(")) {
        int firstI = i + 1;
        i += findSecondParentheses(line.substring(i));
        num2 = i < line.length() ? interpretLine(line.substring(firstI, i))
            : interpretLine(line.substring(firstI));
      } else if (character.equals("+")) {
        symbol = true;
      } else if (character.equals("*")) {
        symbol = false;
      } else if (!character.equals(")")) {
        num2 = Long.parseLong(character);
      }
      if (num1 > 0 && num2 > 0) {
        num1 = calculate(num1, num2, symbol);
        num2 = 0;
      }
    }
    return num1;
  }

  private static int findSecondParentheses(String substring) {
    int counterInnerParentheses = 0;
    String[] split = substring.split("");
    for (int i = 0; i < split.length; i++) {
      if (split[i].equals(")")) {
        counterInnerParentheses--;
        if (counterInnerParentheses == 0) {
          return i;
        }
      } else if (split[i].equals("(")) {
        counterInnerParentheses++;
      }
    }
    return -1;
  }

  private static int findFirstParentheses(String substring) {
    int counterInnerParentheses = 0;
    String[] split = substring.split("");
    for (int i = split.length - 1; i > 0; i--) {
      if (split[i].equals("(")) {
        counterInnerParentheses--;
        if (counterInnerParentheses == 0) {
          return i;
        }
      } else if (split[i].equals(")")) {
        counterInnerParentheses++;
      }
    }
    return 0;
  }

  private static long calculate(long number1, long number2, boolean add) {
    return add ? number1 + number2 : number1 * number2;
  }
}
