package com.example;

import java.util.Objects;

public class Bug extends Entity<Long>{
    private String denumire;
    private String descriere;
    private BugRisk risk;
    private BugStatus status;

    public Bug() {
        // this form used by Hibernate
    }

    public Bug(String denumire, String descriere, BugRisk bugRisk, BugStatus bugStatus) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.risk = bugRisk;
        this.status = bugStatus;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public BugRisk getBugRisk() {
        return risk;
    }

    public void setBugRisk(BugRisk bugRisk) {
        this.risk = bugRisk;
    }

    public BugStatus getBugStatus() {
        return status;
    }

    public void setBugStatus(BugStatus bugStatus) {
        this.status = bugStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bug bug)) return false;
        return Objects.equals(getDenumire(), bug.getDenumire()) && Objects.equals(getDescriere(), bug.getDescriere()) && getBugRisk() == bug.getBugRisk() && getBugStatus() == bug.getBugStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDenumire(), getDescriere(), getBugRisk(), getBugStatus());
    }

    @Override
    public String toString() {
        return "Bug{" +
                "denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", bugRisk=" + risk +
                ", bugStatus=" + status +
                '}';
    }
}
