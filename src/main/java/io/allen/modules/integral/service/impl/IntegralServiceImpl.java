package io.allen.modules.integral.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.service.ContractService;
import io.allen.modules.integral.dao.IntegralDao;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
import io.allen.modules.integral.service.UserIntegralService;
import io.allen.modules.sys.entity.SysUserEntity;


/**
 * 积分
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
@Service("integralService")
public class IntegralServiceImpl implements IntegralService {
	
	@Autowired
	private IntegralDao integralDao;
	
	@Autowired
	private UserIntegralService userIntegralService;
	
	@Autowired
	private ContractService contractService;
	
	@Override
	public IntegralEntity queryByUserId(Long userId) {
		return integralDao.queryByUserId(userId);
	}

	
	
	@Override
	@Transactional
	public void bindingIntegralUser(IntegralEntity integral, SysUserEntity user) {
		integral.setCreateTime(new Date());
		integralDao.save(integral);
		userIntegralService.saveOrUpdate(user.getUserId(), integral.getIntegralId());
	}

	@Override
	public List<IntegralEntity> queryList(Map<String, Object> map) {
		return integralDao.queryList(map);
	}

	@Override
	public double getIntegralBalance(String contractAddress, String ownerAddress) {
			String jytContractAddress = CryptoUtils.checkAddress(contractAddress);
			// 积分
			BigInteger balance = contractService.balanceOf(jytContractAddress, CryptoUtils.checkAddress(ownerAddress));
			// 精度
			BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
			
			BigDecimal decimalIntegral = new BigDecimal(balance);
			
			decimalIntegral= decimalIntegral.divide(BigDecimal.TEN.pow(decimals.intValue()));
			// 小数点左移
			// decimalIntegral = decimalIntegral.movePointLeft(decimals.intValue());
			// 保留小数点后俩位
			double integralBalance = decimalIntegral.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return integralBalance;
	}

}
