package com.example.telemetria;

public class extintor {
    private String id;
    private String aula;
    private String marca;
    private int dia;
    private int mes;
    private int ano;
    private int capacidad;
    private int porcentaje;

    public extintor(String id, String aula, String marca, int dia, int mes, int ano, int capacidad, int porcentaje){
        this.id = id;
        this.aula = aula;
        this.marca = marca;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.capacidad = capacidad;
        this.porcentaje = porcentaje;
    }

    public String getAula(){
        return aula;
    }

    public String getMarca() {
        return marca;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public String getId() {
        return id;
    }
}