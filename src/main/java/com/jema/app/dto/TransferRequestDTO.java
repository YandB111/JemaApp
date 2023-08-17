package com.jema.app.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long yourAccountId;
    private Long employeeId;
    private Long amount;

   
}
