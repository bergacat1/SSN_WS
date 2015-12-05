package ssn.beans;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private int idField;
	private int idManagerEntity;
	private String name;
	private List<FieldSports> sports;
	
	public Field()
	{
		sports = new ArrayList<>();
	}

	public int getIdField() {
		return idField;
	}

	public void setIdField(int idField) {
		this.idField = idField;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FieldSports> getSports() {
		return sports;
	}

	public void setSports(List<FieldSports> sports) {
		this.sports = sports;
	}

	public int getIdManagerEntity() {
		return idManagerEntity;
	}

	public void setIdManagerEntity(int idManagerEntity) {
		this.idManagerEntity = idManagerEntity;
	}
}
