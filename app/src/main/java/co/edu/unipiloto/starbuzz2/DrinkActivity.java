package co.edu.unipiloto.starbuzz2;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrinkActivity extends AppCompatActivity {

    public final static String EXTRA_DRINKID = "drinkId";
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drink);

        // Obtener el ID de la bebida
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        TextView nameTextView = findViewById(R.id.name);
        TextView descriptionTextView = findViewById(R.id.description);
        ImageView photoImageView = findViewById(R.id.photo);


        /*Drink drink = Drink.bebidas[drinkId];

        // Establecer el nombre de la bebida
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getNombre());

        // Establecer la descripción de la bebida
        TextView descripcion = (TextView) findViewById(R.id.description);
        descripcion.setText(drink.getDescripcion());  // Aquí ahora es la descripción correcta

        // Establecer la imagen de la bebida
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImagenRecursoId());
        photo.setContentDescription(drink.getNombre());*/

        SQLiteOpenHelper dbHelper  = new StarbuzzDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[]{Integer.toString(drinkId)}, null, null, null);
            //registro del cursor
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int imageResourceId = cursor.getInt(2);

                nameTextView.setText(name);
                descriptionTextView.setText(description);
                photoImageView.setImageResource(imageResourceId);
            }
            cursor.close();
        } catch (SQLException d) {
            //Log.d("DrinkActivity", "Nombre: " + name + ", Descripción: " + description + ", Image ID: " + imageResourceId);

            Toast.makeText(this, "Base de datos no disponible", Toast.LENGTH_SHORT).show();
        }



        // Ajuste de los Insets para bordes de pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
}