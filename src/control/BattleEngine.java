package control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boundary.UserInterface;
import control.strategy.TurnOrderStrategy;
import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.enemy.Enemy;
import entity.combatant.player.Player;
import entity.level.Level;

import control.mode.GameMode;

public class BattleEngine {

    private final UserInterface ui;
    private final TurnOrderStrategy turnStrategy;
    private final Level level;
    private final Player player;
    private final GameMode mode;
    private final List<Combatant> allCombatants = new ArrayList<>();
    private int currentRound = 0;
    private int levelNumber = 1;
    private int enemiesKilled = 0;

    public BattleEngine(UserInterface ui, TurnOrderStrategy turnStrategy, Level level, Player player, GameMode mode) {
        this(ui, turnStrategy, level, player, 1, mode);
    }

    public BattleEngine(UserInterface ui, TurnOrderStrategy turnStrategy, Level level, Player player, int levelNumber, GameMode mode) {
        this.ui = ui;
        this.turnStrategy = turnStrategy;
        this.level = level;
        this.player = player;
        this.levelNumber = levelNumber;
        this.mode = mode;
        allCombatants.add(player);
        allCombatants.addAll(level.getInitialEnemies());
    }

    public boolean startBattle() {
        while (true) {
            if (isBattleOver() || mode.isBattleOver(this)) {
                return player.isAlive();
            }

            currentRound++;

            if (getLivingEnemies().isEmpty() && level.isNextWaveAvailable()) {
                List<Enemy> backup = level.getNextWave();
                allCombatants.addAll(backup);
                ui.displayActionResult("--- NEXT WAVE SPAWN! " + backup.stream()
                        .map(Enemy::getName).collect(Collectors.joining(", ")) + " enter the arena! ---");
            }

            ui.displayRoundStart(currentRound, allCombatants);

            List<Combatant> turnOrder = turnStrategy.determineTurnOrder(
                    allCombatants.stream().filter(Combatant::isAlive).collect(Collectors.toList()));

            for (Combatant combatant : turnOrder) {
                if (!combatant.isAlive()) continue;
                takeTurn(combatant);
                if (isBattleOver() || mode.isBattleOver(this)) {
                    return player.isAlive();
                }
            }
        }
    }

    public void takeTurn(Combatant combatant) {
        ActionContext ctx = new ActionContext(combatant, allCombatants, ui);
        int aliveBefore = getLivingEnemies().size();
        combatant.takeTurn(ctx);
        int aliveAfter = getLivingEnemies().size();
        enemiesKilled += (aliveBefore - aliveAfter);
    }

    public boolean isBattleOver() {
        if (!player.isAlive()) return true;
        if (getLivingEnemies().isEmpty() && !level.isNextWaveAvailable()) return true;
        return false;
    }

    private List<Combatant> getLivingEnemies() {
        return allCombatants.stream()
            .filter(c -> c.isAlive() && c.getTeam() == ActionContext.Team.ENEMY)
            .collect(Collectors.toList());
    }

    public int getRound()        { return currentRound; }
    public int getLevelNumber()  { return levelNumber;  }
    public int getEnemiesKilled(){ return enemiesKilled; }
    public Player getPlayer()    { return player; }
}
