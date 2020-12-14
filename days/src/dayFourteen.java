import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import reader.Reader;

public class dayFourteen {

  public static void main(String[] args) {
    Map<Integer, Long> memory = new HashMap<>();
    List<String> list = Reader.readInput("/resources/inputd14.txt", dayFourteen.class)
        .collect(Collectors.toList());
    //part 1
    String mask = "";
    for (String line : list) {
      if (line.startsWith("mask = ")) {
        mask = line.substring(7);
      } else if (line.startsWith("mem[")) {
        String[] array = line.split("[mem]|[=]");
        int address = parseInt(array[3].replace("[", "")
            .replace("]", "").strip());
        String valueBin = Integer.toBinaryString(Integer.parseInt(array[4].strip()));
        valueBin = ("000000000000000000000000000000000000" + valueBin).substring(valueBin.length());
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
          if (mask.charAt(i) != 'X') {
            value.append(mask.charAt(i));
          } else {
            value.append(valueBin.charAt(i));
          }
        }
        long valueDec = Long.parseLong(value.toString(), 2);
        memory.put(address, valueDec);
      }
    }
    memory.values().stream().reduce(Long::sum).ifPresent(System.out::println);

    //part 2
    Map<String, Long> memory2 = new HashMap<>();
    String mask2 = "";
    for (String line : list) {
      if (line.startsWith("mask = ")) {
        mask2 = line.substring(7);
      } else if (line.startsWith("mem[")) {
        String[] array = line.split("[mem]|[=]");
        String address = Integer
            .toBinaryString(Integer.parseInt(array[3].replace("[", "").replace("]", "").strip()));
        Long value = Long.parseLong(array[4].strip());
        address = ("000000000000000000000000000000000000" + address).substring(address.length());
        StringBuilder newAddress = new StringBuilder();
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < mask2.length(); i++) {
          if (mask2.charAt(i) == 'X') {
            idx.add(i);
            newAddress.append('X');
          } else if (mask2.charAt(i) == '1') {
            newAddress.append(1);
          } else if (mask2.charAt(i) == '0') {
            newAddress.append(address.charAt(i));
          }
        }
        String adr = newAddress.toString();
        for (int i = 0; i < Math.pow(2, idx.size()); i++) {
          String combination = Integer.toBinaryString(i);
          while (combination.length() < idx.size()) combination = "0" + combination;
          for (int j = 0; j < idx.size(); j++) {
            adr = adr.substring(0, idx.get(j)) + combination.charAt(j) + adr.substring(idx.get(j) + 1);
          }
          memory2.put(adr, value);
        }
      }
    }
    memory2.values().stream().reduce(Long::sum).ifPresent(System.out::println);
  }
}
