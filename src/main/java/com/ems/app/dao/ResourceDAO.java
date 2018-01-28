package com.ems.app.dao;

import java.util.List;

import com.ems.app.object.User;

public abstract class ResourceDAO {
	public abstract List<User> search();
	public abstract User get(String id);
	public abstract User create(String resource);
	public abstract User update(String resource, String id);
	public abstract void delete(String id);
}
