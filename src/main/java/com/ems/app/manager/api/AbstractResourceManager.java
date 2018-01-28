package com.ems.app.manager.api;

import java.util.ArrayList;
import java.util.List;

import com.ems.app.dao.ResourceDAO;
import com.ems.app.object.BaseResource;
import com.ems.app.object.User;

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
}
