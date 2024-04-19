package net.hectus.bb.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Parser {
    public static @NotNull Location argumentToLocation(@NotNull String x, @NotNull String y, @NotNull String z, CommandSender sender) {
        if (sender instanceof Player player) {
            return new Location(player.getWorld(),
                    x.equals("~") ? player.getX() : Double.parseDouble(x),
                    y.equals("~") ? player.getY() : Double.parseDouble(y),
                    z.equals("~") ? player.getZ() : Double.parseDouble(z)
            );
        } else {
            return new Location(Bukkit.getWorld("world"), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
        }
    }
}