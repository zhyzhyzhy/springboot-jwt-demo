package cc.zhy.jwtdemo.web;

import cc.zhy.jwtdemo.Service.StudentService;
import cc.zhy.jwtdemo.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhy on 6/8/17.
 */
@RestController
public class StudentController {

    private StudentService studentService;
    private PasswordEncoder encoder;

    @Autowired
    public StudentController(StudentService studentService, PasswordEncoder encoder) {
        this.studentService = studentService;
        this.encoder = encoder;
    }


    @GetMapping("/users")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        studentService.save(student);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/profile")
    public Student profile(@AuthenticationPrincipal User user) {
        return studentService.findById(user.getUsername());
    }
}
