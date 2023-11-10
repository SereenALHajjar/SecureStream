/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
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
public class Client {

    Socket soc;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    En_De en_de;
    KeyPairGenerator keyPairGenerator ;
    KeyPair keyPair ;
    Sig si ;
    pKey ClientPublicKey , ServerPublicKey ; 
    public Client() throws IOException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, ClassNotFoundException, InterruptedException, SignatureException 
    {
        soc = new Socket("localhost", 5559);
        Scanner scan = new Scanner(System.in);
        oos = new ObjectOutputStream(soc.getOutputStream());
        ois = new ObjectInputStream(soc.getInputStream());
        keyPairGenerator = KeyPairGenerator.getInstance("RSA") ;
        keyPair = keyPairGenerator.generateKeyPair() ;
        ClientPublicKey = new pKey(keyPair.getPublic()) ;
        ServerPublicKey = (pKey) ois.readObject() ;
        si = new Sig(keyPair.getPublic() , keyPair.getPrivate() , ServerPublicKey.getkey()) ;
        oos.writeObject(ClientPublicKey);
        oos.flush();
        String v = ois.readUTF();
        System.out.println(v);
        String t = scan.nextLine();
        oos.writeUTF(t);
        oos.flush();
        Object obj = ois.readObject();
        en_de = (En_De) obj;
//        System.out.println(ServerPublicKey.getkey());
        while (true) 
        {
            Message m = (Message) ois.readObject() ;
            System.out.println("from the server before decoding :" + m.msg);
            String text = en_de.de(m.msg);
            System.out.println("from the server after decoding :" + text);
            if(!si.veri(m.msg , m.msgSig))
            {
                // do something 
                System.out.println("***") ;
            }
            String s = scan.nextLine() , ss ;
            s = en_de.en(s) ;
            ss = si.getSig(s) ;
            Message m2 = new Message(s , ss) ;
            oos.writeObject(m2) ;
            oos.flush() ;
        }
    }


    public static void main(String[] args) throws SignatureException {
        try {
            try {
                new Client();
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
