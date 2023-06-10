package com.sarfaraz.opticart.user.dto.power;

import com.sarfaraz.opticart.commons.annotation.PowerIncrement;
import com.sarfaraz.opticart.user.enums.BaseHorizontalDirection;
import com.sarfaraz.opticart.user.enums.BaseVerticalDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrismDto {
    @PowerIncrement(min = 0.5f, max = 5.0f, message = "Value must fall in range of 0.5 to 5.0")
    private float horizontal;
    private BaseHorizontalDirection baseHorizontal;
    @PowerIncrement(min = 0.5f, max = 5.0f, message = "Value must fall in range of 0.5 to 5.0")
    private float vertical;
    private BaseVerticalDirection baseVertical;


}
