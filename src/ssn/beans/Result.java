package ssn.beans;

import java.util.ArrayList;
import java.util.List;

public class Result<T> 
{
	private List<T> data;
	private boolean valid;
	private String error;
	
	public Result()
	{
		data = new ArrayList<>();
		valid = true;
		error = "";
	}
	
	public Result(List<T> data, boolean valid, String error)
	{
		this.data = data;
		this.valid = valid;
		this.error = error;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public void addData(T element)
	{
		this.data.add(element);
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
