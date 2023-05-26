package com.example;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@javax.persistence.Entity
@Table( name = "programator" )
public class Programator extends com.example.Entity<Long>/*Entity<Long>*/ {
    private Long id;
    private String username;
    private String parola;
    private String functie;

    public Programator() {
        // this form used by Hibernate
    }

    public Programator(String username, String parola, String functie) {
        this.username = username;
        this.parola = parola;
        this.functie = functie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Programator that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getParola(), that.getParola()) && Objects.equals(getFunctie(), that.getFunctie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getParola(), getFunctie());
    }

    @Override
    public String toString() {
        return "Programator{" +
                "username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", functie='" + functie + '\'' +
                '}';
    }
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long aLong) {
        this.id = aLong;
    }

}
