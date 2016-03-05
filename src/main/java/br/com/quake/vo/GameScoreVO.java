package br.com.quake.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

public class GameScoreVO implements Serializable{

	private static final long serialVersionUID = -3261733077319010897L;

	private int totalKills;
	
	private HashSet<String> players;
	
	private Map<String, Integer> kills;

	public GameScoreVO() {
		this.kills = new HashMap<>();
		this.players = new LinkedHashSet<>();
	}
	
	public int getTotalKills() {
		return totalKills;
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}

	public HashSet<String> getPlayers() {
		return players;
	}

	public void setPlayers(HashSet<String> players) {
		this.players = players;
	}

	public Map<String, Integer> getKills() {
		return kills;
	}

	public void setKills(Map<String, Integer> kills) {
		this.kills = kills;
	}
	
	@Override
	public String toString() {
		return "GameScoreVO [totalKills=" + totalKills + ", players=" + players + "]";
	}
}
