package sneezn.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sneezn.plugin.Plugin;
import sneezn.plugin.gamestates.LobbyState;
import sneezn.plugin.util.ConfigLocationUtil;
import sneezn.plugin.voting.Map;

public class SetupCommand implements CommandExecutor {

    private Plugin plugin;

    public SetupCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ttt.setup")) {
                if (args.length == 0) {
                    player.sendMessage(Plugin.PREFIX
                            .append(Component.text("Bitte nutze /setup <LOBBY>", NamedTextColor.GRAY)));
                } else {
                    if (args[0].equalsIgnoreCase("lobby")) {
                        if (args.length == 1) {
                            new ConfigLocationUtil(plugin, player.getLocation(), "Lobby").saveLocation();
                            player.sendMessage(Plugin.PREFIX.append(Component.text("Lobby was set.", NamedTextColor.GRAY)));
                        } else {
                            player.sendMessage(Plugin.PREFIX
                                    .append(Component.text("Please use /setup lobby", NamedTextColor.GRAY)));
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        if (args.length == 3) {
                            Map map = new Map(plugin, args[1]);
                            if (!map.exists()) {
                                map.create(args[2]);
                                player.sendMessage(Plugin.PREFIX.append(Component.text("The map ", NamedTextColor.GREEN))
                                        .append(Component.text(map.getName(), NamedTextColor.GOLD))
                                        .append(Component.text(" was created.", NamedTextColor.GREEN)));
                            } else {
                                player.sendMessage(Plugin.PREFIX.append(Component.text("This map already exits.", NamedTextColor.RED)));
                            }
                        } else {
                            player.sendMessage(Plugin.PREFIX.append(Component.text("Please use /setup create <NAME> <BUILDER>", NamedTextColor.RED)));
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        if (args.length == 3) {
                            Map map = new Map(plugin, args[1]);
                            if (map.exists()) {
                                try {
                                    int spawnNumber = Integer.parseInt(args[2]);
                                    if (spawnNumber > 0 && spawnNumber <= LobbyState.MAX_PLAYERS) {
                                        map.setSpawnLocation(spawnNumber, player.getLocation());
                                        player.sendMessage(Plugin.PREFIX
                                                .append(Component.text("Spawnlocation Nr. ", NamedTextColor.GRAY))
                                                .append(Component.text(spawnNumber, NamedTextColor.GREEN))
                                                .append(Component.text(" for the map ",NamedTextColor.GRAY))
                                                .append(Component.text(map.getName(),NamedTextColor.GREEN))
                                                .append(Component.text(" was set.", NamedTextColor.GRAY)));
                                    } else {
                                        player.sendMessage(Plugin.PREFIX
                                                .append(Component.text("Please enter a number between 1 and " + LobbyState.MAX_PLAYERS, NamedTextColor.RED)));
                                    }
                                } catch (NumberFormatException e) {
                                    if(args[2].equalsIgnoreCase("spectator")){
                                        map.setSpectatorLocation(player.getLocation());
                                        player.sendMessage(Plugin.PREFIX.append(Component.text("The ", NamedTextColor.GRAY))
                                                .append(Component.text("spectator location", NamedTextColor.GREEN))
                                                .append(Component.text(" for the map ", NamedTextColor.GRAY))
                                                .append(Component.text(map.getName(),NamedTextColor.GREEN))
                                                .append(Component.text(" was set.", NamedTextColor.GRAY)));
                                    } else {
                                        player.sendMessage(Plugin.PREFIX
                                                .append(Component.text("Please use /setup set <NAME> <1-" + LobbyState.MAX_PLAYERS + "//SPECTATOR>")));
                                    }
                                }
                            } else {
                                player.sendMessage(Plugin.PREFIX.append(Component.text("This map does not exist", NamedTextColor.RED)));
                            }
                        } else {
                            player.sendMessage(Plugin.PREFIX
                                    .append(Component.text("Please use /setup set <NAME> <1-" + LobbyState.MAX_PLAYERS + "//SPECTATOR>")));
                        }
                    } else {
                        player.sendMessage(Plugin.PREFIX
                                .append(Component.text("Unknown argument", NamedTextColor.RED)));
                    }
                }
            } else {
                player.sendMessage(Plugin.NO_PERMISSION);
            }
        }

        return false;
    }
}