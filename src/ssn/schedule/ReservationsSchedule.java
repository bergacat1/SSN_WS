package ssn.schedule;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class ReservationsSchedule {

	@Schedule(second="*/60", minute="*/1",hour="*", persistent=false)
	public void manageReservationsScheduled(){
		System.out.println("HeeeY");
	}
}
