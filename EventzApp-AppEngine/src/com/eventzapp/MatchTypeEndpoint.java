package com.eventzapp;

import com.eventzapp.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "matchtypeendpoint", namespace = @ApiNamespace(ownerDomain = "eventzapp.com", ownerName = "eventzapp.com", packagePath = ""))
public class MatchTypeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listMatchType")
	public CollectionResponse<MatchType> listMatchType(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<MatchType> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from MatchType as MatchType");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<MatchType>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (MatchType obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<MatchType> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getMatchType")
	public MatchType getMatchType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		MatchType matchtype = null;
		try {
			matchtype = mgr.find(MatchType.class, id);
		} finally {
			mgr.close();
		}
		return matchtype;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param matchtype the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertMatchType")
	public MatchType insertMatchType(MatchType matchtype) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsMatchType(matchtype)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(matchtype);
		} finally {
			mgr.close();
		}
		return matchtype;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param matchtype the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateMatchType")
	public MatchType updateMatchType(MatchType matchtype) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsMatchType(matchtype)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(matchtype);
		} finally {
			mgr.close();
		}
		return matchtype;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "removeMatchType")
	public MatchType removeMatchType(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		MatchType matchtype = null;
		try {
			matchtype = mgr.find(MatchType.class, id);
			mgr.remove(matchtype);
		} finally {
			mgr.close();
		}
		return matchtype;
	}

	private boolean containsMatchType(MatchType matchtype) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			MatchType item = mgr.find(MatchType.class, matchtype.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
