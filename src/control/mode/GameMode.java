package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Level;

public abstract class GameMode {
    public final boolean allowItemSelection = true;
    public final boolean allowWeaponSelection = true;

    public abstract String getName();
    public abstract Level getNextLevel(int roundNumber);
    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }
    public abstract void onRoundEnd(BattleEngine engine, GameUI ui);
}
