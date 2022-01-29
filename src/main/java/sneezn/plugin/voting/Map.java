package sneezn.plugin.voting;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import sneezn.plugin.Plugin;
import sneezn.plugin.gamestates.LobbyState;
import sneezn.plugin.util.ConfigLocationUtil;

public class Map {

    private Plugin plugin;
    private String name;
    private String builder;
    private Location[] spawnLocations = new Location[LobbyState.MAX_PLAYERS];
    private Location spectatorLocation;

    public Map(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name.toUpperCase();
    }

    public void create(String builder) {
        this.builder = builder;
        plugin.getConfig().set("Arenas." + name + ".Builder", builder);
        plugin.saveConfig();
    }

    public boolean exists() {
        return (plugin.getConfig().getString("Arenas." + name + ".Builder") != null);
    }

    public boolean playable() {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("Arenas." + name);
        if (!configurationSection.contains("Spectator")) return false;
        if (!configurationSection.contains("Builder")) return false;
        for(int i = 1; i <= LobbyState.MAX_PLAYERS; i++){
            if(!configurationSection.contains(Integer.toString(i))) return false;
        }
        return true;
    }

    public void setSpawnLocation(int spawnNumber, Location location) {
        spawnLocations[spawnNumber - 1] = location;
        new ConfigLocationUtil(plugin, location, "Arenas." + name + "." + spawnNumber).saveLocation();
    }

    public void setSpectatorLocation(Location location) {
        spectatorLocation = location;
        new ConfigLocationUtil(plugin, location, "Arenas." + name + ".Spectator").saveLocation();
    }

    public String getName() {
        return name;
    }

    public String getBuilder() {
        return builder;
    }

    public Location[] getSpawnLocations() {
        return spawnLocations;
    }

    public Location getSpectatorLocation() {
        return spectatorLocation;
    }

}
