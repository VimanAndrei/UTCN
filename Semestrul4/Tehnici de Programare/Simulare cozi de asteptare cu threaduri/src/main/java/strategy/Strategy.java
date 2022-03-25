package strategy;

import java.util.List;
import model.*;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}
