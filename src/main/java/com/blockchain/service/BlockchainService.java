package com.blockchain.service;

import com.blockchain.domain.RecipientDTO;
import com.blockchain.domain.ResultDTO;
import com.blockchain.domain.SenderDTO;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@Service
public class BlockchainService {
    private static final String URL = "http://localhost:8545";

    public ResultDTO send(Double value, String senderAddress, String recipientAddress) {
        Web3j web3 = Web3j.build(new HttpService(URL));
        ResultDTO result =  new ResultDTO();
        try {
            obterNúmeroDoUltimoBloco(web3);
            EthGetBalance balanceSender = web3.ethGetBalance(senderAddress, DefaultBlockParameterName.LATEST).send();
            BigDecimal saldoEther = Convert.fromWei(balanceSender.getBalance().toString(), Convert.Unit.ETHER);
            System.out.println("Saldo do endereço de quem esta enviando " + senderAddress + ": " + saldoEther + " Ether");

            TransactionReceipt transactionReceipt = getTransactionReceipt(value, recipientAddress, web3);

            System.out.println("Transação enviada. Hash: " + transactionReceipt.getTransactionHash());

            EthGetBalance balanceRecipient = web3.ethGetBalance(recipientAddress, DefaultBlockParameterName.LATEST).send();
            BigDecimal saldoEtherRecipient = Convert.fromWei(balanceRecipient.getBalance().toString(), Convert.Unit.ETHER);
            System.out.println("Saldo do endereço recebedor " + recipientAddress + ": " + saldoEtherRecipient + " Ether");

            result = buildResult(senderAddress, balanceSender, recipientAddress, balanceRecipient);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private ResultDTO buildResult(final String senderAddress, final EthGetBalance senderObject, final String recipientAddress, final EthGetBalance recipientObject){

        BigDecimal saldoSender = Convert.fromWei(senderObject.getBalance().toString(), Convert.Unit.ETHER);

        final SenderDTO sender = SenderDTO.builder()
                .address(senderAddress)
                .balance(saldoSender)
                .build();

        BigDecimal saldoRecipient = Convert.fromWei(recipientObject.getBalance().toString(), Convert.Unit.ETHER);

        final RecipientDTO recipient = RecipientDTO.builder()
                .address(recipientAddress)
                .balance(saldoRecipient)
                .build();


        return ResultDTO.builder()
                .sender(sender)
                .recipient(recipient)
               .build();
    }

    private static TransactionReceipt getTransactionReceipt(final Double value, final String recipientAddress,final Web3j web3) throws InterruptedException, ExecutionException, IOException, TransactionException {
        TransactionReceipt transactionReceipt = Transfer.sendFunds(
                        web3, Credentials.create("your-private-key"),
                        recipientAddress,
                        BigDecimal.valueOf(value), Convert.Unit.ETHER)
                .sendAsync()
                .get();
        return transactionReceipt;
    }

    private static void obterNúmeroDoUltimoBloco(Web3j web3) throws IOException {
        EthBlock.Block latestBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send()
                .getBlock();
        System.out.println("Número do último bloco: " + latestBlock.getNumber());
    }
}