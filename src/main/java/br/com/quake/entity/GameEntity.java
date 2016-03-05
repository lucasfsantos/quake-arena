package br.com.quake.entity;

import java.util.ArrayList;
import java.util.List;

public class GameEntity{
	
	private int id;
	
	private List<KillEntity> kills;
	
	public GameEntity(int id) {
		this.id = id;
		this.kills = new ArrayList<>();
	}

	public List<KillEntity> getKills() {
		return kills;
	}

	public void setKills(List<KillEntity> kills) {
		this.kills = kills;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GameEntity [id=" + id + ", kills=" + kills + "]";
	}
}
