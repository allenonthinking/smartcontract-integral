package io.allen.modules.job.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;

import io.allen.modules.generator.entity.BcDefaultAccountEntity;
import io.allen.modules.generator.service.BcDefaultAccountService;

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
	private Web3j web3j;
	/**
	 * 检查积分管理员积分账户余额是否充足
	 * 少于一定量使用默认的系统管理员进行官员积分账户充值
	 */
	public void checkIntegralBalance() {
		
	
	}
	/**
	 * 管理员用户和普通用户积分账户 eth余额检查
	 * 余额较少使用默认设置的系统账户进行发放一定量余额，用于积分转账使用
	 */
	public void checkBalance(){
		
		//List<BcDefaultAccountEntity> defaultAccount =bcDefaultAccountService.queryAccountByStatusAndType(map);
	}
	
	
}
