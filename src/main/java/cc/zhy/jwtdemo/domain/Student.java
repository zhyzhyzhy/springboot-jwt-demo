package cc.zhy.jwtdemo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by zhy on 6/8/17.
 */
@Entity
@Getter
@Setter
public class Student  {
    @Id
    private String id;
    private String name;
    private String password;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
