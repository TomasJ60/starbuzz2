package co.edu.unipiloto.starbuzz2;

public class Drink {
    private String nombre;
    private String descripcion;
    private int imagenRecursoId;
    public static final Drink[] bebidas = {
            new Drink( "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink( "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino),
            new Drink( "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter),
    };

    public Drink(String nombre, String descripcion, int imagenRecursoId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenRecursoId = imagenRecursoId;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagenRecursoId() {
        return imagenRecursoId;
    }

    public void setImagenRecursoId(int imagenRecursoId) {
        this.imagenRecursoId = imagenRecursoId;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
