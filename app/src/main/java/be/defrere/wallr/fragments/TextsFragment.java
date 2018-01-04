package be.defrere.wallr.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextsFragment extends Fragment {

    private AppDatabase db;
    private List<Text> texts;
    private Toolbar toolbar;
    private ListView lv;

    public TextsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_texts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateFragment(int eventid) {
        getActivity().setContentView(R.layout.activity_texts);
        lv = getActivity().findViewById(R.id.texts_listview);
        toolbar = getActivity().findViewById(R.id.toolbar);

        db = AppDatabase.getAppDatabase(getActivity());
        texts = db.textDao().findByEvent(eventid);

        System.out.println(texts.size());

        Text[] ev = texts.toArray(new Text[texts.size()]);

        lv.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.listview_text, R.id.text_listview_label, ev));
    }
}
