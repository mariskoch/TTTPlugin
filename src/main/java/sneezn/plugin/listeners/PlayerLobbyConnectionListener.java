package sneezn.plugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sneezn.plugin.Plugin;
import sneezn.plugin.countdowns.LobbyCountdown;
import sneezn.plugin.gamestates.LobbyState;
import sneezn.plugin.util.ConfigLocationUtil;

import static sneezn.plugin.Plugin.PREFIX;

public class PlayerLobbyConnectionListener implements Listener {

    private Plugin plugin;

    public PlayerLobbyConnectionListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)) return;
        Player player = event.getPlayer();
        plugin.getPlayers().add(player);
        event.joinMessage(PREFIX
                .append(player.displayName().color(NamedTextColor.GREEN))
                .append(Component.text(" joined the game", NamedTextColor.GRAY))
                .append(Component.text(" [", NamedTextColor.GRAY))
                .append(Component.text(plugin.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS, NamedTextColor.GOLD))
                .append(Component.text("]", NamedTextColor.GRAY)));

        ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin, "Lobby");
        if (locationUtil.loadLocation() != null){
            player.teleport(locationUtil.loadLocation());
        } else {
            plugin.getLogger().warning("Lobby location has not been set yet");
        }

        LobbyState lobbyState = (LobbyState)plugin.getGameStateManager().getCurrentGameState();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if (plugin.getPlayers().size() >= LobbyState.MIN_PLAYERS) {
            if(!countdown.isRunning()){
                countdown.stopIdle();
                countdown.start();
            }
        }
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)) return;
        Player player = event.getPlayer();
        plugin.getPlayers().remove(player);
        event.quitMessage(PREFIX
                .append(player.displayName().color(NamedTextColor.RED))
                .append(Component.text(" left the game", NamedTextColor.GRAY))
                .append(Component.text(" [", NamedTextColor.GRAY))
                .append(Component.text(plugin.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS, NamedTextColor.GOLD))
                .append(Component.text("]", NamedTextColor.GRAY)));

        LobbyState lobbyState = (LobbyState)plugin.getGameStateManager().getCurrentGameState();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if(plugin.getPlayers().size() < LobbyState.MIN_PLAYERS){
            if(countdown.isRunning()){
                countdown.stop();
                countdown.startIdle();
            }
        }
    }

}
