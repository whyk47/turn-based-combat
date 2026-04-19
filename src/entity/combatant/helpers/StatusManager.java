package entity.combatant.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import boundary.UserInterface;
import boundary.output.colours.ColourPalette;
import entity.action.ActionContext;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.NonStackableEffect;
import entity.effect.base.StatusEffect;

/**
 * Manages all status effects for a single Combatant.
 * Uses a HashMap for both stackable and non-stackable effects.
 * - Non-stackable: one entry per effect type (highest duration wins)
 * - Stackable: List<StatusEffect> per type, allowing duplicates
 */
public class StatusManager {

    // Non-stackable: one effect per type
    private final Map<Class<? extends StatusEffect>, StatusEffect> nonStackable = new HashMap<>();

    // Stackable: multiple effects of the same type, keyed by type
    private final Map<Class<? extends StatusEffect>, List<StatusEffect>> stackable = new HashMap<>();

    private final Combatant owner;

    public StatusManager(Combatant owner) {
        this.owner = owner;
    }

    public String getMostSevereColour(ColourPalette palette) {
        List<StatusEffect> effects = active();
        if (effects.isEmpty()) return palette.neutral();
        
        // Priority: warning (like stun) > success (buffs) > neutral
        String best = palette.neutral();
        for (StatusEffect e : effects) {
            String c = e.getColour(palette);
            if (c.equals(palette.warning())) return c; // Highest priority
            if (c.equals(palette.success())) best = c;
        }
        return best;
    }


    public void add(StatusEffect effect, UserInterface ui) {
        if (effect instanceof NonStackableEffect) {
            NonStackableEffect e = (NonStackableEffect) effect;
            // Since effects are hashed by class, can cast exsiting to non-stackable
            NonStackableEffect existing = (NonStackableEffect) nonStackable.get(effect.getClass());
            if (existing != null) { ((StatusEffect) existing).remove(owner, ui); }
            // Type cast is safe as all non-stackable effects extend status effect
            StatusEffect combined = (StatusEffect) e.combine(existing);
            nonStackable.put(effect.getClass(), combined);
            combined.apply(owner, ui);
        } else {
            stackable.computeIfAbsent(effect.getClass(), k -> new ArrayList<>()).add(effect);
            effect.apply(owner, ui);
        }
    }


    /**
     * Returns all active effects of a given type.
     * - Non-stackable: returns a single-element list, or empty if not present.
     * - Stackable: returns all stacked instances of that type.
     */
    public List<StatusEffect> get(Class<? extends StatusEffect> type) {
        if (nonStackable.containsKey(type)) {
            List<StatusEffect> result = new ArrayList<>();
            result.add(nonStackable.get(type));
            return result;
        }
        return stackable.getOrDefault(type, new ArrayList<>());
    }


    /**
     * Returns all active effects across both maps as a flat list.
     */
    public List<StatusEffect> all() {
        List<StatusEffect> all = new ArrayList<>(nonStackable.values());
        stackable.values().forEach(all::addAll);
        return all;
    }
    
    public void removeExpired() {
        // Clean up non-stackable                                                  
        nonStackable.values().removeIf(StatusEffect::isExpired);                   
        // Clean up stackable                                                    
        stackable.values().forEach(list -> list.removeIf(StatusEffect::isExpired));
        stackable.entrySet().removeIf(entry -> entry.getValue().isEmpty());        
    }

    public List<StatusEffect> active() {        
        removeExpired();                                
        return all();                                                              
    }  

    public boolean trigger(CombatEvent event, ActionContext ctx) {
        boolean proceed = true;
        for (StatusEffect e : active()) {
            proceed = proceed && e.trigger(event, ctx);
        }
        removeExpired();
        return proceed;
    }

    /**
     * Returns true if any effect of the given type is currently active.
     */
    public boolean contains(Class<? extends StatusEffect> type) {
        return nonStackable.containsKey(type) ||
            stackable.getOrDefault(type, new ArrayList<>()).stream().anyMatch(e -> !e.isExpired());
    }

    @Override
    public String toString() {
        List<StatusEffect> all = all();
        if (all.isEmpty()) return "";
        return all.stream()
                .map(StatusEffect::toString)
                .collect(Collectors.joining(" "));
    }
}