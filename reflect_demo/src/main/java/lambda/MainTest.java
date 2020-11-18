package lambda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiaohuan
 * @Date: 2019-12-21 14:47
 */
public class MainTest {
  public static void main(String[] args) {
    List<StudentInfo> studentList = new ArrayList<>();
    studentList.add(new StudentInfo("李小明",true,18,1.76, LocalDate.of(2001,3,23)));
    studentList.add(new StudentInfo("张小丽",false,18,1.61,LocalDate.of(2001,6,3)));
    studentList.add(new StudentInfo("王大朋",true,19,1.82,LocalDate.of(2000,3,11)));
    studentList.add(new StudentInfo("陈小跑",false,17,1.67,LocalDate.of(2002,10,18)));
    StudentInfo.printStudents(studentList);

    //查找身高在1.8米及以上的男生
    List<StudentInfo> boys = studentList.stream().filter(s->s.getGender() && s.getHeight() >= 1.8).collect(Collectors.toList());
//输出查找结果
    StudentInfo.printStudents(boys);

    StudentInfo info = new StudentInfo("李小明",true,18,1.76, LocalDate.of(2001,3,23));
    System.out.println(studentList.contains(info));
  }
}
