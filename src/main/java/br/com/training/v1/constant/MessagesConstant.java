package br.com.training.v1.constant;

public enum MessagesConstant {
    ERROR_GENERIC("Internal error identified. Contact support."),
    ERROR_USER_NOT_FOUND("User not found."),
    ERROR_USER_ALREADY_REGISTERED("User already has registration."),
    ERROR_USER_CPF_REGISTERED("CPF already registered."),
    ERROR_USER_EMAIL_REGISTERED("Email already registered."),
    ERROR_ID_INFORMED("Id cannot be entered in the registration operation..");

    private final String valor;

    MessagesConstant(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
