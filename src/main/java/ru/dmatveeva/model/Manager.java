package ru.dmatveeva.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Manager.BY_LOGIN, query = "SELECT m FROM Manager m WHERE m.login=?1")
})
@Entity
@Table(name = "managers")
public class Manager extends AbstractBaseEntity {

    public static final String BY_LOGIN = "Manager.getByLogin";

    public Manager() {
    }

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @ManyToMany(mappedBy = "manager")
    @Fetch(FetchMode.JOIN)
    private List<Enterprise> enterprise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Enterprise> getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(List<Enterprise> enterprise) {
        this.enterprise = enterprise;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
