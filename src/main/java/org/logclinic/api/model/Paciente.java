package org.logclinic.api.model;

public class Paciente {
    private int id;
    private String nome;
    private String data_nascimento;
    private String genero; // <-- NOVO
    private String cpf;
    private String telefone;
    private String email;
    private String empresa;
    private String logradouro;
    private String bairro;
    private String cidade; // <-- NOVO
    private String numero;
    private String complemento;
    private String cep;
    private String uf;     // <-- NOVO
    private int convenio_id;
    private String data_cadastro;

    // Construtor vazio
    public Paciente() {
    }

    // Construtor completo atualizado
    public Paciente(int id, String nome, String data_nascimento, String genero, String cpf, String telefone, String email,
                    String empresa, String logradouro, String bairro, String cidade, String numero, String complemento, 
                    String cep, String uf, int convenio_id, String data_cadastro) {
        this.id = id;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.genero = genero;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.empresa = empresa;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.uf = uf;
        this.convenio_id = convenio_id;
        this.data_cadastro = data_cadastro;
    }

    // Getters e Setters 
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataNascimento() { return data_nascimento; }
    public void setDataNascimento(String data_nascimento) { this.data_nascimento = data_nascimento; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public int getConvenioId() { return convenio_id; }
    public void setConvenioId(int convenio_id) { this.convenio_id = convenio_id; }

    public String getDataCadastro() { return data_cadastro; }
    public void setDataCadastro(String data_cadastro) { this.data_cadastro = data_cadastro; }
}