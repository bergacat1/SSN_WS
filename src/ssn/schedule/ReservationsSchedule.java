package ssn.schedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import ssn.beans.Event;
import ssn.beans.Reservation;
import ssn.ws.SSNWS;

@Singleton
public class ReservationsSchedule {

	@Schedule(second="*/20", minute="*/1",hour="*", persistent=false)
	public void manageReservationsScheduled(){
		System.out.println("HeeeY");
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
				String sql;
				//Cancelar passada hora limit
				sql = "select e.idevent from events e where limitdatetime < current_timestamp and not exists "
						+ "(select * from reservations r where r.idevent = e.idevent) and canceled = false ";
				rs = stm.executeQuery(sql);
				
				List<Integer> eventsToCancel = new ArrayList<>();
				while(rs.next())
					eventsToCancel.add(rs.getInt("idevent"));
				for (Integer idEvent : eventsToCancel) 
					sendEventNotification(stm, idEvent, 2);
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("ReservationSchedule::  CANCELED EVENTS: %d", eventsToCancel.size()));
				
				sql = "update events e set canceled = true where limitdatetime < current_timestamp and not exists "
						+ "(select * from reservations r where r.idevent = e.idevent) and canceled = false";
				stm.executeUpdate(sql);
				
				//RESERVAR
				sql = "select e.idevent from events e  "
						+ "where e.canceled = false and (select count(*) from eventusers eu where eu.idevent = e.idevent) >= e.minplayers "
						+ "and not exists (select * from reservations r where r.idevent = e.idevent)";
				rs = stm.executeQuery(sql);		
				List<Integer> eventsToReserve = new ArrayList<>();
				while(rs.next())
					eventsToReserve.add(rs.getInt("idevent"));
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("ReservationSchedule::  Events to Reserve: %d", eventsToReserve.size()));
				
				for (Integer idEvent : eventsToReserve) 
				{
					Event e = getEventById(idEvent);
					sql = "select f.idfield from sportfield sf, fields f, managerentity me where sf.idsport = " + e.getIdSport() +
							" and sf.idfield = f.idfield and f.idmanagerentity = me.idmanagerentity and (me.city = '" + e.getCity() + "' or "
							+ "((" + e.getLatitude() + " - me.latitude)^2 + (" + e.getLongitude() + " - me.longitude)^2) < " + e.getRange() + "^2)" 
							+ "and sf.hourprice <= " + e.getMaxPrice() + "/(select count(*) from eventusers where idevent = " + idEvent 
							+ " and not exists (select * from reservations r where r.idfield = sf.idfield and startdate < '" 
							+ SSNWS.df.format(new Date(e.getEndDate())) +
							"' and enddate > '" + SSNWS.df.format(new Date(e.getStartDate())) + "') order by sf.hourprice asc";
					rs = stm.executeQuery(sql);
					boolean reserved = false;
					while (rs.next() && !reserved){
						Reservation r = new Reservation();
						r.setIdEvent(idEvent);
						r.setIdField(rs.getInt("idfield"));
						r.setStartDate(e.getStartDate());
						r.setEndDate(e.getEndDate());
						r.setConfirmed(false);
						r.setType(0);
						if (addReservation(r))
							reserved = true;
					}
					if (reserved)
						sendEventNotification(stm, idEvent, 3);
				}
				connection.close();
				stm.close();
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void sendEventNotification(Statement stm, Integer idEvent, int type)
			throws SQLException {
		ResultSet rs;
		String sql;
		List<String> usersToNotify;
		sql = "select u.gcmid from users u join eventusers eu on (u.iduser = eu.iduser) where u.gcmid <> '' and eu.idevent = " + idEvent;
		rs = stm.executeQuery(sql);
		usersToNotify = new ArrayList<>();
		while (rs.next())
			if (rs.getString("gcmid") != "")
				usersToNotify.add(rs.getString("gcmid"));
		if (!usersToNotify.isEmpty())
			SSNWS.sendPushNotification(usersToNotify, idEvent, type);
	}

	public Event getEventById(int idEvent)
	{
		Event event = new Event();
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
				
				if(rs.next()){					
					event = new Event();
					event.setIdEvent(rs.getInt("idevent"));
					event.setIdCreator(rs.getInt("idcreator"));
					event.setIdSport(rs.getInt("idsport"));
					event.setSportName(rs.getString("name"));
					event.setStartDate(SSNWS.df.parse(rs.getString("startdatetime")).getTime());
					event.setEndDate(SSNWS.df.parse(rs.getString("enddatetime")).getTime());
					event.setCity(rs.getString("city"));
					event.setLatitude(rs.getDouble("latitude"));
					event.setLongitude(rs.getDouble("longitude"));
					event.setRange(rs.getInt("range"));
					event.setMinPlayers(rs.getInt("minplayers"));
					event.setMaxPlayers(rs.getInt("maxplayers"));
					event.setMaxPrice(rs.getDouble("maxprice"));
					event.setLimitDate(SSNWS.df.parse(rs.getString("limitdatetime")).getTime());
				}
	
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return event;
	}
	
	public boolean addReservation(Reservation reservation)
	{
		boolean reserved = false;
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
				String verificationQuery = "select * from reservations where idfield = " + reservation.getIdField() + " and startdate < '" 
						+ SSNWS.df.format(new Date(reservation.getEndDate())) + "' and enddate > '" 
						+ SSNWS.df.format(new Date(reservation.getStartDate())) + "'";
				ResultSet rs = stm.executeQuery(verificationQuery);
				if(!rs.next()){
					String sql = "insert into reservations (" + (reservation.getIdEvent() > 0 ? "idevent," : "") 
							+ "idfield, startdate, enddate, comfirmed, type) values "
							+ "(" + (reservation.getIdEvent() > 0 ? reservation.getIdEvent() + "," : "") 
							+ reservation.getIdField() + ",'" + SSNWS.df.format(new Date(reservation.getStartDate())) + "','" 
							+ SSNWS.df.format(new Date(reservation.getEndDate())) + "'," + (reservation.isConfirmed() ? "'1'" : "'0'") 
							+ "," + reservation.getType() + ")";
					stm.executeUpdate(sql);
					reserved = true;
					Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("ReservationSchedule - RESERVAT::  Field: %d  Event:", reservation.getIdField(), reservation.getIdEvent()));
				}else
					Logger.getLogger(this.getClass().getName()).log(Level.INFO, String.format("ReservationSchedule - IMPOSSIBLE RESERVAR::  Field: %d  Event:", reservation.getIdField(), reservation.getIdEvent()));
					
				connection.close();
				stm.close();
			}   
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return reserved;
	}
}
