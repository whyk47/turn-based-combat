package entity.action.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.SingleTargetAttack;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.interfaces.SmokeBombable;
import java.util.List;

public class FireBreath implements SingleTargetAttack {

    @Override
    public void executeOn(Combatant target, ActionContext ctx) {
        if (target instanceof SmokeBombable && ((SmokeBombable) target).isSmokeBombActive()) {
            ctx.ui.displayActionResult(ctx.actor.getName() +
                    " uses Fire Breath -- 0 damage! (Smoke Bomb)");
            return;
        }

        int damage = Math.max(
                0,
                ctx.actor.stats().get(StatField.attack) + 10
                        - target.stats().get(StatField.defense)
        );

        target.takeDamage(damage);

        ctx.ui.displayActionResult(
                ctx.actor.getName() + " uses Fire Breath on " +
                target.getName() + " for " + damage + " dmg! HP: " +
                target.getHp() + "/" + target.stats().get(StatField.maxHp)
        );

        if (!target.isAlive()) {
            ctx.ui.displayActionResult(target.getName() + " is ELIMINATED!");
        }
    }

    @Override
    public List<Combatant> selectTargets(ActionContext ctx) {
        return ctx.getLivingOpponents();
    }

    @Override
    public Combatant selectTarget(ActionContext ctx) {
        return ctx.getLivingOpponents().get(0);
    }

    @Override
    public String getLabel() {
        return "Fire Breath";
    }
}