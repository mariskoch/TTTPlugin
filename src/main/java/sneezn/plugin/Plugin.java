package sneezn.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sneezn.plugin.commands.SetupCommand;
import sneezn.plugin.commands.StartCommand;
import sneezn.plugin.gamestates.GameState;
import sneezn.plugin.gamestates.GameStateManager;
import sneezn.plugin.listeners.PlayerLobbyConnectionListener;
import sneezn.plugin.voting.Map;
import sneezn.plugin.voting.Voting;

import java.util.ArrayList;

public final class Plugin extends JavaPlugin {

    public static final TextComponent PREFIX = Component
            .text("[", NamedTextColor.GRAY)
            .append(Component.text("TTT", NamedTextColor.RED))
            .append(Component.text("] ", NamedTextColor.GRAY));
    public static final TextComponent NO_PERMISSION = Component
            .text("You do not have the permission to perform this action.", NamedTextColor.RED);

    private GameStateManager gameStateManager;
    private ArrayList<Player> players;
    private Voting voting;

    @Override
    public void onEnable() {
        getLogger().info("TTT started");

        gameStateManager = new GameStateManager(this);
        gameStateManager.setGameState(GameState.LOBBY_STATE);

        players = new ArrayList<Player>();

        init(Bukkit.getPluginManager());
    }

    private void initVoting(){
        ArrayList<Map> maps = new ArrayList<>();
        for(String current : getConfig().getConfigurationSection("Arenas").getKeys(false)) {
            Map map = new Map(this, current);
            if (map.playable()) {
                maps.add(map);
            } else {
                getLogger().warning("The map " + current + " is not fully set up");
            }
        }
        voting = new Voting(this, maps);
    }

    @Override
    public void onDisable() {
        getLogger().info("TTT stopped");
    }

    private void init(PluginManager pluginManager){
        initVoting();

        pluginManager.registerEvents(new PlayerLobbyConnectionListener(this), this);

        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public Voting getVoting() {
        return voting;
    }
}
