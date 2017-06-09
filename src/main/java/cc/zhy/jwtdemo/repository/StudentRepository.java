package cc.zhy.jwtdemo.repository;

import cc.zhy.jwtdemo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by zhy on 6/8/17.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(String id);
}
