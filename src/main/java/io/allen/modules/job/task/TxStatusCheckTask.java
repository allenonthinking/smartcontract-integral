package io.allen.modules.job.task;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthTransaction;

import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.generator.service.BcTransactionService;

/**
 * 
 * 区块链交易状态检查
 * @author allen.liu
 * @date 2017年11月13
 */
@Component("txStatusCheckTask")
public class TxStatusCheckTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BcTransactionService bcTransactionService;
	
	@Autowired
	private Web3j web3j;
	
	public void check(){
		// {nd=1510554518576, limit=10, page=1, _search=false, sidx=, order=asc,_=1510554518417}
		// 数据量大需要加上查询分页
		// Query query = new Query(params);
		
		Map<String, Object> map = new HashMap<String,Object>();
		// 查询未检查过的  和状态未确认的交易
		List<BcTransactionEntity> txs = bcTransactionService.queryNotProcessed(map);
		if (txs != null && txs.size() > 0) {
			try {
				// 查询当前的区块数
				EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
				BigInteger blockNumber = ethBlock.getResult().getNumber();
				for (BcTransactionEntity bcTransactionEntity : txs) {
					// 查询当前交易在区块链上的状态
					EthTransaction ethTx = web3j
							.ethGetTransactionByHash(CryptoUtils.checkAddress(bcTransactionEntity.getTxId())).send();
					
					if (ethTx.getResult() != null) {
						if (StringUtils.isNotBlank(ethTx.getResult().getBlockHash())) {
							//比较交易的区块数和当前区块数
							BigInteger txBlockNumber  = ethTx.getResult().getBlockNumber();
							Integer stutasNum =  blockNumber.subtract(txBlockNumber).intValue();
							bcTransactionEntity.setStatus(stutasNum);
							bcTransactionService.updateStatus(bcTransactionEntity);
						} 
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
	
	
}
