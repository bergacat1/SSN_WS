package ssn.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import ssn.beans.Field;
import ssn.beans.FieldSports;
import ssn.beans.ManagerEntity;
import ssn.beans.ReportType;
import ssn.beans.Reservation;
import ssn.beans.Result;
import ssn.beans.Sport;
import ssn.beans.User;

@WebService
public class SSNWS {
	
	final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSX";
	
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
				ResultSet rs = stm.executeQuery("select * from sports");
				
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
	public Result<Sport> getSportById(int idSport)
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
				ResultSet rs = stm.executeQuery("select * from sports where idsport = " + idSport);
				
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
				String sql = "insert into reporttypes (name, severity) values "
						+ "('" + reportType.getName() + "'," + reportType.getSeverity() + ")";
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
	public Result<Integer> createSport(Sport sport)
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
				String sql = "insert into sports (name, minplayers, maxplayers) values "
						+ "('" + sport.getName() + "'," + sport.getMinPlayers() + "," + sport.getMaxPlayers() + ")";
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
	public Result<Integer> addReservation(Reservation reservation)
	{
		Result<Integer> result = new Result<>();
		try     
		{
			InitialContext cxt = new InitialContext();
			if ( cxt != null ) 
			{			
				DataSource ds = (DataSource) cxt.lookup( "java:jboss/PostgreSQL/SSN");
				DateFormat df = new SimpleDateFormat(TIME_FORMAT);
				
				if ( ds == null ) 
				{
			 		System.out.print("Data source no trobat");
				}
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				String sql = "insert into reservations (" + (reservation.getIdEvent() > 0 ? "idevent," : "") + "idfield, startdate, enddate, comfirmed, type) values "
						+ "(" + (reservation.getIdEvent() > 0 ? reservation.getIdEvent() + "," : "") + reservation.getIdField() + ",'" + df.format(new Date(reservation.getStartDate())) + "','" 
						+ df.format(new Date(reservation.getEndDate())) + "'," + (reservation.isConfirmed() ? "'1'" : "'0'") + "," + reservation.getType() + ")";
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
	public Result<Reservation> getReservationsByField(int idField)
	{
		Result<Reservation> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select * from reservations");
				
				Reservation r;
				while(rs.next()){
					r= new Reservation();
					r.setIdReservation(rs.getInt("idreservation"));
					r.setIdEvent(rs.getInt("idevent"));
					r.setIdField(rs.getInt("idfield"));
					r.setStartDate(rs.getDate("startdate").getTime());
					r.setEndDate(rs.getDate("enddate").getTime());
					r.setConfirmed(rs.getBoolean("comfirmed"));
					r.setType(rs.getInt("type"));
					result.addData(r);
				}

				connection.close();
				stm.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result deleteReservation(int idReservation){
		Result result = new Result();
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
				stm.executeUpdate("delete from reservations where idreservation = " + idReservation);	
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
	public Result<Integer> addField(Field field)
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
				String sql = "insert into fields (idmanagerentity, name) values "
						+ "(" + field.getIdManagerEntity() + ",'" + field.getName() + "')";
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
	public Result<Field> getFieldById(int idField)
	{
		Result<Field> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select * from fields where idfield = " + idField);
				
				Field f;
				FieldSports fs;
				rs.next();
				f = new Field();
				f.setIdField(rs.getInt("idfield"));
				f.setIdManagerEntity(rs.getInt("idmanagerentity"));
				f.setName(rs.getString("name"));
				rs = stm.executeQuery("select sf.*, s.name from sportfield sf, sports s where sf.idsport = s.idsport and idfield = " + idField);
				while(rs.next()){
					fs = new FieldSports();
					fs.setHourPrice(rs.getDouble("hourprice"));
					fs.setIdSport(rs.getInt("idsport"));
					fs.setSportName(rs.getString("name"));
					f.getSports().add(fs);
				}
				result.addData(f);
				

				connection.close();
				stm.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}
	
	@WebMethod
	public Result<Field> getFieldsByManagerEntity(int idManagerEntity)
	{
		Result<Field> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select * from fields where idmanagerentity = " + idManagerEntity);
				
				Field f;
				FieldSports fs;
				while(rs.next()){
					f= new Field();
					f.setIdField(rs.getInt("idfield"));
					f.setIdManagerEntity(rs.getInt("idmanagerentity"));
					f.setName(rs.getString("name"));
					result.addData(f);
				}
				for (Field field : result.getData()) {
					rs = stm.executeQuery("select sf.*, s.name from sportfield sf, sports s where sf.idsport = s.idsport and idfield = " + field.getIdField());
					while(rs.next()){
						fs = new FieldSports();
						fs.setHourPrice(rs.getDouble("hourprice"));
						fs.setIdSport(rs.getInt("idsport"));
						fs.setSportName(rs.getString("name"));
						field.getSports().add(fs);
					}
				}			

				connection.close();
				stm.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}
		return result;
	}

	@WebMethod
	public Result<Integer> updateField(Field field)
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
				String sql = "update fields set idmanagerentity=" + field.getIdManagerEntity() + ",name='" + field.getName() 
						+ "' where idfield = " + field.getIdField();
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
	public Result deleteField(int idField){
		Result result = new Result();
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
				stm.executeUpdate("delete from fields where idfield = " + idField);	
				stm.executeUpdate("delete from sportfield where idfield = " + idField);
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
	public Result addFieldSport(int idField, int idSport, double pricePerHour)
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
				String sql = "insert into sportfield (idfield, idsport, hourprice) values "
						+ "(" + idField + "," + idSport + "," + pricePerHour + ")";
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
	public Result updateFieldSport(int idField, int idSport, double pricePerHour)
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
				String sql = "update sportfield set hourprice=" + pricePerHour + " where idfield=" + idField + " and idsport = " + idSport;
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
	public Result deleteFieldSport(int idField, int idSport){
		Result result = new Result();
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
				stm.executeUpdate("delete from sportfield where idfield = " + idField + " and idsport = " + idSport);	
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
	public Result<Integer> createManagerEntity(ManagerEntity me)
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
				String sql = "insert into managerentity (iduser, type, name, address, city, latitude, longitude, telephone, email, web) values "
						+ "(" + me.getIdUser() + "," + me.getType() + ",'" + me.getName() + "','" + me.getAddress()  
						+ "','" + me.getCity() + "'," + me.getLatitude() + "," + me.getLongitude() + "," + me.getTelephone() + ",'" 
						+ me.getEmail() + "','" + me.getWeb() + "')";
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
	public Result updateManagerEntity(ManagerEntity me)
	{
		Result result = new Result<>();
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
				String sql = "update managerentity set "
						+ "iduser=" + me.getIdUser() + ", type=" + me.getType() + ",name='" + me.getName() + "',address='" + me.getAddress()  
						+ "',city='" + me.getCity() + "',latitude=" + me.getLatitude() + ",longitude=" + me.getLongitude() 
						+ ",telephone=" + me.getTelephone() + ",email='" + me.getEmail() + "',web='" + me.getWeb() + "' where idmanagerentity = " + me.getIdManagerEntity();
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
	public Result<ManagerEntity> getManagerEntities()
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
				ResultSet rs = stm.executeQuery("select * from managerentity ");
				
				ManagerEntity me;
				while(rs.next()){
					
					me = new ManagerEntity();
					me.setIdUser(rs.getInt("iduser"));
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
	public Result<ManagerEntity> getManagerEntitiesById(int idManagerEntity)
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
				ResultSet rs = stm.executeQuery("select * from managerentity where idmanagerentity = " + idManagerEntity);
				
				ManagerEntity me;
				while(rs.next()){
					
					me = new ManagerEntity();
					me.setIdUser(rs.getInt("iduser"));
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
				ResultSet rs = stm.executeQuery("select * from managerentity me "
												+ "where exists "
														+ "(select * from fields f, sportfield sf, sports s where f.idmanagerentity = me.idmanagerentity"
														+ " and f.idfield = sf.idfield and sf.idsport = s.idsport and s.idsport = " + idSport + ")");
				
				ManagerEntity me;
				while(rs.next()){
					
					me = new ManagerEntity();
					me.setIdUser(rs.getInt("iduser"));
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
	public Result deleteManagerEntity(int idManagerEntity){
		Result result = new Result();
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
				ResultSet rs = stm.executeQuery("select idfield from fields where idmanagerentity = " + idManagerEntity);
				while(rs.next())
					stm.executeUpdate("delete from sportfield where idfield = " + rs.getInt("idfield"));
				stm.executeUpdate("delete from fields where idmanagerentity = " + idManagerEntity);	
				stm.executeUpdate("delete from managerentity where idmanagerentity = " + idManagerEntity);
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
}
