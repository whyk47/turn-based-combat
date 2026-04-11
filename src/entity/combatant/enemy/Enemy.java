package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.interfaces.Stunnable;
import java.util.List;
import java.util.Random;

public class Enemy extends Combatant implements Stunnable {
    private static final Random RANDOM = new Random();

    public Enemy(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
        actions.add(new EnemyBasicAttack());
    }

    @Override
    public void selectTargets(ActionContext ctx, Action action) {
    if (ctx.getLivingOpponents().isEmpty()) {
        ctx.targets = List.of();
        return;
    }
    ctx.targets = List.of((Combatant) ctx.getLivingOpponents().get(0));
}

    @Override
    public Action chooseAction(ActionContext ctx) {
        if (isStunned()) {
            showStun(ctx.ui);
            return null;
        }

        List<Action> readyActions = actions.ready(ctx);
        if (readyActions.isEmpty()) {
            return null;
        }

        return readyActions.get(RANDOM.nextInt(readyActions.size()));
    }
}