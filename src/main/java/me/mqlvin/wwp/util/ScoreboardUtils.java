package me.mqlvin.wwp.util;

import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Written by Aaron1998ish on 11/12/2017.
 * <p>
 * Useful methods for retrieving data on scoreboards on Hypixel
 */
public class ScoreboardUtils {
    public static List<String> getSidebarSuffixes(Scoreboard scoreboard) {

        return scoreboard.getTeams().stream().map(ScorePlayerTeam::getColorSuffix).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    public static List<String> getSidebarPrefixes(Scoreboard scoreboard) {
        return scoreboard.getTeams().stream().map(ScorePlayerTeam::getColorPrefix).collect(Collectors.toList());
    }
}