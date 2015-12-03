package ssn.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import ssn.beans.Field;
import ssn.beans.ManagerEntity;
import ssn.beans.ReportType;
import ssn.beans.Reservation;
import ssn.beans.Result;
import ssn.beans.Sport;
import ssn.beans.User;

@WebService
public class SSNWS {
	
	@WebMethod
	public Result<Integer> registerUser(User user)
	{
		Result<Integer> result = new Result<>();
		try     
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup( "java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				String sql = "insert into users (type, email, username, name, surname1, surname2, telephone, currentaccount) values "
						+ "(" + user.getType() + ",'" + user.getEmail() + "','" + user.getUsername() + "','" + user.getName() + "','" + user.getSurname1() 
						+ "','" + user.getSurname2() + "'," + user.getTelephone() + "," + user.getCurrentAccount() + ")";
				stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs;
				rs = stm.getGeneratedKeys();
				rs.next();
				result.addData(new Integer(rs.getInt(1)));
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result<User> getUserDetails(int userId)
	{
		Result<User> result = new Result<>();
		try     	
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no tr1obat");
				}
				
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				ResultSet rs = stm.executeQuery("select * "
						+ "from eaccessible.user "
						+ "where iduser = " + userId);
				User user = new User();
				while(rs.next()){
					user = new User();					
					user.setId(userId);					
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
					user.setSurname1(rs.getString("surname1"));
					user.setSurname2(rs.getString("surname2"));
					user.setType(rs.getInt("type"));
					user.setUsername(rs.getString("username"));
					user.setTelephone(rs.getInt("telephone"));
					user.setCurrentAccount(rs.getInt("currentaccount"));
				}
				result.addData(user);
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result updateUser(User user)
	{
		Result result = new Result();
		try     
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup( "java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				String sql = "update ssn.user (type, email, username, name, surname1, surname2, telephone, currentaccount) values "
						+ "(" + user.getType() + ",'" + user.getEmail() + "','" + user.getUsername() + "','" + user.getName() + "','" + user.getSurname1() 
						+ "','" + user.getSurname2() + "'," + user.getTelephone() + "," + user.getCurrentAccount() + ")";
				stm.executeUpdate(sql);				
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result<Sport> getSports()
	{
		Result<Sport> result = new Result<>();
		try     	
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				ResultSet rs = stm.executeQuery("select * from ssn.sports");
				
				Sport s;
				while(rs.next()){
					s= new Sport();
					s.setIdSport(rs.getInt("idsport"));
					s.setName(rs.getString("name"));
					s.setMinPlayers(rs.getInt("minplayers"));
					s.setMaxPlayers(rs.getInt("maxplayers"));
					result.addData(s);
				}

				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	@WebMethod
	public Result<ManagerEntity> getManagerEntitiesBySport(int idSport)
	{
		Result<ManagerEntity> result = new Result<>();
		try     	
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				ResultSet rs = stm.executeQuery("select * from ssn.managerentity me, ssn.users u "
												+ "where me.iduser = u.iduser and exists "
														+ "(select * from fields f, sportfield sf, sport s where f.idmanagerentity = me.idmanagerentity"
														+ " and f.idfield = sf.idfield and sf.idsport = s.idsport and s.idsport = idSport)");
				
				ManagerEntity me;
				User u;
				while(rs.next()){
					u = new User();
					u.setId(rs.getInt("userid"));					
					u.setEmail(rs.getString("email"));
					u.setName(rs.getString("name"));
					u.setSurname1(rs.getString("surname1"));
					u.setSurname2(rs.getString("surname2"));
					u.setType(rs.getInt("type"));
					u.setUsername(rs.getString("username"));
					u.setTelephone(rs.getInt("telephone"));
					u.setCurrentAccount(rs.getInt("currentaccount"));
					
					me = new ManagerEntity();
					me.setEntityManager(u);
					me.setType(rs.getInt("type"));
					me.setName(rs.getString("name"));
					me.setAddress(rs.getString("address"));
					me.setCity(rs.getString("city"));
					me.setLatitude(rs.getDouble("latitude"));
					me.setLongitude(rs.getDouble("longitude"));
					me.setTelephone(rs.getInt("telephone"));
					me.setEmail(rs.getString("email"));
					me.setWeb(rs.getString("email"));
					result.addData(me);
				}

				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result<Integer> createEvent(int idCreator, int idSport, int minPlayers, int maxPlayers, Date startDateTime, Date endDateTime, 
			String city, float latitude, float longitude, int range, List<Integer> managerentities)
	{
		Result<Integer> result = new Result<>();
		try     
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup( "java:jboss/PostgreSQL/SSN");
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				String sql = "insert into events (idcreator, idsport, minplayers, maxplayers, startdatetime, enddatetime) values "
						+ "(" + idCreator + "," + idSport + "," + minPlayers + "," + maxPlayers + ",'" + startDateTime
						+ "','" + endDateTime + "','" + city + "," + latitude + "," + longitude + ")";
				stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs;
				rs = stm.getGeneratedKeys();
				rs.next();
				Integer idEvent = rs.getInt(1);
				result.addData(idEvent);
				
				for (int i = 0; i < managerentities.size(); i++) {
					stm.executeUpdate("insert into eventmanagerentities values (" + idEvent + "," + managerentities.get(i) + ")");
				}
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result<Integer> createReportType(ReportType reportType)
	{
		return null;
	}
	
	@WebMethod
	public Result<Integer> createSport(Sport sport)
	{
		return null;
	}
	
	@WebMethod
	public Result<Integer> createManagerEntity(ManagerEntity me)
	{
		return null;
	}
	
	@WebMethod
	public Result<Integer> addReservation(Reservation reservation)
	{
		return null;
	}
	
	@WebMethod
	public Result<Reservation> getReservationsByField(int idField)
	{
		return null;
	}
	
	@WebMethod
	public Result<Field> getFieldsByManagerEntity(int idManagerEntity)
	{
		return null;
	}
	
	@WebMethod
	public Result addFieldSport(int idField, int idSport, double pricePerHour)
	{
		return null;
	}
	
	@WebMethod
	public Result<Integer> addField(Field field)
	{
		return null;
	}
	
	@WebMethod
	public Result<ManagerEntity> getManagerEntities()
	{
		return null;
	}
}
