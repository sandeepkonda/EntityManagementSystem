package com.ems.app.manager.api;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.ems.app.dao.ResourceDAO;
import com.ems.app.object.BaseResource;

@Contract
public interface ResourceManager<T extends BaseResource> {
	List<T> search(Class<? extends ResourceDAO> resourceDAO) throws Exception;

	T get(Class<? extends ResourceDAO> resourceDAO, String id) throws Exception;

	T create(Class<? extends ResourceDAO> resourceDAO, String resource) throws Exception;
	
	T update(Class<? extends ResourceDAO> resourceDAO, String resource, String id) throws Exception;
	
	void delete(Class<? extends ResourceDAO> resourceDAO, String id) throws Exception;
}
