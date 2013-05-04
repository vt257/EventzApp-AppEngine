package com.eventzapp;

import java.util.List;

import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;

public class EventUtils {
	public static void getAllEvents(User user, EventFetchParams eventfetchparams) {
		FacebookBatcher batcher = new FacebookBatcher(user.getAccesToken());
		batcher.setMaxBatchSize(5);
		batcher.setTimeout(59000);
		Later<List<Event>> userEvents = batcher.query(
			    "SELECT eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone " + 
			    "FROM event WHERE eid IN(SELECT eid from event_member WHERE uid IN(SELECT uid2 FROM friend WHERE uid1=" + user.getUid()+") or uid=" + user.getUid()+")" + 
			    " and start_time>now() limit 100", Event.class);
//		Later<List<Event>> userEvents = batcher.query("SELECT eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone" + 
//													  " FROM event where eid IN(SELECT eid from event_member WHERE uid=" + user.getUid()+")", Event.class);
		
		EventEndpoint eventendpoint = new EventEndpoint();
		for (Event event : userEvents.get()) {
			eventendpoint.insertEvent(event);
		}
		return;
	}
}
