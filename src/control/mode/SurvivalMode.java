package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Difficulty;
import entity.level.Level;

public class SurvivalMode extends GameMode {

    @Override
    public String getName() { return "Survival Mode"; }

    @Override
    public Level getNextLevel(int roundNumber) {
        Difficulty diff;
        if (roundNumber <= 3) {
            diff = Difficulty.EASY;
        } else if (roundNumber <= 6) {
            diff = Difficulty.MEDIUM;
        } else {
            diff = Difficulty.HARD;
        }
        return new Level(diff);
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return !engine.getPlayer().isAlive();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Wave " + engine.getLevelNumber() +
                    " survived! Brace yourself — the next wave approaches... ---");
        }
    }

}
