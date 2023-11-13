package lk.ijse.Jayabima.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SupplierDto {
    private String supId;
    private String supName;
    private String supDesc;
    private String supMobile;
}