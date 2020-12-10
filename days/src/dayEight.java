import java.util.List;
import java.util.stream.Collectors;
import reader.Reader;

public class dayEight {

  public static void main(String[] args) {
    List<Command> list = Reader.readInput("/resources/inputd8.txt", dayEight.class)
        .map(Command::new)
        .collect(Collectors.toList());
    int acc = 0;
    int counter = 0;
    System.out.println(list.get(0).getCommand());
    while(!list.get(counter).isExecuted()) {
      Command cmd = list.get(counter);
      if(cmd.getCommand().equals("acc")) { acc += cmd.getValue(); counter++; }
      else if(cmd.getCommand().equals("jmp")) { counter += cmd.getValue(); }
      else if(cmd.getCommand().equals("nop")) { counter++; }
      cmd.switchExecuted();
    }
    System.out.println(acc);
  }
}

class Command {
    private boolean executed;
    private final String command;
    private final int value;

    public Command(String line) {
      command = line.substring(0, 3);
      value = Integer.parseInt(line.substring(4));
      executed = false;
    }

  public int getValue() {
    return value;
  }

  public boolean isExecuted() {
    return executed;
  }

  public String getCommand() {
    return command;
  }

  public void switchExecuted() {
    this.executed = true;
  }
}
