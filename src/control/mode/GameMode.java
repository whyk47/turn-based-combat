package control.mode;

import java.util.Iterator;
import java.util.List;

import boundary.UserInterface;
import control.BattleEngine;
import control.strategy.SpeedBasedTurnOrder;
import control.strategy.TurnOrderStrategy;
import entity.combatant.player.Player;
import entity.combatant.player.PlayerRegistry;
import entity.equipment.Equipment;
import entity.equipment.artifact.ArtifactRegistry;
import entity.equipment.weapon.WeaponRegistry;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.item.ItemRegistry;
import entity.level.Level;

public abstract class GameMode implements Iterable<Level>, Named, Describable {
    protected final LevelGenerator levelGenerator;
    public final TurnOrderStrategy turnStrategy = new SpeedBasedTurnOrder();

    protected GameMode(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
    }

    @Override
    public String getName() { return getClass().getSimpleName().replaceAll("(?<=[a-z])(?=[A-Z])", " "); }
    public abstract String getDescription();

    @Override
    public Iterator<Level> iterator() {
        return levelGenerator.iterator();
    }

    public Class<? extends Player> getPlayerSelection(UserInterface ui) {
        return ui.selectFromRegistry(PlayerRegistry.getInstance(), "SELECT YOUR PLAYER");
    }

    public List<Class<? extends Item>> getItemSelection(UserInterface ui) {
        return ui.selectMultipleFromRegistry(ItemRegistry.getInstance(), "SELECT 2 ITEMS", 2);
    }

    public Class<? extends Equipment> getWeaponSelection(UserInterface ui) {
        return ui.selectFromRegistry(WeaponRegistry.getInstance(), "SELECT 1 WEAPON");
    }

    public Class<? extends Equipment> getArtifactSelection(UserInterface ui) {
        return ui.selectFromRegistry(ArtifactRegistry.getInstance(), "SELECT 1 ARTIFACT");
    }

    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    public boolean hasPlayerWon(BattleEngine engine) {
        return engine.getPlayer().isAlive() && engine.isBattleOver();
    }

    public abstract void onRoundEnd(BattleEngine engine, UserInterface ui);
}
