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
	public final T get(Class<? extends ResourceDAO> resourceDAO, String id) throws Exception{
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
}
