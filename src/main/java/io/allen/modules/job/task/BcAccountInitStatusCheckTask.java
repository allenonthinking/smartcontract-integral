package io.allen.modules.job.task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.TransactionResponse;
import io.allen.modules.erc20.service.ContractService;
import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.generator.entity.BcDefaultAccountEntity;
import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.generator.service.BcAdminAccountService;
import io.allen.modules.generator.service.BcDefaultAccountService;
import io.allen.modules.generator.service.BcTransactionService;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;

/**
 * 
 * 区块链账户初始化状态检查
 * @author allen.liu
 * @date 2017年11月14
 */
@Component("bcAccountInitStatusCheckTask")
public class BcAccountInitStatusCheckTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BcDefaultAccountService bcDefaultAccountService;
	
	@Autowired
	private BcAdminAccountService bcAdminAccountService;
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private BcTransactionService bcTransactionService;
	
	@Autowired
	private ContractService contractService;

	/**
	 * 检查积分管理员积分账户余额是否充足
	 * 少于一定量使用默认的系统管理员进行官员积分账户充值
	 */
	public void checkIntegralBalance() {
		
	
	}
	
	// 管理员账户最小余额
	private final BigInteger adminMinWei = Convert.toWei(new BigDecimal("10"), Unit.ETHER).toBigInteger(); 
	
	// 普通账户最小余额
	private final BigInteger normalAccountBalanceWei =  Convert.toWei(new BigDecimal("0.01"), Unit.ETHER).toBigInteger(); 
	
	private String  transferEth = "0.1";
	// 转账金额
	private final BigInteger transferWei = Convert.toWei(new BigDecimal(transferEth), Unit.ETHER).toBigInteger(); 
	/**
	 * 管理员用户和普通用户积分账户 eth余额检查
	 * 余额较少使用默认设置的系统账户进行发放一定量余额，用于积分转账使用
	 */
	public void checkBalance(){
		//查询状态正常基础发放账户
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		map.put("type", 1);
		List<BcDefaultAccountEntity> defaultAccounts =bcDefaultAccountService.queryAccountByStatusAndType(map);
		if(defaultAccounts!=null && defaultAccounts.size()>0) {
			for (BcDefaultAccountEntity bcDefaultAccountEntity : defaultAccounts) {
				BigInteger  bibalance = 	contractService.etherBalanceOf(CryptoUtils.checkAddress(bcDefaultAccountEntity.getAddress()));
				if(bibalance.compareTo(adminMinWei) <0 ) {
					logger.error("区块链账户:"+bcDefaultAccountEntity.getAddress() +"余额小于"+adminMinWei+"ETH");
					//继续使用下个账户
					continue;
				}
				
				Map<String, Object> params = new HashMap<String,Object>();
				// 业务管理员区块链账户
				List<BcAdminAccountEntity> adminAccountList = bcAdminAccountService.queryList(params);
				// 普通用户区块链账户
				List<IntegralEntity> integralAccountList =  integralService.queryList(map);
				
				List<String> addressList = new ArrayList<String>();
				
				// 已经绑定的所有区块链账户
				for (BcAdminAccountEntity bcAdminAccountEntity : adminAccountList) {
					if(StringUtils.isNotBlank(bcAdminAccountEntity.getAddress())) {
						addressList.add(bcAdminAccountEntity.getAddress());
					}
				}
			
				for (IntegralEntity integralEntity : integralAccountList) {
					if(StringUtils.isNotBlank(integralEntity.getAddress())) {
						addressList.add(integralEntity.getAddress());
					}
				}
				// 去重
				List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(addressList));
				
				
				if(listWithoutDup != null && listWithoutDup.size()>0) {
					
					// 默认账户解锁私钥
			        KeystoreFormat keystoreFormat = new KeystoreFormat();
			        ECKey key =  keystoreFormat.fromKeystore(bcDefaultAccountEntity.getKeystore(), bcDefaultAccountEntity.getPassword());
			        String privateKey = Hex.toHexString(key.getPrivKeyBytes());
			        EthereumAccount fromaccount = new EthereumAccount();
			        fromaccount.init(key);
			        String fromAddress = CryptoUtils.checkAddress(Hex.toHexString(fromaccount.getAddress()));
					for (String	 address : listWithoutDup) {
						BigInteger  accountBalance = 	contractService.etherBalanceOf(CryptoUtils.checkAddress(address));
						// 默认账户小于最小定义余额，则转账
						if(accountBalance.compareTo(normalAccountBalanceWei) < 0 ) {
							 TransactionResponse resp  = contractService.transferEther(privateKey, CryptoUtils.checkAddress(address), transferWei);
							 String txId = resp.getTransactionHash();
							 BcTransactionEntity bcTransaction = new BcTransactionEntity();
							 bcTransaction.setTxId(txId);
							 bcTransaction.setStatus(0);
							 bcTransaction.setFromAddress(fromAddress);
							 bcTransaction.setToAddress(CryptoUtils.checkAddress(address));
							 bcTransaction.setAmount(transferEth);
							 bcTransaction.setValue(transferWei.toString());
							 bcTransaction.setDecimals(18l);
							 bcTransaction.setType(1);
							 bcTransactionService.save(bcTransaction);
						}
					}
				}
				break;
			}
		}else {
			logger.error("丢失有效的默认基础余额发放账户");
		}
	}
	
	
}
