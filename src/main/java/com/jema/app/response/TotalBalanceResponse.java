package com.jema.app.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TotalBalanceResponse {
    private Long totalBalance;

    public TotalBalanceResponse(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }
}
