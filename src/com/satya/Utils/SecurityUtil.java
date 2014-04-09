//package com.satya.Utils;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.spec.InvalidKeySpecException;
//import java.util.LinkedHashSet;
//import java.util.LinkedList;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.DESKeySpec;
//
//import com.nlogic.ebay.security.Base64;
//
//public class SecurityUtil {
//	private static SecureRandom sr = null;
//    private static SecretKey key = null;
//    private static Base64 codec = new Base64();
//    private final static String ALGORITHEM = "DES";
//
//    public static void init(String base64Key){
//        try{
//            sr = SecureRandom.getInstance("SHA1PRNG");
//            byte[] seed = new String("nlogic3525").getBytes();
//            sr.setSeed(seed);
//        }catch(NoSuchAlgorithmException e){
//            throw new RuntimeException("Unable to initialize SecurityUtil",e);
//        }
//        
//        byte[] decodedKey = codec.decode(base64Key.getBytes());
//        try{
//            DESKeySpec desSpec = new DESKeySpec(decodedKey);
//            SecretKeyFactory desFactory = SecretKeyFactory.getInstance(ALGORITHEM);
//            key = desFactory.generateSecret(desSpec);
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static boolean isNumeric(String integer){
//        if ((integer == null) || (integer.length() == 0))
//            return false;
//        int index = 0;
//        char character = 0;
//        for (; index < integer.length(); index++){
//            if(integer.charAt(index)=='.')
//                continue;
//            if (((character = integer.charAt(index)) < '0') || (character > '9'))
//                return false;
//        }
//        return true;
//    }
//
//    public static String generateRegistrationKey(){
//        byte[] randomBytes = new byte[12];
//        sr.nextBytes(randomBytes);
//        Base64 encoder = new Base64();
//        randomBytes = encoder.encode(randomBytes);
//        return new String(randomBytes);
//    }
//
//   
//
//    public static String generatePassword(){
//        String newPassword = Integer.toHexString(
//                (int)(Math.random() * 0xfffffff));
//        return newPassword;
//    }
//
//    public static boolean IsValidEmailAddress(String emailAddress){
//        if(emailAddress == null)
//            return false;
//        if (emailAddress.length() < 7)
//            return false;
//        int atIndex = emailAddress.indexOf('@');
//        if ((atIndex < 1) || (atIndex == (emailAddress.length() - 1)))
//            return false;
//        int dotIndex = emailAddress.indexOf('.', atIndex);
//        if ((dotIndex < (atIndex + 2)) || (atIndex == (emailAddress.length() - 1)))
//            return false;
//        char character = 0;
//        for (int i = emailAddress.length() - 1; i >= 0 ; i--){
//            character = emailAddress.charAt(i);
//            if (!Character.isLetterOrDigit(character) && (character != '.') && (character != '-')
//                && (character != '_') && (character != '~') && (i != atIndex))
//                return false;
//        }
//        return true;
//    }
//    
//    /**
//     * Generates a secret key pair based on RSA algorthm. The only
//     * change is that two key primary numbers for generation of public
//     * key and primary key are hard-coded which will result in
//     * always and always the same set of public and private keys.
//     * As mentioned in SecurityUtils.encrypt() and .decrypt() java-doc,
//     * we propose to change it to generate public and private keys from
//     * purely random numbers and be done at deployment time.
//     */
//    private static String generateSecretKeyString(){
//        byte[] keyBytes = null;
//        try{
//            KeyGenerator desGen = KeyGenerator.getInstance("DES");
//            SecretKey desKey = desGen.generateKey();
//            SecretKeyFactory desFactory = SecretKeyFactory.getInstance("DES");
//            DESKeySpec desSpec =
//                (DESKeySpec)desFactory.getKeySpec(desKey,javax.crypto.spec.DESKeySpec.class);
//            keyBytes = desSpec.getKey();
//            Base64 encoder = new Base64();
//            keyBytes = encoder.encode(keyBytes);
//        }catch(NoSuchAlgorithmException nse){
//            throw new RuntimeException("Error generating key",nse);
//        }catch(InvalidKeySpecException ikse){
//            throw new RuntimeException("Error generating key",ikse);
//        }
//        return new String(keyBytes);
//    }
//
//    private static SecretKey generateSecretKey(){
//        String keyStr = generateSecretKeyString();
//        try{
//            KeyGenerator desGen = KeyGenerator.getInstance("DES");
//            SecretKey desKey = desGen.generateKey();
//            return desKey;
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String encrypt(String plainText){
//        if(plainText == null || plainText.length() == 0){
//            return plainText;
//        }
//        byte[] b = plainText.getBytes();
//        try{
//            Cipher cipher = Cipher.getInstance("DES");
//            cipher.init(Cipher.ENCRYPT_MODE,key);
//            byte[] cipherText = cipher.doFinal(b);
//            Base64 encoder = new Base64();
//            byte[] charText = encoder.encode(cipherText);
//            return new String(charText);
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String decrypt(String cipherText){
//        if(cipherText == null || cipherText.length() == 0){
//            return cipherText;
//        }
//        byte[] b = cipherText.getBytes();
//        try{
//            Base64 decoder = new Base64();
//            byte[] decodedBytes = decoder.decode(b);
//            Cipher cipher = Cipher.getInstance("DES");
//            cipher.init(Cipher.DECRYPT_MODE,key);
//            byte[] plainText = cipher.doFinal(decodedBytes);
//            return new String(plainText);
//        }catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args){
//        String secretKey = "m8dwhZQB7+Y=";
//        System.out.println("Generated key is " + secretKey);
//        init(secretKey);
//        String plainText = "lincoln9";
//        String cipherText = encrypt(plainText);
//        System.out.println("Encrypted string is " + cipherText);
//        plainText = decrypt("fDZwjb+GDcXhiOwEnD5pLA==");
//        System.out.println("Decrypted String is " + plainText);
//        
//        //collection ordering test
//        LinkedHashSet set = new LinkedHashSet();
//        set.add(2);
//        set.add(3);
//        set.add(4);
//        set.add(3);
//        set.add(4);
//        set.add(5);
//        
//        LinkedList list = new LinkedList();
//        list.addAll(set);
//        
//        System.out.println(set);
//        System.out.println(list);
//    }
//}
