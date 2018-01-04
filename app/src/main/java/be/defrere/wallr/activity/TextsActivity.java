package be.defrere.wallr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Text;

public class TextsActivity extends AppCompatActivity {

    private AppDatabase db;
    private List<Text> texts;
    private Toolbar toolbar;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texts);
        lv = findViewById(R.id.texts_listview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getAppDatabase(this);
        texts = db.textDao().findByEvent(Integer.parseInt(getIntent().getExtras().get("eventid").toString()));

        System.out.println(texts.size());

        Text[] ev = texts.toArray(new Text[texts.size()]);

        lv.setAdapter(new ArrayAdapter<>(this, R.layout.listview_text, R.id.text_listview_label, ev));
    }
}
