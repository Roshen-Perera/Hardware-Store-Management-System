package lk.ijse.Jayabima.dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemDto {
    private String itemCode;
    private String itemName;
    private String itemUnitPrice;
    private String itemSellingPrice;

}


