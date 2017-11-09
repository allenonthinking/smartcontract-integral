package io.allen.modules.erc20.controller;

import org.spongycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.utils.R;
import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.integral.entity.IntegralEntity;

@RestController
@RequestMapping("/crypto")
public class CryptoController {
	@RequestMapping("/keystore/{password}")
	public R createKeyStore(@PathVariable("password") String password){
	      final ECKey key = new ECKey();
	        EthereumAccount account = new EthereumAccount();
	        account.init(key);
	        KeystoreFormat keystoreFormat = new KeystoreFormat();
	        String content = keystoreFormat.toKeystore(key, password);
	        final String address =CryptoUtils.toHexString(account.getAddress());
	        IntegralEntity integral = new IntegralEntity();
	        integral.setAddress(address);
	        integral.setKeystore(content);
		return R.ok().put("integral", integral);
	}
}
