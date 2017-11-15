package io.allen.config;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.generated.NodeConfiguration;

/**
 * ERC2.0 TOKEN的配置文件
 *
 * @author allen.liu
 */
@Configuration
public class ERCToken20Config {

	@Bean("nodeConfiguration")
	public NodeConfiguration nodeConfiguration(@Value("${allen.nodeEndpoint}") String nodeEndpoint,
			@Value("${allen.fromAddress}") String fromAddress) {
		NodeConfiguration nodeConfiguration = new NodeConfiguration();
		nodeConfiguration.setNodeEndpoint(nodeEndpoint);
		nodeConfiguration.setFromAddress(fromAddress);
		return nodeConfiguration;
	}

	@Bean("integralConfig")
	public IntegralConfig integralConfig(@Value("${allen.integralContrctAddress}") String integralContrctAddress,
			@Value("${allen.gaslimit}") BigInteger gasLimit, @Value("${allen.txdata}") String txData,@Value("${allen.integralRecycleAddress}") String recycleAddress) {
		IntegralConfig integralConfig = new IntegralConfig();
		integralConfig.setContractAddress(integralContrctAddress);
		integralConfig.setGasLimit(gasLimit);
		integralConfig.setTxData(txData);
		integralConfig.setRecycleAddress(recycleAddress);
		return integralConfig;
	}

	@Bean("web3j")
	public Web3j web3j(NodeConfiguration nodeConfiguration) {
		Web3j web3j = Web3j.build(new HttpService(nodeConfiguration.getNodeEndpoint()));
		return web3j;
	}
}
