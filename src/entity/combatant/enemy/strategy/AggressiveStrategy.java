package entity.combatant.enemy.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.enemy.Enemy;
import entity.combatant.player.Player;

public class AggressiveStrategy implements EnemyBehaviourStrategy {

    @Override
    public Action chooseAction(ActionContext ctx) {
        List<Combatant> possibleTargets = ctx.allCombatants.stream()
                .filter(c -> c instanceof Player)
                .filter(Combatant::isAlive)
                .collect(Collectors.toList());

        Combatant weakestTarget = possibleTargets.stream()
                .min(Comparator.comparingInt(Combatant::getHp))
                .orElse(null);

        if (weakestTarget == null) {
            return null;
        }

        ctx.targets = List.of(weakestTarget);
        return new EnemyBasicAttack();
    }
}