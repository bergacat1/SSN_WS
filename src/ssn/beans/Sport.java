package ssn.beans;

import java.util.ArrayList;
import java.util.List;

public class Sport {
	private int idSport;
	private String name;
	private int minPlayers, maxPlayers;
	private List<Field> fields;
	
	public Sport()
	{
		fields = new ArrayList<>();
	}
	
	public int getIdSport() {
		return idSport;
	}
	public void setIdSport(int idSport) {
		this.idSport = idSport;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMinPlayers() {
		return minPlayers;
	}
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
