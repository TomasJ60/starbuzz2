package co.edu.unipiloto.starbuzz2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrinkCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        // ListView de bebidas
        ListView listaBebidas = (ListView) findViewById(R.id.lista_bebidas);

        // DrinkDatabaseHelper
        StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            // Adaptador
            if (cursor != null && cursor.getCount() > 0) {
                SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0
                );
                listaBebidas.setAdapter(listAdapter);
                }

        }catch (SQLException e) {
            Log.e("StarbuzzDatabaseHelper", "Database unavailable", e);
            Toast.makeText(this, "Base de datos no disponible", Toast.LENGTH_SHORT).show();
        }


        // Listener para manejar los clics en los elementos de la lista
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listaBebidas, View itemView, int position, long id) {
                Log.d("DrinkCategoryActivity", "Bebida seleccionada ID: " + id);
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }

        };

        // Establecer el listener en el ListView
        listaBebidas.setOnItemClickListener(itemClickListener);

        // Ajustar los insets para bordes de pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}