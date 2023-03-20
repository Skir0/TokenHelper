package project;

import java.util.HashMap;
import java.util.Scanner;

public class Inputer {
    static HashMap <String,String> inputer=new HashMap<String, String>();

    static{
        inputer.put("ADA","https://ru.investing.com/crypto/cardano/historical-data");
        inputer.put("BTC","https://ru.investing.com/crypto/bitcoin/historical-data");
        inputer.put("ETH","https://ru.investing.com/crypto/ethereum/historical-data");
        inputer.put("LTC","https://ru.investing.com/crypto/litecoin/historical-data");
        inputer.put("XMR","https://ru.investing.com/crypto/monero/historical-data");
        inputer.put("USDT","https://ru.investing.com/crypto/tether/historical-data");
    }

    public static String input(){
        Scanner scan=new Scanner(System.in);
        String line=scan.nextLine();
        if(line.equals("EXIT")){
            System.exit(0);
        }
        else{
            return inputer.get(line);
        }
        return inputer.get("BTC");
    }

}