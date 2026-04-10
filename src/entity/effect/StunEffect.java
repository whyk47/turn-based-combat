package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class StunEffect extends DurationEffect implements NonStackableEffect {
    public StunEffect(int duration) { 
        super("Stun", duration, false);
    }

    public void apply(Combatant target, GameUI ui) { 
        ui.displayActionResult(getName() + " is STUNNED for " + duration + " turns!");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NonStackableEffect> T combine(T other) {
        if (!(other instanceof StunEffect)) return (T) this;
        StunEffect otherStun = (StunEffect) other;
        return (T) (this.getDuration() >= otherStun.getDuration() ? this : otherStun);
    }
}