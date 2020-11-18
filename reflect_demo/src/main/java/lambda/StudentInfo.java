package lambda;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: xiaohuan
 * @Date: 2019-12-21 14:45
 */
@Data
public class StudentInfo implements Comparable<StudentInfo> {

  //名称
  private String name;

  //性别 true男 false女
  private Boolean gender;

  //年龄
  private Integer age;

  //身高
  private Double height;

  //出生日期
  private LocalDate birthday;

  public StudentInfo(String name, Boolean gender, Integer age, Double height, LocalDate birthday){
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.height = height;
    this.birthday = birthday;
  }

  @Override
  public String toString(){
    String info = String.format("%s\t\t%s\t\t%s\t\t\t%s\t\t%s",this.name,this.gender.toString(),this.age.toString(),this.height.toString(),birthday.toString());
    return info;
  }

  public static void printStudents(List<StudentInfo> studentInfos){
    System.out.println("[姓名]\t\t[性别]\t\t[年龄]\t\t[身高]\t\t[生日]");
    System.out.println("----------------------------------------------------------");
    studentInfos.forEach(s->System.out.println(s.toString()));
    System.out.println(" ");
  }

  @Override
  public int compareTo(StudentInfo ob) {
    return this.age.compareTo(ob.getAge());
    //return 1;
  }

  @Override
  public boolean equals(Object object){
    if(object!=null && object instanceof StudentInfo){
      StudentInfo other = (StudentInfo) object;
      if(other.getName()!=null && other.getName().equals(this.getName()) && other.getGender().equals(this.getGender())
              && other.getAge().equals(this.getAge()) && other.getBirthday().equals(this.getBirthday())){
        return true;
      }
    }
    return false;

  }
}
