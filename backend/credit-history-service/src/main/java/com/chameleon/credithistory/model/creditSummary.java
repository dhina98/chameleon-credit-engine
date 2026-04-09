package com.chameleon.credithistory.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // generate constructor for all fields unlike
// which is @requiredargsconstrutor generates Only "required" fields: final fields and those marked @NonNull.
public class creditSummary {
    private Long userId;
    private double food;
    private double travel;
    private double fuel;
    private double groceries;
}
