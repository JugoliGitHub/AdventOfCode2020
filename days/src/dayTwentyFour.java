import java.util.ArrayList;
import java.util.List;
import reader.Reader;

public class dayTwentyFour {

  public static void main(String[] args) {
    List<Coordinate> blackTiles = new ArrayList<>();
    Reader.readInput("/resources/inputd24.txt", dayTwentyFour.class).forEach(line -> {
      int n = 0;
      float e = 0;
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == 's') {
          i++;
          n--;
          if (line.charAt(i) == 'e') {
            e += 0.5;
          } else {
            e -= 0.5;
          }
        } else if (line.charAt(i) == 'n') {
          i++;
          n++;
          if (line.charAt(i) == 'e') {
            e += 0.5;
          } else {
            e -= 0.5;
          }
        } else {
          e += line.charAt(i) == 'e' ? 1 : -1;
        }
      }
      Coordinate c = new Coordinate(n, e);
      if (blackTiles.contains(c)) {
        blackTiles.remove(c);
      } else {
        blackTiles.add(c);
      }
    });
    System.out.println(blackTiles.size());

    List<Coordinate> newBlackTiles = new ArrayList<>();
    List<Coordinate> allTiles = new ArrayList<>();
    for(int i = 0; i < 100; i++) {
      blackTiles.forEach((thisTile -> {
        if (!allTiles.contains(thisTile))
          allTiles.add(thisTile);
        if (!allTiles.contains(new Coordinate(thisTile.north + 1, thisTile.east + 0.5)))
          allTiles.add(new Coordinate(thisTile.north + 1, thisTile.east + 0.5));
        if (!allTiles.contains(new Coordinate(thisTile.north - 1, thisTile.east + 0.5)))
          allTiles.add(new Coordinate(thisTile.north - 1, thisTile.east + 0.5));
        if (!allTiles.contains(new Coordinate(thisTile.north + 1, thisTile.east - 0.5)))
          allTiles.add(new Coordinate(thisTile.north + 1, thisTile.east - 0.5));
        if (!allTiles.contains(new Coordinate(thisTile.north - 1, thisTile.east - 0.5)))
          allTiles.add(new Coordinate(thisTile.north - 1, thisTile.east - 0.5));
        if (!allTiles.contains(new Coordinate(thisTile.north, thisTile.east + 1)))
          allTiles.add(new Coordinate(thisTile.north, thisTile.east + 1));
        if (!allTiles.contains(new Coordinate(thisTile.north, thisTile.east - 1)))
          allTiles.add(new Coordinate(thisTile.north, thisTile.east - 1));
      }));
      allTiles.forEach(thisTile -> {
        int countNeighborTiles = (int) blackTiles.stream().filter(thatTile ->
            (thisTile.east == thatTile.east - 1 && thisTile.north == thatTile.north) ||
                (thisTile.east == thatTile.east + 1 && thisTile.north == thatTile.north) ||
                (thisTile.east == thatTile.east - 0.5 && thisTile.north == thatTile.north + 1) ||
                (thisTile.east == thatTile.east - 0.5 && thisTile.north == thatTile.north -1) ||
                (thisTile.east == thatTile.east + 0.5 && thisTile.north == thatTile.north + 1) ||
                (thisTile.east == thatTile.east + 0.5 && thisTile.north == thatTile.north -1))
            .count();
        if (blackTiles.contains(thisTile) && (countNeighborTiles == 1 || countNeighborTiles == 2)) {
          newBlackTiles.add(thisTile);
        } else if (!blackTiles.contains(thisTile) && countNeighborTiles == 2) {
          newBlackTiles.add(thisTile);
        }
      });
      blackTiles.clear();
      blackTiles.addAll(newBlackTiles);
      newBlackTiles.clear();
      allTiles.clear();
    }
    System.out.println(blackTiles.size());
  }

  static class Coordinate {
    int north;
    double east;

    protected Coordinate(int north, double east) {
      this.north = north;
      this.east = east;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Coordinate)) {
        return false;
      }

      Coordinate that = (Coordinate) o;

      if (north != that.north) {
        return false;
      }
      return Double.compare(that.east, east) == 0;
    }

    @Override
    public int hashCode() {
      return 0;
    }
  }

}
