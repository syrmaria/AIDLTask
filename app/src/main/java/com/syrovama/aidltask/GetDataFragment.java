package com.syrovama.aidltask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GetDataFragment extends Fragment {
    private static final String TAG = "GetDataFragment";
    private TextView mTextView;
    private Button mGetButton;

    public interface Callback {
        String onTextRequested();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get, container, false);
        mTextView = v.findViewById(R.id.textView);
        mGetButton = v.findViewById(R.id.getButton);
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(((Callback)getActivity()).onTextRequested());
            }
        });
    }
}
