package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.enemy.FireBreath;
import entity.action.interfaces.Action;
import java.util.List;

public class Dragon extends Enemy {

    private final Action fireBreath = new FireBreath();

    public Dragon() {
        super("Dragon", 120, 50, 20, 15);
        actions.add(new EnemyBasicAttack());
        actions.add(fireBreath);
    }

    @Override
    public Action chooseAction(ActionContext ctx) {
        if (isStunned()) {
            showStun(ctx.ui);
            return null;
        }

        List<Action> ready = actions.ready(ctx);
        if (ready.isEmpty()) {
            return null;
        }

        Action action;
        if (ready.contains(fireBreath)) {
            action = fireBreath;
        } else {
            action = ready.get(0);
        }

        selectTargets(ctx, action);
        return action;
    }
}