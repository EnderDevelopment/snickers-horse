package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MaxHorseCommand implements CommandExecutor {

    private final Main plugin;

    public MaxHorseCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by a player.")
                    .color(NamedTextColor.RED));
            return true;
        }

        if (!player.hasPermission("maxhorse.use")) {
            player.sendMessage(Component.text("You do not have permission to use this command.")
                    .color(NamedTextColor.RED));
            return true;
        }

        String mode = args.length > 0 ? args[0].toLowerCase() : "full";

        Location loc = player.getLocation();
        Horse horse = player.getWorld().spawn(loc, Horse.class);
        horse.setTamed(true);
        horse.setOwner(player);
        horse.setAdult();
        horse.setBreed(true);

        applyMaxAttributes(horse);

        switch (mode) {
            case "health" -> {
                horse.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.15d);
                horse.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.4d);
                player.sendMessage(Component.text("You received a horse with MAXIMUM HEALTH.")
                        .color(NamedTextColor.GREEN));
            }
            case "speed" -> {
                horse.getAttribute(Attribute.MAX_HEALTH).setBaseValue(15.0d);
                horse.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.4d);
                player.sendMessage(Component.text("You received a horse with MAXIMUM SPEED.")
                        .color(NamedTextColor.GREEN));
            }
            case "jump" -> {
                horse.getAttribute(Attribute.MAX_HEALTH).setBaseValue(15.0d);
                horse.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.15d);
                player.sendMessage(Component.text("You received a horse with MAXIMUM JUMP HEIGHT.")
                        .color(NamedTextColor.GREEN));
            }
            default -> player.sendMessage(Component.text("You received a horse with MAXIMUM "
                            + "health, speed and jump height.")
                    .color(NamedTextColor.GREEN));
        }

        return true;
    }

    private void applyMaxAttributes(Horse horse) {
        setAttribute(horse, Attribute.MAX_HEALTH, 30.0d);
        setAttribute(horse, Attribute.MOVEMENT_SPEED, 0.3375d);
        setAttribute(horse, Attribute.JUMP_STRENGTH, 1.0d);
    }

    private void setAttribute(Horse horse, Attribute attribute, double value) {
        AttributeInstance instance = horse.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }
}
