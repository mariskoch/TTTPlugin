package sneezn.plugin.voting;

import sneezn.plugin.Plugin;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;

public class Voting {

    private Plugin plugin;
    private ArrayList<Map> maps;
    private Map[] votingMaps;
    public static final int MAP_AMOUNT = 2;

    public Voting(Plugin plugin, ArrayList<Map> maps) {
        this.plugin = plugin;
        this.maps = maps;
        votingMaps = new Map[MAP_AMOUNT];

        chooseRandomMaps();
    }

    private void chooseRandomMaps(){
        for(int i = 0; i < votingMaps.length; i++){
            Collections.shuffle(maps);
            votingMaps[i] = maps.remove(0);
        }
    }

    public Map getWinnerMap(){
        Map winnerMap = votingMaps[0];
        for(int i = 1; i < votingMaps.length; i++){
            if(votingMaps[i].getVotes() >= winnerMap.getVotes()){
                winnerMap = votingMaps[i];
            }
        }
        return winnerMap;
    }

}
