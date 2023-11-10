package homework;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DES {

     public static String en(String pl , SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
     {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(new byte[]{11,22,33,55,44,77,99,66}));
        byte[] enc = cipher.doFinal(pl.getBytes());
        String s = Base64.getEncoder().encodeToString(enc);
//        System.out.println(enc.length) ;
//        String s=Arrays.toString(enc) ;
//        System.out.println(s.length()) ;
        return s ;
    }
    public static String de(String s , SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
    {
//        byte enc[]=s.getBytes() ;
        byte[] de = Base64.getDecoder().decode(s);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(new byte[]{11,22,33,55,44,77,99,66}));
        byte[] dec = cipher.doFinal(de);
        return new String(dec) ;
    }
}
