package com.blockchain.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SenderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal balance;

    private String address;
}
