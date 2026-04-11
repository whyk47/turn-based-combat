package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Difficulty;
import entity.level.Level;

public class ChallengeMode extends GameMode {
    public final boolean allowItemSelection = false;
    public final boolean allowWeaponSelection = false;

    @Override
    public String getName() { return "Challenge Mode"; }

    @Override
    public Level getNextLevel(int roundNumber) {
        if (roundNumber == 1) return new Level(Difficulty.HARD);
        return null;
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Challenge complete! You conquered Hard mode with a fixed loadout! ---");
        } else {
            ui.displayActionResult("--- Challenge failed. Adapt your tactics and try again! ---");
        }
    }
}