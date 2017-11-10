package io.allen.modules.erc20.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import io.allen.common.utils.R;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.generated.NodeConfiguration;
import io.allen.modules.erc20.generated.TransactionResponse;
import io.allen.modules.erc20.service.ContractService;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for our ERC-20 contract API.
 */
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private ContractService contractService;

	@Autowired
	private IntegralConfig integralConfig;

	@RequestMapping("/integral/{address}")
	public R getIntegral(@PathVariable String address) {

		String jytContractAddress = CryptoUtils.checkAddress(integralConfig.getContractAddress());
		// 积分
		BigInteger balance = contractService.balanceOf(jytContractAddress, CryptoUtils.checkAddress(address));
		// 精度
		BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
		BigDecimal decimalIntegral = new BigDecimal(balance);
		// 小数点左移
		decimalIntegral = decimalIntegral.movePointLeft(decimals.intValue());
		// 保留小数点后俩位
		double integralBalance = decimalIntegral.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		return R.ok().put("integralBalance", integralBalance);
	}

	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	NodeConfiguration config() {
		return contractService.getConfig();
	}

	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	String deploy(HttpServletRequest request, @RequestBody ContractSpecification contractSpecification) {

		return contractService.deploy(privateKey(request), contractSpecification.getInitialAmount(),
				contractSpecification.getTokenName(), contractSpecification.getDecimalUnits(),
				contractSpecification.getTokenSymbol());
	}

	@RequestMapping(value = "/{contractAddress}/name", method = RequestMethod.GET)
	String name(@PathVariable String contractAddress) {
		return contractService.name(contractAddress);
	}

	@RequestMapping(value = "/{contractAddress}/approve", method = RequestMethod.POST)
	TransactionResponse<ContractService.ApprovalEventResponse> approve(HttpServletRequest request,
			@PathVariable String contractAddress, @RequestBody ApproveRequest approveRequest) {
		return contractService.approve(privateKey(request), contractAddress, approveRequest.getSpender(),
				approveRequest.getValue());
	}

	@RequestMapping(value = "/{contractAddress}/totalSupply", method = RequestMethod.GET)
	BigInteger totalSupply(@PathVariable String contractAddress) {
		return contractService.totalSupply(contractAddress);
	}

	@RequestMapping(value = "/{contractAddress}/transferFrom", method = RequestMethod.POST)
	TransactionResponse<ContractService.TransferEventResponse> transferFrom(HttpServletRequest request,
			@PathVariable String contractAddress, @RequestBody TransferFromRequest transferFromRequest) {
		return contractService.transferFrom(privateKey(request), contractAddress, transferFromRequest.getFrom(),
				transferFromRequest.getTo(), transferFromRequest.getValue());
	}

	@RequestMapping(value = "/{contractAddress}/decimals", method = RequestMethod.GET)
	BigInteger decimals(@PathVariable String contractAddress) {
		return contractService.decimals(contractAddress);
	}

	@RequestMapping(value = "/{contractAddress}/version", method = RequestMethod.GET)
	String version(@PathVariable String contractAddress) {
		return contractService.version(contractAddress);
	}

	@RequestMapping(value = "/{contractAddress}/balanceOf/{ownerAddress}", method = RequestMethod.GET)
	BigInteger balanceOf(@PathVariable String contractAddress, @PathVariable String ownerAddress) {
		return contractService.balanceOf(contractAddress, ownerAddress);
	}

	@RequestMapping(value = "/{contractAddress}/symbol", method = RequestMethod.GET)
	String symbol(@PathVariable String contractAddress) {
		return contractService.symbol(contractAddress);
	}

	@RequestMapping(value = "/{contractAddress}/transfer", method = RequestMethod.POST)
	TransactionResponse<ContractService.TransferEventResponse> transfer(HttpServletRequest request,
			@PathVariable String contractAddress, @RequestBody TransferRequest transferRequest) {
		return contractService.transfer(privateKey(request), contractAddress, transferRequest.getTo(),
				transferRequest.getValue());
	}

	@RequestMapping(value = "/{contractAddress}/approveAndCall", method = RequestMethod.POST)
	TransactionResponse<ContractService.ApprovalEventResponse> approveAndCall(HttpServletRequest request,
			@PathVariable String contractAddress, @RequestBody ApproveAndCallRequest approveAndCallRequest) {
		return contractService.approveAndCall(privateKey(request), contractAddress,
				approveAndCallRequest.getSpender(), approveAndCallRequest.getValue(),
				approveAndCallRequest.getExtraData());
	}

	@RequestMapping(value = "/{contractAddress}/allowance", method = RequestMethod.GET)
	BigInteger allowance(@PathVariable String contractAddress, @RequestParam String ownerAddress,
			@RequestParam String spenderAddress) {
		return contractService.allowance(contractAddress, ownerAddress, spenderAddress);
	}
	
	private static String privateKey(HttpServletRequest request) {
		String privateKey = request.getHeader("privateKey");
		return privateKey;
	}

	@Data
	static class ContractSpecification {
		private final long initialAmount;
		private final String tokenName;
		private final long decimalUnits;
		private final String tokenSymbol;
	}

	@Data
	static class ApproveRequest {
		private final String spender;
		private final long value;
	}

	@Data
	static class TransferFromRequest {
		private final String from;
		private final String to;
		private final long value;
	}

	@Data
	static class TransferRequest {
		private final String to;
		private final long value;
	}

	@Data
	static class ApproveAndCallRequest {
		private final String spender;
		private final long value;
		private final String extraData;
	}

	@Data
	static class AllowanceRequest {
		private final String ownerAddress;
		private final String spenderAddress;
	}
}
