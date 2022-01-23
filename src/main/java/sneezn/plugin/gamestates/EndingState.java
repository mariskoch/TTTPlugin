package sneezn.plugin.gamestates;

import sneezn.plugin.Plugin;

public class EndingState extends GameState{

    private Plugin plugin;

    //TODO: Constructor überarbeiten, dass gamestatemanager übergeben wird nicht mehr plugin
    public EndingState(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void start() {
        plugin.getLogger().info("EndingState started");
    }

    @Override
    public void stop() {
        plugin.getLogger().info("EndingState stopped");
    }
}
