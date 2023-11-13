package lk.ijse.Jayabima.dto.tm;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemTm {
    private String itemCode;
    private String itemName;
    private String itemDesc;
    private int itemQty;
    private double itemUnitPrice;
    private String supplierId;
}


