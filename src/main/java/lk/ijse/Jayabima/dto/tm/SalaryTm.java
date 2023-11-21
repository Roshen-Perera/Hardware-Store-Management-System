package lk.ijse.Jayabima.dto.tm;

import javafx.scene.control.RadioButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalaryTm {
    private String id;
    private String salary;
    private RadioButton status;
}
