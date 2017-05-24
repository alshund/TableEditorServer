package studentDataBase;

import java.io.Serializable;

/**
 * Created by shund on 09.04.2017.
 */
public class Student implements Serializable {
    private String firstName;
    private String patronymic;
    private String surname;
    private Mother mother;
    private Father father;
    private int brothersAmount;
    private int sistersAmount;
    private static final long serialVersionUID = 6529685098267757690L;
    public Student() {
        father = new Father();
        mother = new Mother();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Mother getMother() {
        return mother;
    }

    public void setMother(Mother mother) {
        this.mother = mother;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    public int getBrothersAmount() {
        return brothersAmount;
    }

    public void setBrothersAmount(int numberOfBrothers) {
        this.brothersAmount = numberOfBrothers;
    }

    public int getSistersAmount() {
        return sistersAmount;
    }

    public void setSistersAmount(int numberOfSisters) {
        this.sistersAmount = numberOfSisters;
    }
}
