package io.allen;

import java.math.BigInteger;

import org.spongycastle.util.encoders.Hex;

import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;

public class KeyStoreTest {
    public static void main(String[] args) {
//   	 final ECKey key = ECKey.fromPrivate(new BigInteger("1E99423A4ED27608A15A2616A2B0E9E52CED330AC530EDCC32C8FFC6A526AEDD",16));
	      	final ECKey key = new ECKey();
//	      	System.out.println(Hex.toHexString(key.getPrivKeyBytes()));
	        EthereumAccount account = new EthereumAccount();
	        account.init(key);
	        KeystoreFormat keystoreFormat = new KeystoreFormat();
	        String content = keystoreFormat.toKeystore(key, "123456");
	        final String address =Hex.toHexString((account.getAddress()));
	        System.out.println(content);
	        System.out.println(address);
	        
	        KeystoreFormat keystoreFormat2 = new KeystoreFormat();
	        ECKey key2 =  keystoreFormat2.fromKeystore(content, "123456");
	        System.out.println(Hex.toHexString(key2.getPrivKeyBytes()));
	        
	}
}
