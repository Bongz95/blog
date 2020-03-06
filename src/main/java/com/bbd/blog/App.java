package com.bbd.blog;


import com.bbd.blog.exceptions.AccessRevokedException;
import com.bbd.blog.exceptions.InvalidUserCredentialsException;
import com.bbd.blog.exceptions.UsernameException;
import com.bbd.blog.ui.LoginPane;
import com.bbd.blog.ui.StageInstance;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application
{
    public static void main( String[] args ) throws InvalidUserCredentialsException, UsernameException, AccessRevokedException
    {
    	Application.launch(args);
//    		User u = new User(-1, "Richard", "Roe");
//    		Credentials cred = new Credentials("rich","roe23");
//            Auth.register(u, Role.AUTHOR, cred);
    		//Reader author = (Reader)Auth.login("paul_r", "rud123");
//    		Post p = new Post(-1, Category.FINANCE, new Date(), "MONEY MONEY MONEY", "This blog is all about money");
//    		PostService.post(p, author);
    		//author.getPosts().forEach(pst -> System.out.println(pst.getId()+ " date: " + pst.getDatetime()));
    		//CommentService.getComments(author).stream().forEach(c -> System.out.println("Post id: " + c.getPost().getId() + "\nComment Text: " + c.getText()));
      
    }

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		StageInstance.setInstance(stage);
		Scene scene = new Scene(new LoginPane(), 1080,720);
		stage.setScene(scene);
		stage.show();
		
	}
}
