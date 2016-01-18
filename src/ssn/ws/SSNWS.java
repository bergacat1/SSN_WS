package ssn.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import ssn.beans.Event;
import ssn.beans.Field;
import ssn.beans.FieldSports;
import ssn.beans.ManagerEntity;
import ssn.beans.Report;
import ssn.beans.ReportType;
import ssn.beans.Reservation;
import ssn.beans.Result;
import ssn.beans.Sport;
import ssn.beans.User;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@WebService
public class SSNWS {
	
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
	private static final String SENDER_ID = "AIzaSyD2Z4HHOA3_rbUJVSHDcmPyJfs-JL9wv1g";
	
	@WebMethod
	public Result<User> registerUser(@WebParam(name="user") User user)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("RegisterUser: %s", user.getEmail()));
		Result<User> result = new Result<>();
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
				ResultSet rs;
				
				rs = stm.executeQuery("select * from users where email = '" + user.getEmail() + "'");
				if(rs.next()){
					User u = new User();					
					u.setId(rs.getInt("iduser"));					
					u.setEmail(rs.getString("email"));
					u.setName(rs.getString("name"));
					u.setSurname1(rs.getString("surname1"));
					u.setSurname2(rs.getString("surname2"));
					u.setType(rs.getInt("type"));
					u.setUsername(rs.getString("username"));
					u.setTelephone(rs.getInt("telephone"));
					u.setCurrentAccount(rs.getInt("currentaccount"));
					u.setGcmId(rs.getString("gcmid"));
					u.setSettings(rs.getString("settings"));
					if(!u.getGcmId().equals(user.getGcmId())){
						stm.execute("update users set gcmid = '" + user.getGcmId() + "' where email = '" + user.getEmail() + "'");
						u.setGcmId(user.getGcmId());
					}
					result.addData(u);						
				}else{
					String sql = "insert into users (type, email, username, name, surname1, surname2, telephone, currentaccount, gcmid, settings) values "
							+ "(" + user.getType() + ",'" + user.getEmail() + "','" + user.getUsername() + "','" + user.getName() + "','" + user.getSurname1() 
							+ "','" + user.getSurname2() + "'," + user.getTelephone() + "," + user.getCurrentAccount() + ",'" + user.getGcmId() + "','"
							+ user.getSettings() + "')";
					stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					rs = stm.getGeneratedKeys();
					rs.next();
					user.setId(rs.getInt(1));
					result.addData(user);
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
	public Result setUserSettings(@WebParam(name="user") User user)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("SetUserSettings: %s", user.getId()));
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
				
				String sql = "update users set username = '" + user.getUsername() + "', settings = '" + user.getSettings() + "' where iduser = " + user.getId();
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
	public Result<User> getUserDetails(@WebParam(name="userid") int userId)
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
			 		System.out.print("Data source no trobat");
				}
				
				Connection connection = ds.getConnection();
				Statement stm = connection.createStatement(); 
				ResultSet rs = stm.executeQuery("select * "
						+ "from users "
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
					user.setGcmId(rs.getString("gcmid"));
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
	public Result<User> getUsersByEvent(@WebParam(name="idevent") int idEvent)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetUsersByEvent: %d", idEvent));
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
				String sql = "select * from users u join eventusers eu on (u.iduser = eu.iduser) "
						+ "where eu.idevent = " + idEvent;
				ResultSet rs = stm.executeQuery(sql);
				User user = new User();
				while(rs.next()){
					user = new User();					
					user.setId(rs.getInt("iduser"));					
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
					user.setSurname1(rs.getString("surname1"));
					user.setSurname2(rs.getString("surname2"));
					user.setType(rs.getInt("type"));
					user.setUsername(rs.getString("username"));
					user.setTelephone(rs.getInt("telephone"));
					user.setCurrentAccount(rs.getInt("currentaccount"));
					user.setGcmId(rs.getString("gcmid"));
					result.addData(user);
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
	public Result<Integer> updateUser(@WebParam(name="user") User user)
	{
		Result<Integer> result = new Result<Integer>();
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
				String sql = "update user set type =" + user.getType() + ", email='" + user.getEmail() + "', username='" + user.getUsername() + 
						"', name'" + user.getName() + "', surname1='" + user.getSurname1() + "', surname2'" + user.getSurname2() + 
						"', telephone=" + user.getTelephone() + ", currentaccount" + user.getCurrentAccount() + ", gcmid='" + user.getGcmId() + 
						" where iduser = " + user.getId();
				stm.executeUpdate(sql);	
				result.addData(user.getId());			
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
	public Result<Integer> logoutUser(@WebParam(name="userid") int userid)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("LogoutUser: %d", userid));
		Result<Integer> result = new Result<Integer>();
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
				String sql = "update users set gcmid = '' where iduser = " + userid;
				stm.executeUpdate(sql);	
				result.addData(userid);			
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
	public Result<Integer> createSport(@WebParam(name="sport") Sport sport)
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
	public Result<Sport> getSports()
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "GetSports");
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
	public Result<Sport> getSportById(@WebParam(name="idsport") int idSport)
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
	public Result<Integer> createEvent(@WebParam(name="event") Event event)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("CreateEvent::  Sport: %s  User: %d", event.getSportName(), event.getIdCreator()));
		Result<Integer> result = new Result<>();
		Connection connection = null;
		Statement stm = null;
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
				connection = ds.getConnection();
				stm = connection.createStatement(); 
				ResultSet rs;
				int sportMinPlayers = 0;
				int sportMaxPlayers = 0;
				if(event.getMinPlayers() == 0 || event.getMaxPlayers() == 0)
				{
					rs = stm.executeQuery("select minplayers, maxplayers from sports where idsport =" + event.getIdSport());
					if(rs.next())
					{
						sportMinPlayers = rs.getInt("minplayers");
						sportMaxPlayers = rs.getInt("maxplayers");
					}
				}
				if(!event.getManagerEntities().isEmpty()){
					rs = stm.executeQuery("select city from managerentity where idmanagerentity = " + event.getManagerEntities().get(0));
					if(rs.next())
						event.setCity(rs.getString("city") + "...");
				}
				String sql = "insert into events (idcreator, idsport, minplayers, maxplayers, startdatetime, enddatetime, city, latitude,"
						+ " longitude, range, maxprice, limitdatetime, canceled) values "
						+ "(" + event.getIdCreator() + "," + event.getIdSport() + "," + (event.getMinPlayers() == 0 ? sportMinPlayers : event.getMinPlayers()) 
						+ "," + (event.getMaxPlayers() == 0 ? sportMaxPlayers : event.getMaxPlayers()) + ",'" + df.format(new Date(event.getStartDate()))
						+ "','" + df.format(new Date(event.getEndDate())) + "','" + event.getCity() + "'," + event.getLatitude() + "," + event.getLongitude() 
						+ "," + event.getRange() + "," + event.getMaxPrice() + ",'" + df.format(new Date(event.getLimitDate())) + "','FALSE')";
				stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				rs = stm.getGeneratedKeys();
				rs.next();
				Integer idEvent = rs.getInt(1);
				result.addData(idEvent);
				
				if(event.getManagerEntities() != null)
					for (int i = 0; i < event.getManagerEntities().size(); i++) {
						stm.executeUpdate("insert into eventmanagerentities values (" + idEvent + "," + event.getManagerEntities().get(i) + ")");
					}
				
				rs = stm.executeQuery("select type from users where iduser = " + event.getIdCreator());
				
				if(rs.next() && rs.getInt("type") == 0)
					stm.executeUpdate("insert into eventusers values (" + idEvent + "," + event.getIdCreator() + ")");
				
				rs = stm.executeQuery("select gcmid from users where gcmid <> '' and substring(settings, 2, 1) = '1' and iduser <> " + event.getIdCreator());
				List<String> usersToNotify = new ArrayList<>();
				while(rs.next())
					if(rs.getString("gcmid") != "")
						usersToNotify.add(rs.getString("gcmid"));
				if(!usersToNotify.isEmpty())
					sendPushNotification(usersToNotify, idEvent, 0);				
			}   
					
		}catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			result.setValid(false);
			result.setError(e.getMessage());
		}finally{
			try {
				connection.close();
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@WebMethod
	public Result<Event> getUnjoinedEvents(@WebParam(name="userid")int userId)
	{
		Result<Event> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select e.*, s.name from events e join sports s on (e.idsport = s.idsport) where canceled = FALSE userid <> " + userId);
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setSportName(rs.getString("name"));
					e.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					e.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
					e.setLimitDate(df.parse(rs.getString("limitdatetime")).getTime());
					if(rs.getBoolean("canceled"))
						e.setState(Event.States.CANCELED);
					result.addData(e);
				}
				
				for (Event event : result.getData()) {
					rs = stm.executeQuery("select count(*) as players from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setActualPlayers(rs.getInt("players"));
					}else{
						event.setActualPlayers(0);
					}

					rs = stm.executeQuery("select count(*) as numusers from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						int numUsers = rs.getInt("numusers");
						if(numUsers == event.getMaxPlayers())
							event.setState(Event.States.COMPLETED);
						else 
							event.setState(Event.States.OPEN);
					}else
						event.setState(Event.States.OPEN);
					
					rs = stm.executeQuery("select idreservation from reservations where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setState(Event.States.RESERVED);
						event.setIdReservation(rs.getInt("idreservation"));
					}					
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
	public Result<Event> getEventsByFilters(@WebParam(name="iduser") int idUser, @WebParam(name="idsport") int idSport,@WebParam(name="minplayers")  int minPlayers,
										@WebParam(name="maxprice") double maxPrice,@WebParam(name="fromdate")  long fromDate,
										@WebParam(name="todate")  long toDate)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetEventsByFilters: %d", idUser));
		Result<Event> result = new Result<>();
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
				StringBuilder sb = new StringBuilder();
				sb.append("select e.*, s.name from events e join sports s on (e.idsport = s.idsport) ");
				sb.append("where e.limitdatetime > current_timestamp and canceled = FALSE and ");
				sb.append("e.maxplayers > (select count(*) from eventusers eu where eu.idevent = e.idevent)");
				sb.append(" and not exists (select * from eventusers eu where eu.idevent = e.idevent and eu.iduser = " + idUser + ")");
				if(idSport > 0)
					sb.append(" and e.idsport = " + idSport);
				if(minPlayers > 0)
					sb.append(" and (select count(*) from eventusers eu where eu.idevent = e.idevent) >= " + minPlayers);
				if(maxPrice > 0)
					sb.append(" and e.maxprice <= " + maxPrice);
				if(fromDate > 0)
					sb.append(" and e.startdatetime >= '" + df.format(new Date(fromDate)) + "'");
				if(toDate > 0)
					sb.append(" and e.startdatetime <= '" + df.format(new Date(toDate)) + "'");
				ResultSet rs = stm.executeQuery(sb.toString());
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setSportName(rs.getString("name"));
					e.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					e.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
					e.setLimitDate(df.parse(rs.getString("limitdatetime")).getTime());
					result.addData(e);
				}
				
				for (Event event : result.getData()) {
					rs = stm.executeQuery("select * from eventusers where idevent = " + event.getIdEvent() + " and iduser = " + idUser);
					event.setJoined(rs.next());
					rs = stm.executeQuery("select count(*) as players from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setActualPlayers(rs.getInt("players"));
					}else{
						event.setActualPlayers(0);
					}
					rs = stm.executeQuery("select count(*) as numusers from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						int numUsers = rs.getInt("numusers");
//						Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("Event: %d  NumUsers: %d  MaxUsers: %d", 
//								event.getIdEvent(), numUsers, event.getMaxPlayers()));
						if(numUsers == event.getMaxPlayers())
							event.setState(Event.States.COMPLETED);
						else 
							event.setState(Event.States.OPEN);
					}else
						event.setState(Event.States.OPEN);
					
					rs = stm.executeQuery("select idreservation from reservations where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setState(Event.States.RESERVED);
						event.setIdReservation(rs.getInt("idreservation"));
					}					
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
	public Result<Event> getEventById(@WebParam(name="idevent") int idEvent, @WebParam(name="iduser") int idUser)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetEventById: %d  User: %d", idEvent, idUser));
		Result<Event> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select * from events e join sports s on (e.idsport = s.idsport) where idevent = " + idEvent);
				
				Event e;
				if(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setSportName(rs.getString("name"));
					e.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					e.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
					e.setLimitDate(df.parse(rs.getString("limitdatetime")).getTime());
					if(rs.getBoolean("canceled"))
						e.setState(Event.States.CANCELED);
					rs = stm.executeQuery("select * from eventmanagerentities where idevent = " + e.getIdEvent());
					if(e.getRange() != 0 || rs.next())
						e.setCity("");
					rs = stm.executeQuery("select * from eventusers where idevent = " + idEvent + " and iduser = " + idUser);
					if(rs.next())
						e.setJoined(true);
					if(e.getState() != Event.States.CANCELED){
						rs = stm.executeQuery("select count(*) as numusers from eventusers where idevent = " + e.getIdEvent());
						if(rs.next()){
							int numUsers = rs.getInt("numusers");
							if(numUsers == e.getMaxPlayers())
								e.setState(Event.States.COMPLETED);
							else 
								e.setState(Event.States.OPEN);
						}else
							e.setState(Event.States.OPEN);
						
						rs = stm.executeQuery("select idreservation from reservations where idevent = " + e.getIdEvent());
						if(rs.next()){
							e.setState(Event.States.RESERVED);
							e.setIdReservation(rs.getInt("idreservation"));
						}
					}
					
					result.addData(e);
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
	public Result<Event> getEventsByUser(@WebParam(name="iduser") int idUser)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetEventsByUser: %d", idUser));
		Result<Event> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select e.*, eu.*, s.name from events e join eventusers eu on (e.idevent = eu.idevent), sports s where"
						+ " s.idsport = e.idsport and e.startdatetime > current_timestamp and canceled = FALSE and eu.iduser = " + idUser);
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setSportName(rs.getString("name"));
					e.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					e.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
					e.setLimitDate(df.parse(rs.getString("limitdatetime")).getTime());
					result.addData(e);
				}
				
				for (Event event : result.getData()) {
					rs = stm.executeQuery("select count(*) as players from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setActualPlayers(rs.getInt("players"));
					}else{
						event.setActualPlayers(0);
					}

					rs = stm.executeQuery("select count(*) as numusers from eventusers where idevent = " + event.getIdEvent());
					if(rs.next()){
						int numUsers = rs.getInt("numusers");
						Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("Event: %d  NumUsers: %d  MaxUsers: %d", 
						event.getIdEvent(), numUsers, event.getMaxPlayers()));
						if(numUsers == event.getMaxPlayers())
							event.setState(Event.States.COMPLETED);
						else 
							event.setState(Event.States.OPEN);
					}else
						event.setState(Event.States.OPEN);
					
					rs = stm.executeQuery("select idreservation from reservations where idevent = " + event.getIdEvent());
					if(rs.next()){
						event.setState(Event.States.RESERVED);
						event.setIdReservation(rs.getInt("idreservation"));
					}					
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
	public Result<Event> getEventsHistoryByUser(@WebParam(name="iduser") int idUser)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetEventsHistoryByUser: %d", idUser));
		Result<Event> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select e.*, eu.*, s.name from events e join eventusers eu on (e.idevent = eu.idevent), sports s where s.idsport = e.idsport and (e.startdatetime <= current_timestamp or canceled = TRUE) and eu.iduser = " + idUser);
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setSportName(rs.getString("name"));
					e.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					e.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
					e.setLimitDate(df.parse(rs.getString("limitdatetime")).getTime());
					if(rs.getBoolean("canceled"))
						e.setState(Event.States.CANCELED);
					else
						e.setState(Event.States.FINISHED);
					result.addData(e);
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
	public Result joinEvent(@WebParam(name="iduser") int idUser, @WebParam(name="idevent") int idEvent)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("JoinEvent: %d  User: %d", idEvent, idUser));
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
				String sql = "insert into eventusers values "
						+ "(" + idEvent + "," + idUser + ")";
				stm.executeUpdate(sql);
				ResultSet rs = stm.executeQuery("select gcmid from users where gcmid <> '' and substring(settings, 5, 1) = '1' and iduser <> " + idUser);
				List<String> usersToNotify = new ArrayList<>();
				while(rs.next())
					if(rs.getString("gcmid") != "")
						usersToNotify.add(rs.getString("gcmid"));
				if(!usersToNotify.isEmpty())
					sendPushNotification(usersToNotify, idEvent, 1);	
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
	public Result leaveEvent(@WebParam(name="iduser") int idUser, @WebParam(name="idevent") int idEvent)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("LeaveEvent: %d  User: %d", idEvent, idUser));
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
				String sql = "delete from eventusers where idevent = " + idEvent + " and iduser = " + idUser;
				stm.executeUpdate(sql);
				
				ResultSet rs = stm.executeQuery("select * from eventusers where idevent = " + idEvent);
				if(!rs.next())
				{
					stm.executeUpdate("delete from eventmanagerentities where idevent = " + idEvent);
					stm.executeUpdate("delete from events where idevent = " + idEvent);
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
	public Result<Integer> createReportType(@WebParam(name="reporttype") ReportType reportType)
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
	public Result<ReportType> getReportTypes()
	{
		Result<ReportType> result = new Result<>();
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
				ResultSet rs = stm.executeQuery("select * from reporttypes");
				
				ReportType r;
				while(rs.next()){
					r = new ReportType();
					r.setIdReportType(rs.getInt("idreporttype"));
					r.setName(rs.getString("name"));
					r.setSeverity(rs.getInt("severity"));
					result.addData(r);
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
	public Result<Integer> reportUser(@WebParam(name="report") Report report)
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
				String sql = "insert into reports ( idreporttype, " + (report.getIdUser() > 0 ? "iduser," : "idfield,") + "idreporter, date, comment) values "
						+ "(" + report.getIdReportType() + "," + (report.getIdUser() > 0 ? report.getIdUser() : report.getIdField()) + "," + report.getIdReporter() 
						+ ",current_timestamp,'" + report.getComment() + "')";
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
	public Result<Integer> addReservation(@WebParam(name="reservation") Reservation reservation)
	{
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("AddReservation::  Field: %d  Event:", reservation.getIdField(), reservation.getIdEvent()));
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
				String verificationQuery = "select * from reservations where idfield = " + reservation.getIdField() + " and startdate < '" + df.format(new Date(reservation.getEndDate())) + "' and enddate > '" + 
						df.format(new Date(reservation.getStartDate())) + "'";
				ResultSet auxRs = stm.executeQuery(verificationQuery);
				if(!auxRs.next()){
					String sql = "insert into reservations (" + (reservation.getIdEvent() > 0 ? "idevent," : "") + "idfield, startdate, enddate, comfirmed, type) values "
							+ "(" + (reservation.getIdEvent() > 0 ? reservation.getIdEvent() + "," : "") + reservation.getIdField() + ",'" + df.format(new Date(reservation.getStartDate())) + "','" 
							+ df.format(new Date(reservation.getEndDate())) + "'," + (reservation.isConfirmed() ? "'1'" : "'0'") + "," + reservation.getType() + ")";
					stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs;
					rs = stm.getGeneratedKeys();
					rs.next();
					result.addData(new Integer(rs.getInt(1)));
				}else{
					result.setValid(false);
					result.setError("This reservation collides with other reservations.");
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
	public Result<Integer> addReservations(@WebParam(name="reservations") List<Reservation> reservations)
	{
		Result<Integer> result = new Result<>();	
		if(reservations == null) return result;
		for (Reservation reservation : reservations) {
			Result<Integer> auxResult = addReservation(reservation);
			if(!auxResult.isValid())
				result = auxResult;
		}
		return result;
	}
	
	@WebMethod
	public Result<Reservation> getReservationsByField(@WebParam(name="idfield") int idField)
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
					r.setStartDate(df.parse(rs.getString("startdate")).getTime());
					r.setEndDate(df.parse(rs.getString("enddate")).getTime());
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
	public Result<Reservation> getReservationsById(@WebParam(name="idreservation") int idReservation)
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
				ResultSet rs = stm.executeQuery("select * from reservations where idreservation = " + idReservation);
				
				Reservation r;
				if(rs.next()){
					r= new Reservation();
					r.setIdReservation(rs.getInt("idreservation"));
					r.setIdEvent(rs.getInt("idevent"));
					r.setIdField(rs.getInt("idfield"));
					r.setStartDate(df.parse(rs.getString("startdatetime")).getTime());
					r.setEndDate(df.parse(rs.getString("enddatetime")).getTime());
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
	public Result deleteReservation(@WebParam(name="idreservation") int idReservation){
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
	public Result deleteReservations(@WebParam(name="reservations") List<Reservation> reservations)
	{
		Result result = new Result();	
		if(reservations == null) return result;
		for (Reservation reservation : reservations) {
			Result auxResult = deleteReservation(reservation.getIdReservation());
			if(!auxResult.isValid())
				result = auxResult;
		}
		return result;
	}
	
	@WebMethod
	public Result<Integer> addField(@WebParam(name="field") Field field)
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
				int id = new Integer(rs.getInt(1));
				result.addData(id);
				connection.close();
				stm.close();
				Result aux;
				for(FieldSports f : field.getSports()){
					aux = addFieldSport(id,f.getIdSport(),f.getHourPrice());
					if(!aux.isValid()){
						result = aux;
					}
				}
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
	public Result<Field> getFieldById(@WebParam(name="idfield") int idField)
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
	public Result<Field> getFieldsByManagerEntity(@WebParam(name="idmanagerentity") int idManagerEntity)
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
	public Result<Integer> updateField(@WebParam(name="field") Field field)
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
				
				Result aux = deleteFieldSportByIdField(field.getIdField());
				if(!aux.isValid()){
					result = aux;
				}
				
				for(FieldSports f : field.getSports()){
					aux = addFieldSport(field.getIdField(),f.getIdSport(),f.getHourPrice());
					if(!aux.isValid()){
						result = aux;
						break;
					}
				}
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
	public Result deleteField(@WebParam(name="idfield") int idField){
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
	public Result addFieldSport(@WebParam(name="idfield") int idField, @WebParam(name="idsport") int idSport, 
								@WebParam(name="priceperhour") double pricePerHour)
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
	public Result updateFieldSport(@WebParam(name="idfield") int idField, @WebParam(name="idsport") int idSport, 
									@WebParam(name="priceperhour") double pricePerHour)
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
	public Result deleteFieldSport(@WebParam(name="idfield") int idField, @WebParam(name="idsport") int idSport){

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
	
	private Result deleteFieldSportByIdField(@WebParam(name="idfield") int idField){

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
	public Result<Integer> createManagerEntity(@WebParam(name="managerentity") ManagerEntity managerEntity)
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
						+ "(" + managerEntity.getIdUser() + "," + managerEntity.getType() + ",'" + managerEntity.getName() + "','" + managerEntity.getAddress()  
						+ "','" + managerEntity.getCity() + "'," + managerEntity.getLatitude() + "," + managerEntity.getLongitude() + "," + managerEntity.getTelephone() + ",'" 
						+ managerEntity.getEmail() + "','" + managerEntity.getWeb() + "')";
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
	public Result updateManagerEntity(@WebParam(name="managerentity") ManagerEntity managerEntity)
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
						+ "iduser=" + managerEntity.getIdUser() + ", type=" + managerEntity.getType() + ",name='" + managerEntity.getName() + "',address='" + managerEntity.getAddress()  
						+ "',city='" + managerEntity.getCity() + "',latitude=" + managerEntity.getLatitude() + ",longitude=" + managerEntity.getLongitude() 
						+ ",telephone=" + managerEntity.getTelephone() + ",email='" + managerEntity.getEmail() + "',web='" + managerEntity.getWeb() + "' where idmanagerentity = " + managerEntity.getIdManagerEntity();
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
					me.setIdManagerEntity(rs.getInt("idmanagerentity"));
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
	public Result<ManagerEntity> getManagerEntitiesByIds(@WebParam(name="idmanagerentities")List<Integer> ids){
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("GetManagerEntitiesByIds: %d", ids.size()));		
		Result<ManagerEntity> result = new Result<>();
		Result<ManagerEntity> auxResult;
		for (Integer id : ids) {
			auxResult = getManagerEntitiesById(id);
			if(auxResult.isValid())
				result.addData(auxResult.getData().get(0));
		}
		
		return result;
	}
	
	@WebMethod
	public Result<ManagerEntity> getManagerEntitiesById(@WebParam(name="idmanagerentity") int idManagerEntity)
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
					me.setIdManagerEntity(idManagerEntity);
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
	public Result<ManagerEntity> getManagerEntitiesBySport(@WebParam(name="idsport") int idSport)
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
					me.setIdManagerEntity(rs.getInt("idmanagerentity"));
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
	public Result<ManagerEntity> getManagerEntitiesByEvent(@WebParam(name="idevent") int idEvent)
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
				ResultSet rs;
				rs = stm.executeQuery("select idfield from reservations where idevent = " + idEvent);
				if(rs.next())
				{
					int idField = rs.getInt("idfield");
					rs = stm.executeQuery("select me.* from managerentity me, fields f where me.idmanagerentity = f.idmanagerentity and f.idfield = " 
								+ idField);
				}
				else
				{
					rs = stm.executeQuery("select * from managerentity me join eventmanagerentities eme on (me.idmanagerentity = eme.idmanagerentity)"
							+ "where idevent = " + idEvent);
				}
					
				ManagerEntity me;
				while(rs.next()){					
					me = new ManagerEntity();
					me.setIdManagerEntity(rs.getInt("idmanagerentity"));
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
	public Result deleteManagerEntity(@WebParam(name="idmanagerentity") int idManagerEntity){
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
				{
					stm.executeUpdate("delete from sportfield where idfield = " + rs.getInt("idfield"));
					stm.executeUpdate("delete from reservations where idfield = " + rs.getInt("idfield"));
				}
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
	
	public static void sendPushNotification(List<String> gcmIds, int idEvent, int type)
	{
		/**
		 * TYPES:
		 *   0: Nou Event
		 *   1: Nou Membre a event
		 *   2: Event Cancelat
		 *   3: Reserva feta
		 * 
		 */
		Sender sender = new Sender(SENDER_ID);
		Message message = new Message.Builder()
								.collapseKey("collapseKEy")
								.timeToLive(30)
								.delayWhileIdle(true)
								.addData("idEvent", String.valueOf(idEvent))
								.addData("type", String.valueOf(type))
								.build();
		
		try {
			// use this for multicast messages.  The second parameter
			// of sender.send() will need to be an array of register ids.
			com.google.android.gcm.server.MulticastResult result = sender.send(message, gcmIds, 1);
			System.out.println("Notificació enviada");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
