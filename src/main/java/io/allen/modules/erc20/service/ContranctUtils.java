package io.allen.modules.erc20.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.exceptions.TransactionTimeoutException;

public class ContranctUtils {
	  public static String getTransferData(Address _to, Uint256 _value) throws InterruptedException, IOException, TransactionTimeoutException {
	        Function function = new Function("transfer", Arrays.<Type>asList(_to, _value), Collections.<TypeReference<?>>emptyList());
	        return FunctionEncoder.encode(function);
	    }
	  
	  public static String getBurnData(Uint256 _value) throws InterruptedException, IOException, TransactionTimeoutException {
	        Function function = new Function("burn", Arrays.<Type>asList( _value), Collections.<TypeReference<?>>emptyList());
	        return FunctionEncoder.encode(function);
	    }
}

