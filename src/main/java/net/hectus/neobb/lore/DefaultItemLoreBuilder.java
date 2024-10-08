package net.hectus.neobb.lore;

import com.marcpg.libpg.lang.Translation;
import net.hectus.neobb.turn.default_game.attributes.clazz.*;
import net.hectus.neobb.turn.default_game.attributes.function.*;
import net.hectus.neobb.turn.default_game.block.BlockTurn;
import net.hectus.neobb.turn.default_game.item.ItemTurn;
import net.hectus.neobb.turn.default_game.mob.MobTurn;
import net.hectus.neobb.turn.default_game.structure.StructureTurn;
import net.hectus.neobb.turn.default_game.throwable.ThrowableTurn;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DefaultItemLoreBuilder extends ItemLoreBuilder {
    public @NotNull List<Component> build(Locale l) {
        List<Component> lore = new ArrayList<>();

        lore.add(SEPARATOR);
        lore.add(key(l, "item-lore.cost.key", "₪").append(Translation.component(l, "item-lore.cost.value", turn.cost()).color(net.kyori.adventure.text.format.NamedTextColor.GOLD)));
        lore.add(SEPARATOR);
        lore.add(key(l, "info.usage.usage", "❖").append(Translation.component(l, "info.usage." + usage())));
        lore.add(key(l, "info.function.function", "❖").append(Translation.component(l, "info.function." + function())));
        lore.add(key(l, "info.class.class", "❖").append(Translation.component(l, "info.class." + clazz())));
        lore.add(SEPARATOR);
        lore.add(key(l, "item-lore.description", "❖"));
        lore.addAll(longText(l, "description"));
        if (turn.requiresUsageGuide()) {
            lore.add(SEPARATOR);
            lore.add(key(l, "item-lore.usage", "➽"));
            lore.addAll(longText(l, "usage"));
        }
        if (turn instanceof CounterFunction counter) {
            lore.add(SEPARATOR);
            lore.add(key(l, "item-lore.counters", "🛡"));
            lore.addAll(counter.counters().stream().map(c -> c.line(l)).toList());
        }
        if (turn instanceof BuffFunction buff) {
            lore.add(SEPARATOR);
            lore.add(key(l, "item-lore.buffs", "↑"));
            lore.addAll(buff.buffs().stream().map(b -> b.line(l)).toList());
        }

        return lore;
    }

    private @NotNull String clazz() {
        if (turn instanceof ColdClazz) return "cold";
        if (turn instanceof HotClazz) return "hot";
        if (turn instanceof NatureClazz) return "nature";
        if (turn instanceof NeutralClazz) return "neutral";
        if (turn instanceof RedstoneClazz) return "redstone";
        if (turn instanceof SupernaturalClazz) return "supernatural";
        if (turn instanceof WaterClazz) return "water";
        return "other";
    }

    private @NotNull String function() {
        if (turn instanceof CounterattackFunction) return "counterattack";
        if (turn instanceof CounterbuffFunction) return "counterbuff";
        if (turn instanceof AttackFunction) return "attack";
        if (turn instanceof BuffFunction) return "buff";
        if (turn instanceof CounterFunction) return "counter";
        if (turn instanceof DefenseFunction) return "defense";
        if (turn instanceof EventFunction) return "event";
        return "other";
    }

    private @NotNull String usage() {
        if (turn instanceof BlockTurn) return "block";
        if (turn instanceof ItemTurn) return "item";
        if (turn instanceof MobTurn<?>) return "mob";
        if (turn instanceof StructureTurn) return "structure";
        if (turn instanceof ThrowableTurn) return "throwable";
        return "other";
    }
}
