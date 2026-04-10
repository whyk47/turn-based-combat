package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class DurationEffect extends StatusEffect {
    protected int duration;

    public DurationEffect(String name, int duration, boolean begin) {
        super(name, begin);
        this.duration = duration;
    }

    public int getDuration() { return duration; }
    public boolean isExpired() { return duration <= 0; }

    public void remove(Combatant target, GameUI ui) {
        ui.displayActionResult(name + " effect on " + target.getName() + " has expired.");
    }

    @Override
    public void tick(Combatant target, GameUI ui) {
        duration--;
        if (isExpired()) remove(target, ui);
    }

    @Override
    public String toString() {
        return "[" + name + " " + duration + "]";
    }
}