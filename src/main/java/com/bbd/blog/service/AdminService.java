package com.bbd.blog.service;

import com.bbd.blog.exceptions.OperationDeniedException;
import com.bbd.blog.model.Admin;
import com.bbd.blog.model.User;
import com.bbd.blog.repository.DB;

public class AdminService {

	public static void revokeAccess(User user) throws OperationDeniedException {
		if(!(Auth.getActiveUser() instanceof Admin)) throw new OperationDeniedException("Type of user not allowed to perform this operation");
		
		String sql = String.format("UPDATE CREDENTIALS "
				+ "SET access_granted = 'F' "
				+ "WHERE user_id = '%d'", user.getId());
		
		DB.executeUpdate(sql);
	}
	
	public static void grantAccess(User user) throws OperationDeniedException {
		if(!(Auth.getActiveUser() instanceof Admin)) throw new OperationDeniedException("Type of user not allowed to perform this operation");
		String sql = String.format("UPDATE CREDENTIALS "
				+ "SET access_granted = 'T' "
				+ "WHERE user_id = '%d'", user.getId());
		
		DB.executeUpdate(sql);
	}
	

}
