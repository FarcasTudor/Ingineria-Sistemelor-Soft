package com.example;

import java.util.Objects;

public class Programator extends Entity<Long> {
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
}
