package com.ems.app.manager.api;

import java.util.List;

import com.ems.app.dao.ResourceDAO;
import com.ems.app.object.BaseResource;

public abstract class AbstractResourceManager<T extends BaseResource> implements ResourceManager<T>  {

	protected void validateSearch() throws Exception {
		// default is no-op
	}

	protected void preSearch() throws Exception {
		// default is no-op
	}

	protected List<T> postSearch(List<T> returnedResponses)
			throws Exception {
		return returnedResponses;
	}

	@Override
	public final List<T> search(Class<? extends ResourceDAO> resourceDAO) throws Exception {
		List<T> returnedResponses = null;
		validateSearch();
		preSearch();
		Object o =  resourceDAO.getMethod("search").invoke(resourceDAO.newInstance());
		if(o instanceof List<?>) {
			returnedResponses = (List<T>) o;	
		}

		returnedResponses = postSearch(returnedResponses);
		return returnedResponses;
	}

	@Override
	public final T get(Class<? extends ResourceDAO> resourceDAO, String id) throws Exception {
		T returnedResponses = null;
		validateGet();
		preGet();
		returnedResponses =  (T) resourceDAO.getMethod("get", String.class).invoke(resourceDAO.newInstance(), id);

		returnedResponses = postGet(returnedResponses);
		return returnedResponses;
	}

	protected T postGet(T returnedResponses) {
		// TODO Auto-generated method stub
		return returnedResponses;
	}

	protected void preGet() {
		// TODO Auto-generated method stub

	}

	protected void validateGet() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public final T create(Class<? extends ResourceDAO> resourceDAO, String resource) throws Exception {
		T returnedResponses = null;
		validateCreate();
		preCreate();
		returnedResponses =  (T) resourceDAO.getMethod("create", String.class).invoke(resourceDAO.newInstance(), resource);

		returnedResponses = postCreate(returnedResponses);
		return returnedResponses;
	}

	private T postCreate(T returnedResponses) {
		// TODO Auto-generated method stub
		return returnedResponses;
	}

	private void preCreate() {
		// TODO Auto-generated method stub
		
	}

	private void validateCreate() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public final T update(Class<? extends ResourceDAO> resourceDAO, String resource, String id) throws Exception {
		T returnedResponses = null;
		validateUpdate();
		preUpdate();
		returnedResponses =  (T) resourceDAO.getMethod("update", String.class, String.class).
				invoke(resourceDAO.newInstance(), resource, id);

		returnedResponses = postUpdate(returnedResponses);
		return returnedResponses;
	}

	private T postUpdate(T returnedResponses) {
		// TODO Auto-generated method stub
		return returnedResponses;
	}

	private void preUpdate() {
		// TODO Auto-generated method stub
		
	}

	private void validateUpdate() {
		// TODO Auto-generated method stub
		
	}
}
