package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import java.util.Comparator;
import java.util.List;

public class Wolf extends Enemy {
    private static int count = 0;

    public Wolf() {
        super("Wolf-" + (char)('A' + count++), 40, 45, 5, 35);
    }

    @Override
    public void selectTargets(ActionContext ctx, Action action) {
        List<Combatant> opponents = ctx.getLivingOpponents();
        if (opponents.isEmpty()) {
            ctx.targets = List.of();
            return;
        }

        Combatant target = opponents.stream()
                .min(Comparator.comparingInt(Combatant::getHp))
                .orElse(opponents.get(0));

        ctx.targets = List.of(target);
    }
}