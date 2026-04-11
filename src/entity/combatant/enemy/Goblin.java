package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import java.util.List;

public class Goblin extends Enemy {
    private static int count = 0;

    public Goblin() {
        super("Goblin-" + (char)('A' + count++), 50, 30, 10, 20);
    }

    @Override
    public Action chooseAction(ActionContext ctx) {
        List<Action> readyActions = actions.ready(ctx);
        if (readyActions.isEmpty()) {
            return null;
        }

        for (Action action : readyActions) {
            if (!action.getClass().getSimpleName().equals("EnemyBasicAttack")) {
                return action;
            }
        }

        return super.chooseAction(ctx);
    }
}