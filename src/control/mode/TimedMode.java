package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Difficulty;
import entity.level.Level;

public class TimedMode extends GameMode {

    private static final int ROUND_LIMIT = 10;

    private int enemiesKilled = 0;

    @Override
    public String getName() { return "Timed Mode"; }

    @Override
    public Level getNextLevel(int roundNumber) {
        if (roundNumber == 1) return new Level(Difficulty.MEDIUM);
        return null; // only one level in timed mode
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return !engine.getPlayer().isAlive() || engine.getRound() >= ROUND_LIMIT;
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        int roundsLeft = ROUND_LIMIT - engine.getRound();
        enemiesKilled = engine.getEnemiesKilled();

        if (!engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- You were defeated! Final score: " + enemiesKilled + " enemies killed. ---");
        } else if (engine.getRound() >= ROUND_LIMIT) {
            ui.displayActionResult("--- Time's up! Final score: " + enemiesKilled + " enemies killed. ---");
        } else {
            ui.displayActionResult("--- Round " + engine.getRound() + " | " +
                    roundsLeft + " round(s) remaining | Enemies killed: " + enemiesKilled + " ---");
        }
    }

    public int getScore() { return enemiesKilled; }

}