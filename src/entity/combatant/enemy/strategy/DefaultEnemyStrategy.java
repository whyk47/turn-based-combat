package entity.combatant.enemy.strategy;

import entity.action.ActionContext;
import entity.action.interfaces.Action;

public class DefaultEnemyStrategy implements EnemyBehaviourStrategy {

    @Override
    public Action chooseAction(ActionContext ctx) {
        return ctx.actor.getActions().ready(ctx).get(0);
    }
}