package cc.zhy.jwtdemo.security;

import cc.zhy.jwtdemo.Service.StudentService;
import cc.zhy.jwtdemo.domain.Student;
import cc.zhy.jwtdemo.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhy on 6/8/17.
 */
//@Component
public class JwtFilter implements Filter {

    private StudentService studentService;

    public JwtFilter(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String compactJws = req.getHeader("Authorization");
        if (compactJws != null && TokenUtils.validateToken(compactJws)) {
            System.out.println("in");
            String id = TokenUtils.getIdFromToken(compactJws);
            UserDetails userDetails = studentService.loadUserByUsername(id);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
