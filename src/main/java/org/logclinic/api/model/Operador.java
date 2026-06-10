package org.logclinic.api.model;

public class Operador {
    // Variáveis que espelham exatamente as colunas do seu banco de dados
    private int id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private String setor;
    private String perfil;

    // Construtor vazio (importante para o Java instanciar o objeto depois)
    public Operador() {
    }

    // Construtor completo para quando precisarmos criar um operador com dados
    public Operador(int id, String nome, String cpf, String login, String senha, String setor, String perfil) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
        this.setor = setor;
        this.perfil = perfil;
    }

    // Métodos Getters e Setters (para o Java conseguir ler e alterar os valores)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}