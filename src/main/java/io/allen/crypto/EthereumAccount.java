package io.allen.crypto;

import java.math.BigInteger;

import org.ethereum.util.Utils;

public class EthereumAccount {
    private ECKey ecKey;
    private byte[] address;

//    private Set<Transaction> pendingTransactions =
//            Collections.synchronizedSet(new HashSet<Transaction>());


    public EthereumAccount() {
    }

    public void init() {
        this.ecKey = new ECKey(Utils.getRandom());
        address = this.ecKey.getAddress();
    }

    public void init(ECKey ecKey) {
        this.ecKey = ecKey;
        address = this.ecKey.getAddress();
    }

    public BigInteger getNonce() {
//        return repository.getNonce(getAddress());
        return new BigInteger("0");
    }

//    public BigInteger getBalance() {
//
//        BigInteger balance =
//                repository.getBalance(this.getAddress());
//        synchronized (getPendingTransactions()) {
//            if (!getPendingTransactions().isEmpty()) {
//
//                for (Transaction tx : getPendingTransactions()) {
//                    if (Arrays.equals(getAddress(), tx.getSender())) {
//                        balance = balance.subtract(new BigInteger(1, tx.getValue()));
//                    }
//
//                    if (Arrays.equals(getAddress(), tx.getReceiveAddress())) {
//                        balance = balance.add(new BigInteger(1, tx.getValue()));
//                    }
//                }
//                // todo: calculate the fee for pending
//            }
//        }
//
//
//        return balance;
//    }


    public ECKey getEcKey() {
        return ecKey;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

//    public Set<Transaction> getPendingTransactions() {
//        return this.pendingTransactions;
//    }
//
//    public void addPendingTransaction(Transaction transaction) {
//        synchronized (pendingTransactions) {
//            pendingTransactions.add(transaction);
//        }
//    }
//
//    public void clearAllPendingTransactions() {
//        synchronized (pendingTransactions) {
//            pendingTransactions.clear();
//        }
//    }
}
