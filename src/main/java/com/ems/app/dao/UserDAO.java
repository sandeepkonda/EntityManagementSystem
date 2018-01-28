package com.ems.app.dao;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ems.app.object.User;
import com.ems.app.util.SessionUtil;
public class UserDAO extends ResourceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> search() {
		Session session = SessionUtil.getSession();        
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(User.class);

		List<User> usersList =  criteria.list();

		tx.commit();
		session.close();
		return usersList;
	}

	@Override
	public User get(String id) {
		Session session = SessionUtil.getSession();        
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(User.class);

		User user =  (User) criteria.add(Restrictions.eq("id", new Integer(id))).uniqueResult();

		tx.commit();
		session.close();
		return user;
	}

	@Override
	public User create(String resource) {
		User user =  null;

		try {
			user = new ObjectMapper().readValue(resource, User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Session session = SessionUtil.getSession();        
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user); 
		tx.commit();
		session.close();

		return user;
	}

	@Override
	public User update(String resource, String id) {
		User user =  null;

		try {
			user = new ObjectMapper().readValue(resource, User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		user.setId(new Integer(id));
		Session session = SessionUtil.getSession();        
		Transaction tx = session.beginTransaction();
		session.update(user); 
		tx.commit();
		session.close();

		return user;
	}
}
