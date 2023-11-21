package lk.ijse.Jayabima.dto;

import lk.ijse.Jayabima.dto.tm.StockCartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class PlaceStockOrderDto {
    private String stockOrder_id;
    private String sup_id;
    private LocalDate supOrder_date;
    private List<StockCartTm> stockCartTmList = new ArrayList<>();
}
