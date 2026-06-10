package org.logclinic.api.model;

public class Consulta {
    private int id;
    private int paciente_id;
    private int servicos_id;
    private int convenio_id;
    private String data_consulta;
    private String hora_consulta;
    private String paciente_nome; // Campo adicionado para o JOIN
    private String servico_nome; // Campo adicionado para o JOIN

    public Consulta() {}

    public Consulta(int id, int paciente_id, int servicos_id, int convenio_id, String data_consulta, String hora_consulta) {
        this.id = id;
        this.paciente_id = paciente_id;
        this.servicos_id = servicos_id;
        this.convenio_id = convenio_id;
        this.data_consulta = data_consulta;
        this.hora_consulta = hora_consulta;
    }

    public int getId() { return id; } 
    public void setId(int id) { this.id = id; }

    public int getPaciente_id() { return paciente_id; }
    public void setPaciente_id(int paciente_id) { this.paciente_id = paciente_id; }

    public int getServicos_id() { return servicos_id; }
    public void setServicos_id(int servicos_id) { this.servicos_id = servicos_id; }

    public String getServico_nome() { return servico_nome; }
    public void setServico_nome(String servico_nome) { this.servico_nome = servico_nome; }

    public int getConvenio_id() { return convenio_id; }
    public void setConvenio_id(int convenio_id) { this.convenio_id = convenio_id; }

    public String getData_consulta() { return data_consulta; }
    public void setData_consulta(String data_consulta) { this.data_consulta = data_consulta; }

    public String getHora_consulta() { return hora_consulta; }
    public void setHora_consulta(String hora_consulta) { this.hora_consulta = hora_consulta; }

    public String getPaciente_nome() { return paciente_nome; }
    public void setPaciente_nome(String paciente_nome) { this.paciente_nome = paciente_nome; }
}