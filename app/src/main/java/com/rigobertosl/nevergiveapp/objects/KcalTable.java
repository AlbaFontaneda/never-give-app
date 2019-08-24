package com.rigobertosl.nevergiveapp.objects;

public class KcalTable {
    private long id;
    private boolean pasta, huevos, leche, carne, pescado, verdura, bolleria, cereales, legumbre, embutido, queso, yogurt;


    public KcalTable(long id, boolean pasta, boolean huevos, boolean leche, boolean carne, boolean pescado, boolean verdura, boolean bolleria, boolean cereales, boolean legumbre, boolean embutido, boolean queso, boolean yogurt) {
        this.id = id;
        this.pasta = pasta;
        this.huevos = huevos;
        this.leche = leche;
        this.carne = carne;
        this.pescado = pescado;
        this.verdura = verdura;
        this.bolleria = bolleria;
        this.cereales = cereales;
        this.legumbre = legumbre;
        this.embutido = embutido;
        this.queso = queso;
        this.yogurt = yogurt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPasta() {
        return pasta;
    }

    public void setPasta(boolean pasta) {
        this.pasta = pasta;
    }

    public boolean isHuevos() {
        return huevos;
    }

    public void setHuevos(boolean huevos) {
        this.huevos = huevos;
    }

    public boolean isLeche() {
        return leche;
    }

    public void setLeche(boolean leche) {
        this.leche = leche;
    }

    public boolean isCarne() {
        return carne;
    }

    public void setCarne(boolean carne) {
        this.carne = carne;
    }

    public boolean isPescado() {
        return pescado;
    }

    public void setPescado(boolean pescado) {
        this.pescado = pescado;
    }

    public boolean isVerdura() {
        return verdura;
    }

    public void setVerdura(boolean verdura) {
        this.verdura = verdura;
    }

    public boolean isBolleria() {
        return bolleria;
    }

    public void setBolleria(boolean bolleria) {
        this.bolleria = bolleria;
    }

    public boolean isCereales() {
        return cereales;
    }

    public void setCereales(boolean cereales) {
        this.cereales = cereales;
    }

    public boolean isLegumbre() {
        return legumbre;
    }

    public void setLegumbre(boolean legumbre) {
        this.legumbre = legumbre;
    }

    public boolean isEmbutido() {
        return embutido;
    }

    public void setEmbutido(boolean embutido) {
        this.embutido = embutido;
    }

    public boolean isQueso() {
        return queso;
    }

    public void setQueso(boolean queso) {
        this.queso = queso;
    }

    public boolean isYogurt() {
        return yogurt;
    }

    public void setYogurt(boolean yogurt) {
        this.yogurt = yogurt;
    }
}
