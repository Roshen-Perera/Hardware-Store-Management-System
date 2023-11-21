package lk.ijse.Jayabima.dto;

import lk.ijse.Jayabima.dto.tm.CustomerCartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlaceItemOrderDto {
    private String orderId;
    private String customerId;
    private String totalPrice;
    private LocalDate date;
    private List<CustomerCartTm> customerCartTmList = new ArrayList<>();
}