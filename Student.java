package StudentManagementSystem;
public class Student {
    String stdName;
    String stdDate;
    int stdId;
    float stdGpa;
    public Student( int stdId ,float stdGpa ,String stdName , String stdDate) {
        this.stdName = stdName;
        this.stdDate = stdDate;
        this.stdId = stdId;
        this.stdGpa = stdGpa;
    }
    public void setStdName(String stdName) {
        this.stdName = stdName;
    }
    public void setStdDate(String stdDate) {
        this.stdDate = stdDate;
    }
    public void setStdId(int stdId) {
        this.stdId = stdId;
    }
    public void setStdGpa(float stdGpa) {
        this.stdGpa = stdGpa;
    }
    public String getStdName() {
        return stdName;
    }
    public String getStdDate() {
        return stdDate;
    }
    public int getStdId() {
        return stdId;
    }
    public float getStdGpa() {
        return stdGpa;
    }
}
