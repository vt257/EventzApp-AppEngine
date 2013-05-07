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

@Api(name = "keywordendpoint", namespace = @ApiNamespace(ownerDomain = "eventzapp.com", ownerName = "eventzapp.com", packagePath = ""))
public class KeywordEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listKeyword")
	public CollectionResponse<Keyword> listKeyword(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Keyword> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Keyword as Keyword");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Keyword>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Keyword obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Keyword> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getKeyword")
	public Keyword getKeyword(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Keyword keyword = null;
		try {
			keyword = mgr.find(Keyword.class, id);
		} finally {
			mgr.close();
		}
		return keyword;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param keyword the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertKeyword")
	public Keyword insertKeyword(Keyword keyword) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsKeyword(keyword)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(keyword);
		} finally {
			mgr.close();
		}
		return keyword;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param keyword the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateKeyword")
	public Keyword updateKeyword(Keyword keyword) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsKeyword(keyword)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(keyword);
		} finally {
			mgr.close();
		}
		return keyword;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "removeKeyword")
	public Keyword removeKeyword(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Keyword keyword = null;
		try {
			keyword = mgr.find(Keyword.class, id);
			mgr.remove(keyword);
		} finally {
			mgr.close();
		}
		return keyword;
	}

	private boolean containsKeyword(Keyword keyword) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Keyword item = mgr.find(Keyword.class, keyword.getId());
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
