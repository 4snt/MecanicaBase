package infra.crypto;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptCrypto {
    private static final String SECRET_KEY = "minha-chave-super-secreta";
    private static final String ALGORITHM = "PBEWithMD5AndDES";

    private static final StandardPBEStringEncryptor encryptor;

    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_KEY);
        encryptor.setAlgorithm(ALGORITHM);
    }

    /**
     * Criptografa um texto em formato seguro
     */
    public static String encrypt(String texto) {
        return encryptor.encrypt(texto);
    }

    /**
     * Descriptografa um texto criptografado
     */
    public static String decrypt(String textoCriptografado) {
        return encryptor.decrypt(textoCriptografado);
    }

    /**
     * Compara um texto plano com um valor criptografado
     */
    public static boolean compareTo(String textoPlano, String textoCriptografado) {
        try {
            String decrypted = decrypt(textoCriptografado);
            return decrypted.equals(textoPlano);
        } catch (Exception e) {
            return false;
        }
    }
}
