package com.eventzapp;

import java.util.Date;
import java.util.List;

import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;

public class EventUtils {
	private static final String EVENT_FIELDS_TO_GET = "eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone";
	
	public static void getAllEventsFromFb(User user) {
		// TODO these lists should be properly converted to strings
		List<Long> friendIds_toUse;
		List<Long> likeIds_toUse;
		List<Long> friendListIds_toUse;
		Date since;
		Date until;
		if (user.getEventfatchparams_id() != 0 && user.getEventfatchparams_id() != null) {
			EventFetchParams eventFetchParams = new EventFetchParamsEndpoint().getEventFetchParams(user.getEventfatchparams_id());
			friendIds_toUse = eventFetchParams.getFriendids_touse();
			likeIds_toUse = eventFetchParams.getLikeids_touse();
			friendListIds_toUse = eventFetchParams.getFriendlistids_touse();
			// TODO take care of the eventfetchparams with relative since/until somehow..
			since = eventFetchParams.getSince();
			until = eventFetchParams.getUntil();
		} else {
			friendIds_toUse = user.getFriendids();
			likeIds_toUse = user.getLikeids();
			// TODO changes this to friend_list_ids after adding this field to the user class
			friendListIds_toUse = user.getFriendids();
			since = new Date();
			// TODO get a date one week ahead to get events for 1 week
			until = new Date(1000);			
		}
		
		FacebookBatcher batcher = new FacebookBatcher(user.getAccestoken());
		batcher.setMaxBatchSize(5);
		batcher.setTimeout(59000);
		// TODO add the part with the friend lists to the query
		// TODO handle the part with the limit.. should somehow take care of request times longer then 60 sec
		// maybe divide them into 100 by 100 parts..
		Later<List<Event>> userEvents = batcher.query(
			    "SELECT " + EVENT_FIELDS_TO_GET +
			    " FROM event WHERE eid IN(SELECT eid FROM event_member WHERE uid IN(SELECT uid2 FROM friend WHERE uid1=" + 
			    		user.getUid()+" AND uid2 IN(" + friendIds_toUse + ")) or uid=" + user.getUid()+")" + 
			    		"OR ceator IN(" + likeIds_toUse + ")" + " and start_time>" + since + 
			    		" AND start_time<" + until + " limit 100", Event.class);
//		Later<List<Event>> userEvents = batcher.query("SELECT eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone" + 
//													  " FROM event where eid IN(SELECT eid from event_member WHERE uid=" + user.getUid()+")", Event.class);
		
		EventEndpoint eventendpoint = new EventEndpoint();
		for (Event event : userEvents.get()) {
			eventendpoint.insertEvent(event);
			FindEventMatches(user, event);
		}
		return;
	}
	
	public static void FindEventMatches(User user, Event event) {
		// TODO
		// get all the matchtypes that belong to the given user in the db
		// for each matchtype do tha matching based on matchmethod and eventmatch
		// insert the matches into the db
	}
}
