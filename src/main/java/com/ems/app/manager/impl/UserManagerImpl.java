package com.ems.app.manager.impl;

import java.util.List;

import org.jvnet.hk2.annotations.Service;

import com.ems.app.manager.api.AbstractResourceManager;
import com.ems.app.manager.api.UserManager;
import com.ems.app.object.User;

@Service
public class UserManagerImpl extends AbstractResourceManager<User> implements UserManager {
	@Override
	protected void validateSearch() throws Exception {
	}
	
	@Override
	protected void preSearch() throws Exception {
	}
	
	@Override
	protected List<User> postSearch(List<User> returnedResponses)
			throws Exception {
		return returnedResponses;
	}

}
