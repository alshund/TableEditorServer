package studentDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shund on 21.05.2017.
 */
public class SearchResult {
    private List<Student> studentList;

    public SearchResult(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List <Student> getPage(int currentPage, int recodesNumber) {
        List<Student> page = new ArrayList<Student>();
        int beginIndex = (currentPage - 1) * (recodesNumber);
        int endIndex = beginIndex + (recodesNumber);
        for (int studentIndex = beginIndex; studentIndex < endIndex; studentIndex++) {
            if (studentIndex >= studentList.size()) {
                break;
            }
            page.add(studentList.get(studentIndex));
        }
        return page;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
