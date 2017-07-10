package cc.zhy.jwtdemo.Service;

import cc.zhy.jwtdemo.domain.Student;
import cc.zhy.jwtdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhy on 6/8/17.
 */
@Service
public class StudentService implements UserDetailsService{
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> list = Collections.singletonList((
                new SimpleGrantedAuthority("ROLE_USER")
        ));
        return new User(student.getId(),student.getPassword(),list);
    }
}
