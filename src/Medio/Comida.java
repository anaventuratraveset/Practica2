package Medio;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class Comida {

    String stringToRepresentAtributos;
    float cantidadInicial;
    static float [] cantidadComida;
    LocalDate fechaInicial;
    LocalDate fechaFinal;
    float cantidadPico;
    float cantidadFinal;
    LocalDate fechaPico;

    final int duracion=30;

    public Comida(float cantidadInicial, float [] cantidadComida, LocalDate fechaInicial,
                  LocalDate fechaFinal, float cantidadPico, float cantidadFinal, LocalDate fechaPico) {
        this.cantidadInicial = cantidadInicial;
        this.cantidadComida = cantidadComida;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.cantidadPico=cantidadPico;
        this.cantidadFinal=cantidadFinal;
        this.fechaPico=fechaPico;
    }

    public float getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(float cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public float[] getCantidadComida() {
        return this.cantidadComida;
    }

    public void setCantidadComida(float[] cantidadComida) {
        this.cantidadComida = cantidadComida;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public float getCantidadPico() {
        return cantidadPico;
    }

    public void setCantidadPico(float cantidadPico) {
        this.cantidadPico = cantidadPico;
    }

    public float getCantidadFinal() {
        return cantidadFinal;
    }

    public void setCantidadFinal(float cantidadFinal) {
        this.cantidadFinal = cantidadFinal;
    }

    public LocalDate getFechaPico() {
        return fechaPico;
    }

    public void setFechaPico(LocalDate fechaPico) {
        this.fechaPico = fechaPico;
    }

    public float[] calcularComida(){
            int diasIncremento =(int) DAYS.between(this.fechaInicial, this.fechaPico);
    int diasDecremento = (int) DAYS.between(fechaPico, fechaFinal);
    float interseccion= cantidadPico-((cantidadPico-this.cantidadFinal)/diasDecremento);//funcion f(x)= a*x+b,
    // en este caso a es negativo pq decremento y b es lo que he llamado intersección
    float CantidadIncremento= this.cantidadPico - this.cantidadInicial;
    float CantidadDecremento= this.cantidadPico - this.cantidadFinal;
        for (int i=1; i<=diasIncremento; i++){
        cantidadComida[i]=((this.cantidadPico-this.cantidadInicial)/diasIncremento)*i+this.cantidadInicial;
    }
           for(int j=diasIncremento+1; j<30; j++){
        cantidadComida[j]= ((cantidadPico-this.cantidadFinal)/diasDecremento)*j+interseccion;
    }
        return cantidadComida;
    }

    @Override
    public String toString(){
        String stringToRepresentDosisComida= "Dosis de comida para cada día del experimento: "
                + Arrays.toString(cantidadComida);//para representar la cantidad de comida de cada día
        return stringToRepresentDosisComida;
    }
}