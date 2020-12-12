import static java.lang.StrictMath.abs;

import reader.Reader;

public class dayTwelve {

  public static void main(String[] args) {
    Ship ship = new Ship(90);
    ShipWithWaypoint ship2 = new ShipWithWaypoint(1, 10);
    Reader.readInput("/resources/inputd12.txt", dayTwelve.class)
        .forEach(ship2::moveShip);
    System.out.println(ship2.getManhattanDistance());
  }

  private static class ShipWithWaypoint extends Ship {

    private int waypointPositionNorth, waypointPositionEast;

    public ShipWithWaypoint(int waypointNorth, int waypointEast) {
      super(0);
      waypointPositionEast = waypointEast;
      waypointPositionNorth = waypointNorth;
    }

    @Override
    protected void moveNorth(int value) {
      waypointPositionNorth += value;
    }

    @Override
    protected void moveEast(int value) {
      waypointPositionEast += value;
    }

    @Override
    protected void moveRight(int degrees) {
      if (degrees % 360 == 90) {
        int tmpNorth = waypointPositionNorth;
        waypointPositionNorth = -1 * waypointPositionEast;
        waypointPositionEast = tmpNorth;
      } else if (degrees % 360 == 180) {
        waypointPositionNorth *= -1;
        waypointPositionEast *= -1;
      } else if (degrees % 360 == 270) {
        int tmpNorth = waypointPositionNorth;
        waypointPositionNorth = waypointPositionEast;
        waypointPositionEast = -1 * tmpNorth;
      }
    }

    @Override
    protected void moveForward(int value) {
        super.moveNorth(value * waypointPositionNorth);
        super.moveEast(value * waypointPositionEast);
      }
    }

  private static class Ship {

    protected int eastPosition, northPosition, direction;

    public Ship(int direction) {
      this.eastPosition = 0;
      this.northPosition = 0;
      // 0:N 90:O 180:S 270:W
      this.direction = direction;
    }

    public int getManhattanDistance() {
      return abs(eastPosition)+abs(northPosition);
    }

    public void moveShip(String command) {
      switch (command.charAt(0)) {
        case 'N' -> moveNorth(Integer.parseInt(command.substring(1)));
        case 'S' -> moveNorth(-1 * Integer.parseInt(command.substring(1)));
        case 'E' -> moveEast(Integer.parseInt(command.substring(1)));
        case 'W' -> moveEast(-1 * Integer.parseInt(command.substring(1)));
        case 'L' -> moveRight(360 - Integer.parseInt(command.substring(1)));
        case 'R' -> moveRight(Integer.parseInt(command.substring(1)));
        case 'F' -> moveForward(Integer.parseInt(command.substring(1)));
        default -> System.out.println("Not a command: " + command);
      }
    }

    protected void moveNorth(int value) {
        northPosition += value;
    }

    protected void moveEast(int value) {
      eastPosition += value;
    }

    protected void moveRight(int degrees) {
      if (degrees >= 0)
        direction = (direction + degrees) % 360;
    }

    protected void moveForward(int value) {
      if (value >= 0) {
        if (direction == 0) moveNorth(value);
        if (direction == 90) moveEast(value);
        if (direction == 180) moveNorth(-1 * value);
        if (direction == 270) moveEast(-1 * value);
      }
    }
  }
}
