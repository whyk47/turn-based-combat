package entity.combatant.enemy.strategy;

import java.util.List;
import java.util.Random;

import entity.action.ActionContext;
import entity.action.interfaces.Action;

public class RandomStrategy implements EnemyBehaviourStrategy {
    private final Random random = new Random();

    @Override
    public Action chooseAction(ActionContext ctx) {
        List<Action> ready = ctx.actor.getActions().ready(ctx);
        return ready.get(random.nextInt(ready.size()));
    }
}