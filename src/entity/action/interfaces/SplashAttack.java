package entity.action.interfaces;

import java.util.List;

import entity.action.ActionContext;
import entity.combatant.Combatant;

public interface SplashAttack extends Attack{
    default List<Combatant> selectTargets(ActionContext ctx) { return ctx.getLivingOpponents(); }
}
