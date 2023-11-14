package lk.ijse.Jayabima.dto.tm;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeTm {
    private String Id;
    private String Name;
    private String Role;
    private String Address;
    private String Salary;
    private String Mobile;
}
