package io.allen.modules.erc20.generated;

import org.spongycastle.util.encoders.Hex;

public class CryptoUtils {
	public static String toHexString(byte[] value) {
        return value == null ? "" : Hex.toHexString(value);
    }	
	public static String cleanAddress(String input) {
        if (input != null) {
            if (input.startsWith("0x")) {
                return input.substring(2).toLowerCase();
            }
            return input.toLowerCase();
        }
        return input;
    }
	
	public static String checkAddress(String input) {
        if (input != null) {
            if (input.startsWith("0x")) {
                return input.toLowerCase() ;
            }
            return ("0x"+input).toLowerCase();
        }
        return input;
    }
}
