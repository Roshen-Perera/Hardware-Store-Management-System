package lk.ijse.Jayabima.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockOrderDetailDto {
    private String stockId;
    private String supId;
    private String date;
}

