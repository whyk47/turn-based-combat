package entity.action.player.wizard;

import java.util.List;

import entity.action.ActionContext;
import entity.action.interfaces.SpecialAttack;
import entity.action.interfaces.SplashAttack;
import entity.combatant.Combatant;
import entity.effect.ArcaneBlastEffect;

public class ArcaneBlast extends SpecialAttack implements SplashAttack {

    public ArcaneBlast() { super(2); }

    @Override
    public void executeOn(Combatant target, ActionContext ctx) {
        int dmg = getDamage(target, ctx);
        target.takeDamage(dmg);
        displayDamage(target, ctx, dmg, "Arcane Blasts");
    }

    protected int getArcaneBonus(List<Combatant> targets) {
        int kills = 0;
        for (Combatant t : targets) {
            if (!t.isAlive()) { kills++; }
        }
        return kills * 10;
    }

    @Override
    public boolean execute(ActionContext ctx) {
        if (super.execute(ctx)) { 
            int bonus = getArcaneBonus(ctx.targets);
            ctx.actor.getStatus().add(new ArcaneBlastEffect(bonus), ctx.ui);
            return true; 
        }
        return false;
    }

    @Override public String getLabel() { return "Arcane Blast"; }
}
