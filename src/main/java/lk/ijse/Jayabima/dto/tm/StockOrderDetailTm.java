package lk.ijse.Jayabima.dto.tm;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockOrderDetailTm {
    private String stockId;
    private String supId;
    private String date;
}
