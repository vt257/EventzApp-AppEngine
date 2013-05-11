package com.eventzapp.servlets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.eventzapp.EMF;
import com.eventzapp.Event;
import com.eventzapp.EventFetchParams;
import com.eventzapp.EventFetchParamsEndpoint;
import com.eventzapp.EventMemberData;
import com.eventzapp.MatchType;
import com.eventzapp.User;
import com.eventzapp.UserEndpoint;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;

public class GetFbEventsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String EVENT_FIELDS_TO_GET = "eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone";
	private static final char LIST_SEPARATOR = ',';
	// TODO combine cross-dependent insertions into transactions to have either all of them failing or all of them succeeding

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		UserEndpoint userendpoint = new UserEndpoint();
		getAllEventsFromFb(userendpoint.getUser(Long.parseLong(req.getParameter("uid"))));
	}

	private void getAllEventsFromFb(User user) {
		// TODO these lists should be properly converted to strings
		String friendIds_toUse;
		String likeIds_toUse;
		String friendListIds_toUse;
		Date since;
		Date until;
		if (user.getEventFetchParamsId() != 0 && user.getEventFetchParamsId() != null) {
			EventFetchParams eventFetchParams = new EventFetchParamsEndpoint().getEventFetchParams(user.getEventFetchParamsId());
			friendIds_toUse = StringUtils.join(eventFetchParams.getFriendids_touse(), LIST_SEPARATOR);
			likeIds_toUse = StringUtils.join(eventFetchParams.getLikeids_touse(), LIST_SEPARATOR);
			friendListIds_toUse = StringUtils.join(eventFetchParams.getFriendlistids_touse(), LIST_SEPARATOR);
			// TODO take care of the eventfetchparams with relative since/until somehow..
			since = eventFetchParams.getSince();
			until = eventFetchParams.getUntil();
		} else {
			friendIds_toUse =  StringUtils.join(user.getFriendIds(), LIST_SEPARATOR);
			likeIds_toUse =  StringUtils.join(user.getLikeIds(), LIST_SEPARATOR);
			// TODO changes this to friend_list_ids after adding this field to the user class
			friendListIds_toUse =  StringUtils.join(user.getFriendIds(), LIST_SEPARATOR);
			since = new Date();
			// TODO Do this nicer..
			until = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 7 * 1000));		
		}
		
		FacebookBatcher batcher = new FacebookBatcher(user.getAccesToken());
		batcher.setMaxBatchSize(5);
		batcher.setTimeout(59000);
		// TODO add the part with the friend lists to the query
		// TODO handle the part with the limit.. should somehow take care of request times longer then 60 sec
		// maybe divide them into 100 by 100 parts..
		// TODO start_time filter doesn't work properly, figure this out..
		Later<List<Event>> userEvents = batcher.query(
			    "SELECT " + EVENT_FIELDS_TO_GET +
			    " FROM event WHERE eid IN(SELECT eid FROM event_member WHERE uid IN(SELECT uid2 FROM friend WHERE uid1=" + 
			    		user.getUid()+" AND uid2 IN(" + friendIds_toUse + ")) or uid=" + user.getUid()+")" + 
			    		"OR creator IN(" + likeIds_toUse + ")" + " and start_time>" + since.getTime() + 
			    		" AND start_time<" + until.getTime()/1000 + " limit 100", Event.class);
//		Later<List<Event>> userEvents = batcher.query("SELECT eid,attending_count,unsure_count,not_replied_count,description,location,name,venue,start_time,timezone" + 
//													  " FROM event where eid IN(SELECT eid from event_member WHERE uid=" + user.getUid()+")", Event.class);
		List<Long> eids = new ArrayList<Long>();
		for (Event event : userEvents.get()) {
			// adding this users id to the event so that he/she has access to it later
			event.setModified(new Date());
			event.insertOrUpdateEvent(user.getUid());
			eids.add(event.getEid());
			FindEventMatches(user, event);
		}
		// TODO the order of getEventMembers and FindEventMatches is wrong.. but one has to get the ids somehow
		// don't want to do another loop for this.. think of a smart way and chagne the order
		getEventMembers(user, eids);
		return;
	}
	
	private void FindEventMatches(User user, Event event) {
		// TODO this should be called on the server only once and removed afterwards
		user.insertDefaultMatchTypes();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		EntityManager mgr = EMF.get().createEntityManager();
		
		Key userKey = KeyFactory.createKey(User.class.getSimpleName(), user.getUid());
		
		Query matchTypeQuery = new Query(MatchType.class.getSimpleName()).setAncestor(userKey);
		List<Entity> matchTypes = datastore.prepare(matchTypeQuery)
                .asList(FetchOptions.Builder.withDefaults());
		for (Entity matchType : matchTypes) {
			mgr.find(MatchType.class, matchType.getKey()).match(user, event);
		}
		
		// TODO
		// get all the matchtypes that belong to the given user in the db
		// for each matchtype do tha matching based on matchmethod and eventmatch
		// insert the matches into the db
	}
	
	private void getEventMembers(User user, List<Long> eids) {
		String eidsString = StringUtils.join(eids, LIST_SEPARATOR);
		
		FacebookBatcher batcher = new FacebookBatcher(user.getAccesToken());
		batcher.setMaxBatchSize(5);
		batcher.setTimeout(59000);
		// TODO when the eidsString is too long, e.g. fetching 100 events at a time, the request takes too much time
		// and it terminates.. devide this up to smaller request and set each of them as a task?? It would soon be time
		// to connect all this with the front-end to understand how should one proceed
		// TODO in this query only one eid is used now for fetching event members
		Later<List<EventMemberData>> eventMemberDataList = batcher.query("select uid, eid, rsvp_status from event_member where eid IN("+
				Long.toString(eids.get(1))+") and uid IN (SELECT uid2 from friend where uid1 = " + 
																		user.getUid()+")", EventMemberData.class);
		for (EventMemberData eventMemberData : eventMemberDataList.get()) {
			eventMemberData.setId(eventMemberData.getEid() + "x" + eventMemberData.getUid());
			// adding this eventmember to the datastore
			eventMemberData.insertOrUpdateEventMemberData(user.getUid());
		}		
	}

}
