package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class SmokeBombEffect extends DurationEffect implements NonStackableEffect {
    public SmokeBombEffect(int duration) { 
        super("Smoke Bomb", duration, true);
    }

    public void apply(Combatant target, GameUI ui) {}

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NonStackableEffect> T combine(T other) {
        if (!(other instanceof SmokeBombEffect)) return (T) this;
        SmokeBombEffect otherSmoke = (SmokeBombEffect) other;
        return (T) (this.getDuration() >= otherSmoke.getDuration() ? this : otherSmoke);
    }
}