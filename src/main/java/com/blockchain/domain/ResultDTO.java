package com.blockchain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private SenderDTO sender;

    private RecipientDTO recipient;
}
