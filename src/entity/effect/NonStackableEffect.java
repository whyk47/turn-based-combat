package entity.effect;

public interface NonStackableEffect {
    @SuppressWarnings("unchecked")
    default <T extends NonStackableEffect> T combine(T other) {
        return (T) this;
    }
}
