package sneezn.plugin.gamestates;

import sneezn.plugin.Plugin;

public class GameStateManager {

    private Plugin plugin;
    private GameState[] gameStates;
    private GameState currentGameState;

    public GameStateManager(Plugin plugin){
        this.plugin = plugin;
        gameStates = new GameState[3];

        //TODO: restlichen gamestates bearbeiten, dass GamestateManager übergeben wird, nicht plugin
        gameStates[GameState.LOBBY_STATE] = new LobbyState(this);
        gameStates[GameState.INGAME_STATE] = new IngameState(plugin);
        gameStates[GameState.ENDING_STATE] = new EndingState(plugin);
    }

    public void setGameState(int gameStateID){
        if(currentGameState != null){
            currentGameState.stop();
        }
        currentGameState = gameStates[gameStateID];
        currentGameState.start();
    }

    public void stopCurrentGameState(){
        if(currentGameState != null){
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
