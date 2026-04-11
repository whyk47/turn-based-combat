package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.interfaces.Stunnable;
import java.util.List;
import java.util.Random;

public class Enemy extends Combatant implements Stunnable {

    public Enemy(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
        actions.add(new EnemyBasicAttack());
    }

    @Override
    public void selectTargets(ActionContext ctx, Action action) {
        for (Combatant c : ctx.allCombatants) {
            if (!(c instanceof Enemy) && c.isAlive()) {
                ctx.targets = List.of(c);
                return;
            }
        }
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

        int index = new Random().nextInt(ready.size());
        Action action = ready.get(index);

        selectTargets(ctx, action);
        return action;
    }
}