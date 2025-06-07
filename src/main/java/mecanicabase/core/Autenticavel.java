// src/main/java/mecanicabase/core/Autenticavel.java
package mecanicabase.core;

public interface Autenticavel {

    String getEmail();

    boolean compararSenha(String senha);
}
