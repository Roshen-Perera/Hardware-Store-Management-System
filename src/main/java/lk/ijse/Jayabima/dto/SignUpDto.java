package lk.ijse.Jayabima.dto;

public class SignUpDto {
    private String username;
    private String mobile;
    private String password;
    private String repeatPassword;

    public SignUpDto(String username, String mobile, String password, String repeatPassword) {
        this.username = username;
        this.mobile = mobile;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}