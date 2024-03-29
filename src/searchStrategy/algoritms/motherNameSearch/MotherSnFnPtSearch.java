package searchStrategy.algoritms.motherNameSearch;

import searchStrategy.SearchStrategy;
import studentDataBase.Mother;
import studentDataBase.Student;

import java.io.Serializable;

/**
 * Created by shund on 08.05.2017.
 */
public class MotherSnFnPtSearch implements SearchStrategy, Serializable {
    private String surname;
    private String firstName;
    private String patronymic;

    public MotherSnFnPtSearch(String surname, String firstName, String patronymic) {
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    @Override
    public boolean execute(Student student) {
        Mother mother = student.getMother();

        String motherSn = mother.getSurname();
        String motherFn = mother.getFirstName();
        String motherPt = mother.getPatronymic();

        return surname.equals(motherSn) && firstName.equals(motherFn) && patronymic.equals(motherPt);
    }
}
