package entity.combatant.enemy;

import entity.combatant.enemy.strategy.DefaultEnemyStrategy;

public class Goblin extends Enemy {
    private static int count = 0;

    public Goblin() {
        super("Goblin-" + (char)('A' + count++), 55, 35, 15, 25); 
        setStrategy(new DefaultEnemyStrategy());
    }
}