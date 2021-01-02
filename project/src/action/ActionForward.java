package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

public class ActionForward {
	private boolean isRedirect = false;
	private String nextPath = null;	//이동할 다음 화면
	
	
	// Forward 사용 여부 , false이면 forward  사용
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getNextPath() {
		return nextPath;
	}
	public void setNextPath(String nextPath) {
		this.nextPath = nextPath;
	}
	
	public void execute(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		if(isRedirect) {
			response.sendRedirect(nextPath);
		}else {
			RequestDispatcher rdis = request.getRequestDispatcher(nextPath);
			rdis.forward(request, response);
		}
	}
	
}
