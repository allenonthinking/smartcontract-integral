package io.allen.modules.erc20.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.HumanStandardToken;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.generated.NodeConfiguration;
import io.allen.modules.erc20.generated.TransactionResponse;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * Our smart contract service.
 */
@Service("contractService")
public class ContractService {

	@Autowired
	private NodeConfiguration nodeConfiguration;

	@Autowired
	private Web3j web3j;

	@Autowired
	private IntegralConfig integralConfig;
	
	
	public NodeConfiguration getConfig() {
		return nodeConfiguration;
	}

	public Web3j getWeb3j() {
		return this.web3j;
	}

	public BigInteger etherBalanceOf(String ownerAddress) {
		try {
			EthGetBalance balance = web3j
					.ethGetBalance(CryptoUtils.checkAddress(ownerAddress), DefaultBlockParameterName.LATEST).send();
			return balance.getBalance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<TransferEventResponse> transferEther(String privateKey, String to, BigInteger value) {
		try {
			TransactionManager transactionManager = loadRawTransactionManagerKey(privateKey);
			EthSendTransaction sendTransaction = transactionManager.sendTransaction(GAS_PRICE, integralConfig.getGasLimit(), to,
					integralConfig.getTxData(), value);
			return new TransactionResponse<>(sendTransaction.getTransactionHash());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse transferKey(String privateKey, String contractAddress, String to,
			BigInteger value) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken.transferNoWait(new Address(to), new Uint256(value));
			return new TransactionResponse(transactionReceipt.getTransactionHash());
		} catch (InterruptedException  |IOException  |TransactionTimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<TransferEventResponse> transferEventKey(String privateKey, String contractAddress, String to,
			BigInteger value) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken.transfer(new Address(to), new Uint256(value))
					.get();
			return processTransferEventsResponse(humanStandardToken, transactionReceipt);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	private HumanStandardToken loadKey(String contractAddress, String privateKey) {
		TransactionManager transactionManager = loadRawTransactionManagerKey(privateKey);
		return HumanStandardToken.load(contractAddress, web3j, transactionManager, GAS_PRICE, integralConfig.getGasLimit());
	}

	private TransactionManager loadRawTransactionManagerKey(String privateKey) {
		Credentials credentials = Credentials.create(privateKey);
		return new RawTransactionManager(web3j, credentials);
	}

	public String deploy(String privateKey, long initialAmount, String tokenName, long decimalUnits,
			String tokenSymbol) {
		try {
			TransactionManager transactionManager = loadRawTransactionManagerKey(privateKey);
			HumanStandardToken humanStandardToken = HumanStandardToken.deploy(web3j, transactionManager, GAS_PRICE,
					integralConfig.getGasLimit(), BigInteger.ZERO, new Uint256(initialAmount), new Utf8String(tokenName),
					new Uint8(decimalUnits), new Utf8String(tokenSymbol)).get();
			return humanStandardToken.getContractAddress();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public String name(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.name().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<ApprovalEventResponse> approve(String privateKey, String contractAddress, String spender,
			long value) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken.approve(new Address(spender), new Uint256(value))
					.get();
			return processApprovalEventResponse(humanStandardToken, transactionReceipt);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public BigInteger totalSupply(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.totalSupply().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<TransferEventResponse> transferFrom(String privateKey, String contractAddress,
			String from, String to, long value) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken
					.transferFrom(new Address(from), new Address(to), new Uint256(value)).get();
			return processTransferEventsResponse(humanStandardToken, transactionReceipt);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public BigInteger decimals(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.decimals().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public BigInteger decimalsBigInteger(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.decimals().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public String version(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.version().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public BigInteger balanceOf(String contractAddress, String ownerAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			Uint value = humanStandardToken.balanceOf(new Address(ownerAddress)).get();
			return extractValue(value);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public String symbol(String contractAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(humanStandardToken.symbol().get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<TransferEventResponse> transfer(String privateKey, String contractAddress, String to,
			long value) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken.transfer(new Address(to), new Uint256(value))
					.get();
			return processTransferEventsResponse(humanStandardToken, transactionReceipt);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public TransactionResponse<ApprovalEventResponse> approveAndCall(String privateKey, String contractAddress,
			String spender, long value, String extraData) {
		HumanStandardToken humanStandardToken = loadKey(contractAddress, privateKey);
		try {
			TransactionReceipt transactionReceipt = humanStandardToken
					.approveAndCall(new Address(spender), new Uint256(value), new DynamicBytes(extraData.getBytes()))
					.get();
			return processApprovalEventResponse(humanStandardToken, transactionReceipt);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public BigInteger allowance(String contractAddress, String ownerAddress, String spenderAddress) {
		HumanStandardToken humanStandardToken = load(contractAddress);
		try {
			return extractValue(
					humanStandardToken.allowance(new Address(ownerAddress), new Address(spenderAddress)).get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private HumanStandardToken load(String contractAddress) {
		TransactionManager transactionManager = new ClientTransactionManager(web3j, nodeConfiguration.getFromAddress());
		return HumanStandardToken.load(contractAddress, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);
	}

	private <T> T extractValue(Type<T> value) {
		if (value != null) {
			return value.getValue();
		} else {
			throw new RuntimeException("Empty value returned by call");
		}
	}

	private TransactionResponse<ApprovalEventResponse> processApprovalEventResponse(
			HumanStandardToken humanStandardToken, TransactionReceipt transactionReceipt) {

		return processEventResponse(humanStandardToken.getApprovalEvents(transactionReceipt), transactionReceipt,
				ApprovalEventResponse::new);
	}

	private TransactionResponse<TransferEventResponse> processTransferEventsResponse(
			HumanStandardToken humanStandardToken, TransactionReceipt transactionReceipt) {

		return processEventResponse(humanStandardToken.getTransferEvents(transactionReceipt), transactionReceipt,
				TransferEventResponse::new);
	}

	private <T, R> TransactionResponse<R> processEventResponse(List<T> eventResponses,
			TransactionReceipt transactionReceipt, Function<T, R> map) {
		if (!eventResponses.isEmpty()) {
			return new TransactionResponse<>(transactionReceipt.getTransactionHash(), map.apply(eventResponses.get(0)));
		} else {
			return new TransactionResponse<>(transactionReceipt.getTransactionHash());
		}
	}

	@Getter
	@Setter
	public static class TransferEventResponse {
		private String from;
		private String to;
		private long value;

		public TransferEventResponse() {
		}

		public TransferEventResponse(HumanStandardToken.TransferEventResponse transferEventResponse) {
			this.from = transferEventResponse._from.toString();
			this.to = transferEventResponse._to.toString();
			this.value = transferEventResponse._value.getValue().longValueExact();
		}
	}

	@Getter
	@Setter
	public static class ApprovalEventResponse {
		private String owner;
		private String spender;
		private long value;

		public ApprovalEventResponse() {
		}

		public ApprovalEventResponse(HumanStandardToken.ApprovalEventResponse approvalEventResponse) {
			this.owner = approvalEventResponse._owner.toString();
			this.spender = approvalEventResponse._spender.toString();
			this.value = approvalEventResponse._value.getValue().longValueExact();
		}
	}
}
