package com.ultrawars.lucca.utils;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
 
public abstract class ScoreBuilder {
 
        private Scoreboard scoreboard;
 
        private String title;
        private Map<String, Integer> scores;
        private List<Team> teams;
        Player p;
 
        public ScoreBuilder(Player p, String title) {
                this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                this.title = title;
                this.scores = Maps.newLinkedHashMap();
                this.teams = Lists.newArrayList();
                this.p = p;
        }
        
        public abstract void update();
        
        public void blankLine() {
        	addLine(" ");
        }
 
        public void addLine(String text) {
        	addLine(text, null);
        }
 
        public void addLine(String text, Integer score) {
                Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
                text = fixDuplicates(text);
                scores.put(text, score);
        }
        
        @SuppressWarnings("deprecation")
		public void updateLine(Player p, String text, Integer line){
            Map.Entry<Team, String> team = createTeam(text);
            OfflinePlayer player = Bukkit.getOfflinePlayer(team.getValue());
            if (team.getKey() != null)
            team.getKey().addPlayer(player);
            scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(player).setScore(line);
            scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(player).setScore(line);
        }
 
        private String fixDuplicates(String text) {
                while (scores.containsKey(text))
                        text += "§r";
                if (text.length() > 48)
                        text = text.substring(0, 47);
                return text;
        }
 
        private Map.Entry<Team, String> createTeam(String text) {
                String result = "";
                if (text.length() <= 16)
                        return new AbstractMap.SimpleEntry<>(null, text);
                Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
                Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
                team.setPrefix(iterator.next());
                result = iterator.next();
                if (text.length() > 32)
                        team.setSuffix(iterator.next());
                teams.add(team);
                return new AbstractMap.SimpleEntry<>(team, result);
        }
 
        @SuppressWarnings("deprecation")
		public void build() {
                Objective obj = scoreboard.registerNewObjective((title.length() > 16 ? title.substring(0, 15) : title), "dummy");
                obj.setDisplayName(title);
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
 
                int index = scores.size();
 
                for (Map.Entry<String, Integer> text : scores.entrySet()) {
                        Map.Entry<Team, String> team = createTeam(text.getKey());
                        Integer score = text.getValue() != null ? text.getValue() : index;
                        OfflinePlayer player = Bukkit.getOfflinePlayer(team.getValue());
                        if (team.getKey() != null)
                        team.getKey().addPlayer(player);
                        obj.getScore(player).setScore(score);
                        index -= 1;
                }
                p.setScoreboard(scoreboard);
        }
        
        public void clearScores(){
        	scores.clear(); 
        }
 
        public void reset() {
                title = null;
                scores.clear();
                for (Team t : teams)
                        t.unregister();
                teams.clear();
        }
 
        public Scoreboard getScoreboard() {
                return scoreboard;
        }
 
 
}

