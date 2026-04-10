package entity.combatant;

public enum StatField {
    maxHp,
    attack,
    defense,
    speed;

    @Override
    public String toString() {
        return this.name(); // already returns "maxHp", "attack", etc.
    }
}
