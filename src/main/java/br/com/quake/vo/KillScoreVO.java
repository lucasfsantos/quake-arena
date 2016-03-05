package br.com.quake.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KillScoreVO implements Serializable{

	private static final long serialVersionUID = 7303834486391303183L;

	private Map<String, Integer> players;

	public KillScoreVO() {
		this.players = new HashMap<>();
	}
	
	public Map<String, Integer> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Integer> players) {
		this.players = players;
	}
}
