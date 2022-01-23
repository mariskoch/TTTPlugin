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

public class StartCommand implements CommandExecutor {

    private Plugin plugin;
    private static final int START_SECONDS = 5;

    public StartCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ttt.start")) {
                if (args.length == 0) {
                    if (plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
                        if(lobbyState.getCountdown().isRunning() && lobbyState.getCountdown().getSeconds() >= 5) {
                            lobbyState.getCountdown().setSeconds(START_SECONDS);
                            player.sendMessage(Plugin.PREFIX.append(Component.text("The game will start in a few seconds.", NamedTextColor.GREEN)));
                        } else if (lobbyState.getCountdown().isIdling()){
                            player.sendMessage(Plugin.PREFIX.append(Component.text("There are not enough players in the lobby to start the game", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Plugin.PREFIX.append(Component.text("The game is already running.", NamedTextColor.GRAY)));
                        }
                    } else {
                        player.sendMessage(Plugin.PREFIX.append(Component.text("The game is already running.", NamedTextColor.GRAY)));
                    }
                } else {
                    player.sendMessage(Plugin.PREFIX.append(Component.text("Please use /start", NamedTextColor.GRAY)));
                }
            } else {
                player.sendMessage(Plugin.NO_PERMISSION);
            }
        }
        return false;
    }
}
