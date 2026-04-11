package entity.combatant.enemy.strategy;

import entity.action.ActionContext;
import entity.action.interfaces.Action;

public interface EnemyBehaviourStrategy {
    Action chooseAction(ActionContext ctx);
}
