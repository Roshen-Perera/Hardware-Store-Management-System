package lk.ijse.Jayabima.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockCartTm {
    private String code;
    private String description;
    private int qty;
    private String supID;
    private Button btn;

}
