package org.logclinic.api.model;

public class Medico {
    private int id;
    private String nome;
    private String crm;
    private String crm_uf;
    private String especialidade;
    private String telefone;

    public Medico() {}

    public Medico(int id, String nome, String crm, String crm_uf, String especialidade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.crm_uf = crm_uf;   
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; 
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome;
   }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm;
    }

    public String getCrm_uf() { return crm_uf; }
    public void setCrm_uf(String crm_uf) { this.crm_uf = crm_uf;}

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade;}

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
}
