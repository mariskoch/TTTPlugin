package sneezn.plugin.gamestates;

import sneezn.plugin.Plugin;

public class IngameState extends GameState{

    private Plugin plugin;

    //TODO: Constructor ├╝berarbeiten, dass nicht mehr plugin sondern gamestatemanager ├╝bergeben wird
    public IngameState(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void start() {
        plugin.getLogger().info("IngameState started");
    }

    @Override
    public void stop() {
        plugin.getLogger().info("IngameState stopped");
    }
}
