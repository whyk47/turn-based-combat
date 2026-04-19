package control;

import java.util.Iterator;
import java.util.List;

import boundary.UserInterface;
import control.mode.GameMode;
import entity.combatant.player.Player;
import entity.combatant.player.PlayerBuilder;
import entity.equipment.Equipment;
import entity.item.Item;
import entity.level.Level;

public class GameManager {

    private final UserInterface ui;

    public GameManager(UserInterface ui) {
        this.ui = ui;
    }

    public void startGame() {
        ui.displayWelcome();

        GameMode mode = null;
        Class<? extends Player> playerClass = null;
        List<Class<? extends Item>> itemClasses = null;
        Class<? extends Equipment> weaponClass = null;
        Class<? extends Equipment> artifactClass = null;
        boolean replayWithSame = false;

        while (true) {
            if (!replayWithSame) {
                mode = ui.selectGameMode();
                playerClass = mode.getPlayerSelection(ui);
                itemClasses = mode.getItemSelection(ui);
                weaponClass = mode.getWeaponSelection(ui);
                artifactClass = mode.getArtifactSelection(ui);
            }

            boolean won = runMode(mode, playerClass, itemClasses, weaponClass, artifactClass);
            ui.displayModeEnd(won, mode);

            int choice = ui.askReplay();
            if (choice == 1) {
                replayWithSame = true;
            } else if (choice == 2) {
                replayWithSame = false;
            } else {
                System.out.println("Thanks for playing! Goodbye.");
                break;
            }
        }
    }

    private boolean runMode(GameMode mode, Class<? extends Player> playerClass, 
                            List<Class<? extends Item>> itemClasses,
                            Class<? extends Equipment> weaponClass, 
                            Class<? extends Equipment> artifactClass) {
        
        Iterator<Level> levels = mode.iterator();
        int levelNumber = 1;

        while (levels.hasNext()) {
            Level level = levels.next();

            Player player = new PlayerBuilder()
                .createPlayer(playerClass)
                .addItems(itemClasses)
                .addEquipment(weaponClass)
                .addEquipment(artifactClass)
                .build();
            
            BattleEngine engine = new BattleEngine(ui, mode.turnStrategy, level, player, levelNumber, mode);

            engine.startBattle();
            mode.onRoundEnd(engine, ui);

            if (mode.isBattleOver(engine)) {
                return mode.hasPlayerWon(engine);
            }
            levelNumber++;
        }
        return true;
    }
}
