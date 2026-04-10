package entity.effect;

public abstract class PermanentEffect extends StatusEffect {
    public PermanentEffect(String name, boolean begin) { super(name, begin); }

    public boolean isExpired() { return false; }
}
