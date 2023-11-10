/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import javax.crypto.SecretKey;

/**
 *
 * @author Seree
 */
public class CASER {
    public static String en(String pl , int key)
    {
        String enco="" ;
        for(int i=0 ; i<pl.length() ; i++)
        {
            char ch=pl.charAt(i) ;
            if(!Character.isAlphabetic(ch))
            {
                enco+=ch ;
                continue ;
            }
            if(Character.isLowerCase(ch))
                enco+= (char)((ch-'a'+key)%26+'a') ;
            if(Character.isUpperCase(ch))
                enco+= (char)((ch-'A'+key)%26+'A') ;
        }
        return enco ;
    }
    
    
    public static String de(String pl , int key)
    {
        String enco="" ;
        for(int i=0 ; i<pl.length() ; i++)
        {
            char ch=pl.charAt(i) ;
            if(!Character.isAlphabetic(ch))
            {
                enco+=ch ;
                continue ;
            }
          
            if(Character.isLowerCase(ch))
            {
                if(ch-'a'>=key)
                    enco+= (char)(ch-key) ;
                else enco+= (char)((ch-'a'-key+26)+'a') ;
            }
                
            if(Character.isUpperCase(ch))
            {
                if(ch-'A'>=key)
                    enco+= (char)(ch-'A') ;
                enco+= (char)((ch-'A'-key+26)+'A') ;
            }
                
        }
        return enco ;
    }
    
      
}