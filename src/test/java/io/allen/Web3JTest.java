package io.allen;

import java.io.IOException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

public class Web3JTest {
	public static void main(String[] args) {
		Web3j web3 = Web3j.build(new HttpService("http://192.168.32.182:8545"));  // defaults to http://localhost:8545/
		Web3ClientVersion web3ClientVersion;
		try {
			web3ClientVersion = web3.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			Request<?, EthAccounts> requests = web3.ethAccounts();
			EthAccounts accounts = requests.send();
			System.out.println(accounts.getAccounts());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
