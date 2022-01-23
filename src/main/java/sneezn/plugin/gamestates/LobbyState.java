package sneezn.plugin.gamestates;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import sneezn.plugin.Plugin;
import sneezn.plugin.countdowns.LobbyCountdown;

public class LobbyState extends GameState{

    public static final int MIN_PLAYERS = 1,
                            MAX_PLAYERS = 10;
    private LobbyCountdown countdown;
    private GameStateManager gameStateManager;

    public LobbyState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        countdown = new LobbyCountdown(gameStateManager);
    }

    @Override
    public void start() {
        gameStateManager.getPlugin().getLogger().info("LobbyState started");
        countdown.startIdle();
    }

    @Override
    public void stop() {
        gameStateManager.getPlugin().getLogger().info("LobbyState stopped");
        Bukkit.getServer().sendMessage(Component.text("Wir wären jetzt im IngameState"));
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
