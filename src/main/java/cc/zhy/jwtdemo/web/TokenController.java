package cc.zhy.jwtdemo.web;

import cc.zhy.jwtdemo.Service.StudentService;
import cc.zhy.jwtdemo.domain.Student;
import cc.zhy.jwtdemo.security.SecurityConfig;
import cc.zhy.jwtdemo.utils.TokenUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Created by zhy on 6/9/17.
 */
@RestController
public class TokenController {
    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenController(StudentService studentService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> getToken(@RequestParam String id,
                                      @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(id, password)
        );
        String jwt = TokenUtils.generateToken(id);
        Map map = Collections.singletonMap("token",jwt);
        return ResponseEntity.ok(map);
    }
}
