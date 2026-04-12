package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Difficulty;
import entity.level.Level;

public class StoryMode extends GameMode {

    private static final Difficulty[] STORY_LEVELS = {
            Difficulty.EASY,
            Difficulty.MEDIUM,
            Difficulty.HARD
    };

    @Override
    public String getName() { return "Story Mode"; }

    @Override
    public Level getNextLevel(int roundNumber) {
        int idx = roundNumber - 1;
        if (idx >= STORY_LEVELS.length) return null;
        return new Level(STORY_LEVELS[idx]);
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return !engine.getPlayer().isAlive();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        int level = engine.getLevelNumber();
        if (engine.getPlayer().isAlive()) {
            if (level < STORY_LEVELS.length) {
                ui.displayActionResult("--- Chapter " + level + " cleared! Prepare for the next enemy wave... ---");
            } else {
                ui.displayActionResult("--- All chapters conquered! You are victorious! ---");
            }
        }
    }
}