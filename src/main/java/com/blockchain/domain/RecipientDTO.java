package com.blockchain.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RecipientDTO {

    private BigDecimal balance;

    private String address;
}
