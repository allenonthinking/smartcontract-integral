package io.allen.modules.erc20.generated;

import java.math.BigInteger;

/**
 * IntegralConfig  bean.
 */
public class IntegralConfig {

    private String contractAddress;
    
    private BigInteger gasLimit;
    
    private String txData;

    private String recycleAddress;
    
	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public BigInteger getGasLimit() {
		return gasLimit;
	}

	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}

	public String getTxData() {
		return txData;
	}

	public void setTxData(String txData) {
		this.txData = txData;
	}

	public String getRecycleAddress() {
		return recycleAddress;
	}

	public void setRecycleAddress(String recycleAddress) {
		this.recycleAddress = recycleAddress;
	}

    
}
