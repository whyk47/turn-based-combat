package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.enemy.FireBreath;
import entity.action.interfaces.Action;

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

        if (actions.ready(ctx).contains(fireBreath)) {
            return fireBreath;
        }
        return actions.ready(ctx).get(0);
    }
}