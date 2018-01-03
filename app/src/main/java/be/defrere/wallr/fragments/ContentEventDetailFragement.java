package be.defrere.wallr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.defrere.wallr.R;

public class ContentEventDetailFragement extends Fragment {

    public ContentEventDetailFragement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_event_detail, container, false);
    }

}
