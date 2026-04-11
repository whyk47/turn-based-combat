package control;

import java.util.List;

import boundary.GameUI;
import control.mode.GameMode;
import control.strategy.SpeedBasedTurnOrder;
import entity.combatant.player.Player;
import entity.combatant.player.Warrior;
import entity.combatant.player.Wizard;
import entity.item.Item;
import entity.item.Potion;
import entity.level.Level;

public class GameManager {

    private final GameUI ui = new GameUI();

    public void startGame() {
        ui.displayWelcome();

        GameMode mode = null;
        int playerType = -1;
        List<Item> items = null;
        boolean replayWithSame = false;

        while (true) {
            if (!replayWithSame) {
                mode = ui.selectGameMode();

                // TODO: this logic should be in the GameMode class. Implement as getPlayerType() and getItems()
                if (mode.allowWeaponSelection) {
                    playerType = ui.selectPlayerType();
                } else {
                    playerType = 1; // Challenge mode: Warrior
                    ui.displayActionResult("Challenge Mode: Warrior selected as fixed class.");
                }

                if (mode.allowItemSelection) {
                    items = ui.selectItems();
                } else {
                    items = List.of(new Potion(), new Potion());
                    ui.displayActionResult("Challenge Mode: 2x Potion assigned as fixed items.");
                }
            }

            boolean won = runMode(mode, playerType, items);
            ui.displayModeEnd(won, mode);

            // TODO: Use an enum for these options
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



    private boolean runMode(GameMode mode, int playerType, List<Item> items) {
        int levelNumber = 1;

        // TODO: should pass the class instead of an int for the player type
        /*
        public void test(Class<? extends Player> playerClass, List<Item> items) {
            try {
                Player player = playerClass.getDeclaredConstructor(List.class).newInstance(items);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */


        // TODO: iterate through levels of the game mode instead
        /* 
        for (Level level : mode.getLevels()) {
            Player player = createPlayer(playerType, items);
            BattleEngine engine = new BattleEngine(ui, new SpeedBasedTurnOrder(), level, player, levelNumber);

            engine.startBattle();
            mode.onRoundEnd(engine, ui);

            if (mode.isBattleOver(engine)) {
                return player.isAlive();
            }

        }
        
        
        */


        while (true) {
            Level level = mode.getNextLevel(levelNumber);
            if (level == null) return true;

            Player player = createPlayer(playerType, items);
            BattleEngine engine = new BattleEngine(ui, new SpeedBasedTurnOrder(), level, player, levelNumber);

            engine.startBattle();
            mode.onRoundEnd(engine, ui);

            if (mode.isBattleOver(engine)) {
                return player.isAlive();
            }

            levelNumber++;
        }
    }

    // TODO: Remove 
    private Player createPlayer(int type, List<Item> items) {
        if (type == 1) return new Warrior(items);
        else return new Wizard(items);
    }
}