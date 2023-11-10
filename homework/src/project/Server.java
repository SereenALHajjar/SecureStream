/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Seree
 */
public class Server {
    ServerSocket sersoc = null ;
    Socket soc ;
    ObjectInputStream ois ;
    ObjectOutputStream oos ;
    En_De en_de ;
    KeyPairGenerator keyPairGenerator ;
    KeyPair keyPair ;
    Sig si ;
    pKey ServerPublicKey , ClientrPublicKey ; 
    public Server() throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, ClassNotFoundException, SignatureException
    {
        try 
        {
            sersoc = new ServerSocket(5559) ;
            System.out.println("Server Started") ;
            soc = sersoc.accept() ;
            System.out.println("Client Connected") ;
            Scanner scan = new Scanner(System.in) ;
            ois = new ObjectInputStream(soc.getInputStream()) ;
            oos = new ObjectOutputStream(soc.getOutputStream()) ;
            keyPairGenerator = KeyPairGenerator.getInstance("RSA") ;
            keyPair = keyPairGenerator.generateKeyPair() ;
            ServerPublicKey = new pKey(keyPair.getPublic()) ;
            oos.writeObject(ServerPublicKey) ;
            oos.flush() ;
            ClientrPublicKey = (pKey) ois.readObject() ;
            si = new Sig(keyPair.getPublic() , keyPair.getPrivate() , ClientrPublicKey.getkey()) ;
        
            oos.writeUTF("\n1-Caser \n2-DES");
            oos.flush();
            String msg= ois.readUTF() ;
            int type=msg.charAt(0)-'0' ;
            en_de = new En_De(type) ;
            Object obj=en_de ;
            oos.writeObject(obj);
            oos.flush();
            System.out.println("from Client: "+msg);
//            System.out.println(ServerPublicKey.getkey());
            while (true) 
            {     
                String s1=scan.nextLine() , s2;
                s1=en_de.en(s1) ;
                s2=si.getSig(s1) ;
                Message m = new Message(s1 , s2) ;
                oos.writeObject(m);
                oos.flush();
                Message m2 = (Message) ois.readObject() ;
                String text=m2.msg ;
                System.out.println("from the client before decoding :" + text) ;
                text=en_de.de(text) ;
                System.out.println("from the client after decoding :" + text) ;
                if(!si.veri(m2.msg , m2.msgSig))
                {
                    // do something 
                    System.out.println("**") ;
                }         
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws SignatureException
    {
        try {
            new Server() ;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}