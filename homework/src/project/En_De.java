/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import homework.CASER ;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable ;
import java.util.Random;
/**
 *
 * @author Seree
 */
public class En_De implements Serializable{
    SecretKey key ;
    int caserkey=3 ;
    int type=0 ; 
    static int q=0 ;
    En_De(int t) throws NoSuchAlgorithmException 
    {
        if(t==2)
            key = KeyGenerator.getInstance("DES").generateKey();
        else
        {
            Random r = new Random() ;
            int num = r.nextInt() ;
            if(num<0) num*=-1 ;
            caserkey = num%26 ;
        }
        this.type=t ;
    }
    String en(String s) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        if(type==1)
        {
            return CASER.en(s, caserkey) ;
        }
        else 
        {
            return new String(DES.en(s, key)) ;
        }
    }
    String de(String s) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        if(type==1)
        {
            return CASER.de(s, caserkey) ;
        }
        else 
        {
            return DES.de(s, key) ;
        }
    }
}
