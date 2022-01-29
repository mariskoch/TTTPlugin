package sneezn.plugin.voting;

import sneezn.plugin.Plugin;

import java.util.ArrayList;

public class Voting {

    private Plugin plugin;
    private ArrayList<Map> maps;

    public Voting(Plugin plugin, ArrayList<Map> maps) {
        this.plugin = plugin;
        this.maps = maps;
    }

}
