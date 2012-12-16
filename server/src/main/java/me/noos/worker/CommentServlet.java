package me.noos.worker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.noos.engine.comment.Comment;
import me.noos.model.Model;

import org.json.simple.JSONObject;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = -1575801646959296085L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Model model = Model.getInstance();
		
		String user = req.getParameter("user");
		String comment = req.getParameter("comment");
		String checkString = req.getParameter("check");
		boolean isCheck = false;
		
		if (checkString != null) {
			isCheck = Integer.parseInt(req.getParameter("check")) == 1;
		}
		
		if (isCheck) {
			JSONObject obj = new JSONObject();
			for (Comment c : model.getComments()) {
				obj.put(c.user, c.comment);
			}
			
			PrintWriter out = resp.getWriter();
			out.println(obj);
		} else {
			model.getComments().add(new Comment(model.getCommentCounter() + "-" + user, comment));
			model.setCommentCounter(model.getCommentCounter() + 1);
			System.out.println("User \"" + user + "\" made the comment \"" + comment + "\"");
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
}
