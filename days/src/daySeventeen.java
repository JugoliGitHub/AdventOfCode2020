import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import reader.Reader;

public class daySeventeen {

  public static void main(String[] args) {
    // int[] = int(){x,y,z}
    Set<Integer[]> active = new HashSet<>();

    // add ons to set
    List<String> ls = Reader.readInput("/resources/inputd17.txt", daySeventeen.class)
        .collect(Collectors.toList());
    for (int y = 0; y < ls.size(); y++) {
      for (int x = 0; x < ls.get(0).length(); x++) {
        if (ls.get(y).charAt(x) == '#') {
          active.add(new Integer[]{x, y, 0, 0});
        }
      }
    }

    //make steps
    for (int c = 0; c < 6; c++) {
      System.out.println(active.size());
      Set<Integer[]> newActive = new HashSet<>();
      for (int x = -15; x < 16; x++) {
        for (int y = -15; y < 16; y++) {
          for (int z = -8; z < 8; z++) {
            for (int w = -8; w < 8; w++) {
              int neighbours = 0;
              for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                  for (int dz = -1; dz <= 1; dz++) {
                    for (int dw = -1; dw <= 1; dw++) {
                      if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                        Integer[] neighbor = new Integer[]{x + dx, y + dy, z + dz, w + dw};
                        if (active.stream().anyMatch(i -> Arrays.equals(i, neighbor))) {
                          neighbours++;
                        }
                      }
                    }
                  }
                }
              }
              Integer[] possibleNew = new Integer[]{x, y, z, w};
              if (active.stream().anyMatch(i -> Arrays.equals(i, possibleNew)) && (neighbours == 2
                  || neighbours == 3)) {
                newActive.add(possibleNew);
              } else if (active.stream().noneMatch(i -> Arrays.equals(i, possibleNew))
                  && neighbours == 3) {
                newActive.add(possibleNew);
              }
            }
          }
        }
      }
      active = newActive;
    }

    System.out.println(active.size());
  }
}
