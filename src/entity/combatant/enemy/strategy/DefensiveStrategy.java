package entity.combatant.enemy.strategy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import java.util.List;

public class DefensiveStrategy implements EnemyBehaviourStrategy {

    @Override
    public Action chooseAction(ActionContext ctx) {
        int currentHp = ctx.actor.getHp();

        if (currentHp < 30) {
            List<Action> ready = ctx.actor.getActions().ready(ctx);

            for (Action action : ready) {
                if (!(action instanceof EnemyBasicAttack)) {
                    return action;
                }
            }
        }

        return new EnemyBasicAttack();
    }
}