package org.logclinic.api.model;

public class Servico {
    private int id;
    private String nome;
    private double valor; // double mapeia perfeitamente o DECIMAL do MySQL para o Java

    public Servico() {}

    public Servico(int id, String nome, double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}

    