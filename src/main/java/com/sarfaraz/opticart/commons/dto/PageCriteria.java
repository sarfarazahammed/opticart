package com.sarfaraz.opticart.commons.dto;

import com.sarfaraz.opticart.commons.enums.SortOrder;
import lombok.Data;

@Data
public class PageCriteria {
    private int pageNumber;
    private int pageSize;
    private String sortAttribute;
    private SortOrder sortOrder;
}
