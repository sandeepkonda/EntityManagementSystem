package com.ems.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}