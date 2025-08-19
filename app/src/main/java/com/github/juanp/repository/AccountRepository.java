package com.github.juanp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.juanp.exception.AccountNotFoundException;
import com.github.juanp.exception.PixInUseException;
import com.github.juanp.model.AccountWallet;
import com.github.juanp.model.MoneyAudit;

import static com.github.juanp.repository.CommonsRepository.checkFundsForTransaction;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFunds){
        if (!accounts.isEmpty()){
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (var p: pix) {
                if(pixInUse.contains(p)) {
                    throw new PixInUseException("O pix '" + p + "' já está em uso");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount){
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito");
    }

    public long withdraw(final String pix, final long amount){
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount); 
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount){
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "Pix enviado de '" + sourcePix + "' para '" + targetPix + "'";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix){
        return accounts.stream()
            .filter(a -> a.getPix().contains(pix))
            .findFirst()
            .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pix '" + pix + "' não existe ou foi encerrada"));
    }

    public Map<OffsetDateTime, List<MoneyAudit>> getHistory(final String pix) {
        AccountWallet wallet = findByPix(pix);
        return wallet.getFinancialTransactions().stream()
            .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
    }

    public List<AccountWallet> list(){
        return this.accounts;
    }
}
