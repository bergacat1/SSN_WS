package ssn.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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

@WebService
public class SSNWS {
	
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
	
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
	public Result<User> getUsersByEvent(int idEvent)
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
	public Result<Integer> createEvent(Event event)
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
				String sql = "insert into events (idcreator, idsport, minplayers, maxplayers, startdatetime, enddatetime, city, latitude,"
						+ " longitude, range, maxprice) values "
						+ "(" + event.getIdCreator() + "," + event.getIdSport() + "," + (event.getMinPlayers() == 0 ? sportMinPlayers : event.getMinPlayers()) 
						+ "," + (event.getMaxPlayers() == 0 ? sportMaxPlayers : event.getMaxPlayers()) + ",'" + df.format(new Date(event.getStartDate()))
						+ "','" + df.format(new Date(event.getEndDate())) + "','" + event.getCity() + "'," + event.getLatitude() + "," + event.getLongitude() 
						+ "," + event.getRange() + "," + event.getMaxPrice() + ")";
				stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				rs = stm.getGeneratedKeys();
				rs.next();
				Integer idEvent = rs.getInt(1);
				result.addData(idEvent);
				
				for (int i = 0; i < event.getManagerEntities().size(); i++) {
					stm.executeUpdate("insert into eventmanagerentities values (" + idEvent + "," + event.getManagerEntities().get(i) + ")");
				}
				
				rs = stm.executeQuery("select type from users where iduser = " + event.getIdCreator());
				
				if(rs.next() && rs.getInt("type") == 0)
					stm.executeUpdate("insert into eventusers values (" + idEvent + "," + event.getIdCreator() + ")");
				
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
	public Result<Event> getEvents()
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
				ResultSet rs = stm.executeQuery("select * from events ");
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setStartDate(rs.getDate("startdatetime").getTime());
					e.setEndDate(rs.getDate("enddatetime").getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
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
	public Result<Event> getEventsByFilters(int idSport, int minPlayers, double maxPrice, Date fromDate, Date toDate)
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
				StringBuilder sb = new StringBuilder();
				sb.append("select * from events e where 1=1");
				if(idSport > 0)
					sb.append(" and e.idsport = " + idSport);
				if(minPlayers > 0)
					sb.append(" and (select count(*) from eventusers eu where eu.idevent = e.idevent) >= " + minPlayers);
				if(maxPrice > 0)
					sb.append(" and e.maxprice <= " + maxPrice);
				if(fromDate != null)
					sb.append(" and e.startdatetime >= '" + df.format(fromDate) + "'");
				if(toDate != null)
					sb.append(" and e.startdatetime <= '" + df.format(toDate) + "'");
				ResultSet rs = stm.executeQuery(sb.toString());
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setStartDate(rs.getDate("startdatetime").getTime());
					e.setEndDate(rs.getDate("enddatetime").getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
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
	public Result<Event> getEventById(int idEvent)
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
				ResultSet rs = stm.executeQuery("select * from events where idevent = " + idEvent);
				
				Event e;
				if(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setStartDate(rs.getDate("startdatetime").getTime());
					e.setEndDate(rs.getDate("enddatetime").getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
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
	public Result<Event> getEventsByUser(int idUser)
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
				ResultSet rs = stm.executeQuery("select * from events e join eventusers eu on (e.idevent = eu.idevent) where e.startdatetime > current_timestamp and eu.iduser = " + idUser);
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setStartDate(rs.getDate("startdatetime").getTime());
					e.setEndDate(rs.getDate("enddatetime").getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
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
	public Result<Event> getEventsHistoryByUser(int idUser)
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
				ResultSet rs = stm.executeQuery("select * from events e join eventusers eu on (e.idevent = eu.idevent) where e.startdatetime <= current_timestamp and eu.iduser = " + idUser);
				
				Event e;
				while(rs.next()){					
					e = new Event();
					e.setIdEvent(rs.getInt("idevent"));
					e.setIdCreator(rs.getInt("idcreator"));
					e.setIdSport(rs.getInt("idsport"));
					e.setStartDate(rs.getDate("startdatetime").getTime());
					e.setEndDate(rs.getDate("enddatetime").getTime());
					e.setCity(rs.getString("city"));
					e.setLatitude(rs.getDouble("latitude"));
					e.setLongitude(rs.getDouble("longitude"));
					e.setRange(rs.getInt("range"));
					e.setMinPlayers(rs.getInt("minplayers"));
					e.setMaxPlayers(rs.getInt("maxplayers"));
					e.setMaxPrice(rs.getDouble("maxprice"));
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
	public Result joinEvent(int idUser, int idEvent)
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
				String sql = "insert into eventusers values "
						+ "(" + idEvent + "," + idUser + ")";
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
	public Result leaveEvent(int idUser, int idEvent)
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
	public Result<Integer> reportUser(Report r)
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
				String sql = "insert into reports ( idreporttype, " + (r.getIdUser() > 0 ? "iduser," : "idfield,") + "idreporter, date, comment) values "
						+ "(" + r.getIdReportType() + "," + (r.getIdUser() > 0 ? r.getIdUser() : r.getIdField()) + "," + r.getIdReporter() 
						+ ",current_timestamp,'" + r.getComment() + "')";
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
	public Result<ManagerEntity> getManagerEntitiesByEvent(int idEvent)
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
				ResultSet rs = stm.executeQuery("select * from managerentity me join eventmanagerentities eme on (me.idmanagerentity = eme.idmanagerentity)"
						+ "where idevent = " + idEvent);
				
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
