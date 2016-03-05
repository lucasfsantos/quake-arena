package br.com.quake.entity;

public class KillEntity {

	private String killer;
	
	private String killed;
	
	public KillEntity(String killer, String killed) {
		super();
		this.killer = killer;
		this.killed = killed;
	}

	public String getKiller() {
		return killer;
	}

	public void setKiller(String killer) {
		this.killer = killer;
	}

	public String getKilled() {
		return killed;
	}

	public void setKilled(String killed) {
		this.killed = killed;
	}

	@Override
	public String toString() {
		return "KillEntity [killer=" + killer + ", killed=" + killed + "]";
	}
}
