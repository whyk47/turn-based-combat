package entity.action.interfaces;

import entity.action.ActionContext;

public abstract class SpecialAttack implements Attack {
    protected int cooldown;
    protected int specialCooldown;

    public SpecialAttack(int cooldown) { this.cooldown = 0; this.specialCooldown = cooldown; }

    public void resetCooldown() { cooldown = specialCooldown; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public void decrementCooldown() { cooldown--; }
    public boolean isReady(ActionContext ctx) { return cooldown <= 0; }

    @Override
    public boolean execute(ActionContext ctx) {
        if (Attack.super.execute(ctx)) {
            resetCooldown();
            return true;
        }
        return false;
    };
}
