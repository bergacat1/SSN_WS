package ssn.beans;

import java.util.ArrayList;
import java.util.List;

public class Field {
	private int idField;
	private ManagerEntity managerEntity;
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

	public ManagerEntity getManagerEntity() {
		return managerEntity;
	}

	public void setManagerEntity(ManagerEntity managerEntity) {
		this.managerEntity = managerEntity;
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
}
