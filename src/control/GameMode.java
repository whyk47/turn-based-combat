package control;

import boundary.GameUI;
import entity.level.Level;

public interface GameMode {
    String getName();
    Level getNextLevel(int roundNumber);        // controls enemy scaling
    boolean isBattleOver(BattleEngine engine);  // custom win/loss conditions
    void onRoundEnd(BattleEngine engine, GameUI ui); // e.g. timed mode countdown
    boolean allowItemSelection();
    boolean allowWeaponSelection();
}