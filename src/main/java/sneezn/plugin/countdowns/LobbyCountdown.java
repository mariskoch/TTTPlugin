package sneezn.plugin.countdowns;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import sneezn.plugin.Plugin;
import sneezn.plugin.gamestates.GameState;
import sneezn.plugin.gamestates.GameStateManager;
import sneezn.plugin.gamestates.LobbyState;
import sneezn.plugin.voting.Map;
import sneezn.plugin.voting.Voting;

import java.util.ArrayList;
import java.util.Collections;

public class LobbyCountdown extends Countdown {

    private static final int COUNTDOWN_TIME = 60,
            IDLE_TIME = 15;

    private GameStateManager gameStateManager;
    private int seconds;
    private boolean isRunning;
    private int idleID;
    private boolean isIdling;

    public LobbyCountdown(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        seconds = COUNTDOWN_TIME;
    }

    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 60: case 45: case 30: case 20: case 10: case 5: case 3: case 2:
                        Bukkit.getServer().sendMessage(Plugin.PREFIX
                                .append(Component.text("The game starts in ", NamedTextColor.GRAY))
                                .append(Component.text(seconds, NamedTextColor.GOLD))
                                .append(Component.text(" seconds.", NamedTextColor.GRAY)));
                        if(seconds == 3){
                            Voting voting = gameStateManager.getPlugin().getVoting();
                            Map winningMap;
                            if(voting != null){
                                winningMap = voting.getWinnerMap();
                            } else {
                                ArrayList<Map> maps = gameStateManager.getPlugin().getMaps();
                                Collections.shuffle(maps);
                                winningMap = maps.get(0);
                            }
                            Bukkit.getServer().sendMessage(Plugin.PREFIX
                                    .append(Component.text("Tha map ", NamedTextColor.GREEN))
                                    .append(Component.text(winningMap.getName(), NamedTextColor.GOLD))
                                    .append(Component.text(" will be played", NamedTextColor.GREEN)));
                        }
                        break;
                    case 1:
                        Bukkit.getServer().sendMessage(Plugin.PREFIX
                                .append(Component.text("The game starts in ", NamedTextColor.GRAY))
                                .append(Component.text(seconds, NamedTextColor.GOLD))
                                .append(Component.text(" second.", NamedTextColor.GRAY)));
                        break;
                    case 0:
                        gameStateManager.setGameState(GameState.INGAME_STATE);
                        break;
                    default:
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        if(isRunning){
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = COUNTDOWN_TIME;
        }
    }

    public void startIdle() {
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().sendMessage(Plugin.PREFIX
                        .append(Component.text("There are ", NamedTextColor.GRAY))
                        .append(Component.text(LobbyState.MIN_PLAYERS - gameStateManager.getPlugin().getPlayers().size(), NamedTextColor.RED))
                        .append(Component.text(" players missing until the game will start.", NamedTextColor.GRAY)));
            }
        }, 0, 20 * IDLE_TIME);
    }

    public void stopIdle() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(idleID);
            isIdling = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isIdling() {
        return isIdling;
    }
}
