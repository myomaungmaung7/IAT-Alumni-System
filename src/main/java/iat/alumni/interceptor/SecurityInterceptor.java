package iat.alumni.interceptor;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import iat.alumni.controller.exception.UnauthorizedException;
import iat.alumni.model.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SecurityInterceptor implements HandlerInterceptor{
	@Override
	  public boolean preHandle(HttpServletRequest request,
	                           HttpServletResponse response, Object handler)
	                               throws IOException,UnauthorizedException{
	    System.out.println("Intercepting " + request.getRequestURI());
	    
	    String uri = request.getRequestURI();
	    if (uri.startsWith("/css/") || uri.startsWith("/image/") || uri.startsWith("/event-photos/") || uri.startsWith("/article-photos/") || uri.startsWith("/user-photos/") || uri.startsWith("/request/") || uri.startsWith("/forgetpassword/") || uri.startsWith("/") || uri.startsWith("/userHome")) {
	      return true;
	    }
	    
	    if (uri.equalsIgnoreCase("/") || uri.equalsIgnoreCase("/userHome")
	              || uri.equalsIgnoreCase("/login")
	              || uri.equalsIgnoreCase("/request")
	              || uri.equalsIgnoreCase("/forgetpassword")
	    	      || uri.equalsIgnoreCase("/emailsent")
	    	      || uri.equalsIgnoreCase("/save/request")){
	      return true;
	    }
	    
	    if (uri.startsWith("/login/")) {
	      return true;
	    }
	    
	    HttpSession session = request.getSession();
	    UserSession userSession = (UserSession) session.getAttribute("userSession");
	    if (userSession == null) {
	      response.sendRedirect("/login");
	      return false;
	    }
	    
	    List<String> userRoles = userSession.getUser().getRoles();
	    
	    if (uri.startsWith("/admin") && !userRoles.contains("ADMIN")) {
	      throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/alumni") && !userRoles.contains("ALUMNI")) {
	      throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/user/list") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/manageArticle") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/reviewarticle") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/reviewReport") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/event/list") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/reviewPassword") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/user/create") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/admin/user/save") && !userRoles.contains("ADMIN")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/alumni/event/create") && !userRoles.contains("ALUMNI")) {
	        throw new UnauthorizedException();
	    }
	    
	    if (uri.startsWith("/alumni/event/post") && !userRoles.contains("ALUMNI")) {
	        throw new UnauthorizedException();
	    }

	    return true;
	  }
}
