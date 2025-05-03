package infra.crypto;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Classe utilitária para realizar operações de criptografia e descriptografia
 * usando Jasypt. Utiliza o algoritmo {@link PBEWithMD5AndDES} com uma chave
 * secreta configurada para criptografar e comparar senhas.
 */
public class JasyptCrypto {

    // Chave secreta utilizada para a criptografia
    private static final String SECRET_KEY = "minha-chave-super-secreta";

    // Algoritmo de criptografia a ser usado
    private static final String ALGORITHM = "PBEWithMD5AndDES";

    // Instância do objeto de criptografia
    private static final StandardPBEStringEncryptor encryptor;

    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_KEY);  // Define a chave secreta
        encryptor.setAlgorithm(ALGORITHM);  // Define o algoritmo de criptografia
    }

    /**
     * Criptografa o texto fornecido usando o algoritmo configurado.
     *
     * @param texto O texto a ser criptografado.
     * @return O texto criptografado.
     */
    public static String encrypt(String texto) {
        return encryptor.encrypt(texto);
    }

    /**
     * Descriptografa o texto criptografado usando a chave secreta configurada.
     *
     * @param textoCriptografado O texto criptografado a ser descriptografado.
     * @return O texto original, antes da criptografia.
     */
    public static String decrypt(String textoCriptografado) {
        return encryptor.decrypt(textoCriptografado);
    }

    /**
     * Compara um texto plano com um valor criptografado. Realiza a
     * descriptografia do valor criptografado e compara com o texto fornecido.
     *
     * @param textoPlano O texto plano a ser comparado.
     * @param textoCriptografado O texto criptografado a ser comparado com o
     * texto plano.
     * @return True se o texto plano for igual ao texto descriptografado, caso
     * contrário, retorna false.
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
