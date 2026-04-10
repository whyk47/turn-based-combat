package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class StatusEffect {
    protected final String name;
    protected final boolean begin;

    public StatusEffect(String name, boolean begin) {
        this.name = name;
        this.begin = begin;
    }

    public String getName() { return name; }
    public boolean isBegin() { return begin; }
    public void tick(Combatant target, GameUI ui) {}

    public abstract void apply(Combatant target, GameUI ui);
    public abstract void remove(Combatant target, GameUI ui);
    public abstract boolean isExpired();

    public String toString() {
        return "[" + name + "]";
    }
}
