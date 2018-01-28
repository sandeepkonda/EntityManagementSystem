package com.ems.app.manager.api;

import java.util.ArrayList;
import java.util.List;

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
    public final List<T> search() throws Exception {
		List<T> returnedResponses = (List<T>) new ArrayList<User>();
		validateSearch();
		preSearch();
		returnedResponses = postSearch(returnedResponses);
		return returnedResponses;
	}
}
