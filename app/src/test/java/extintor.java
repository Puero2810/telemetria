import android.media.Image;

public class extintor {
    private String marca;
    private String ubicacion;
    private Image qr;
    private int capacidad;
    private int dia;
    private int mes;
    private int ano;

    public void Extintor(String marca, String ubicacion, Image qr, int capacidad, int dia, int mes, int ano){
        this.marca = marca;
        this.ubicacion = ubicacion;
        this.qr = qr;
        this.capacidad = capacidad;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }


}
