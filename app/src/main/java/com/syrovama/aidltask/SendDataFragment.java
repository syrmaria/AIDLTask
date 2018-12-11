package com.syrovama.aidltask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SendDataFragment extends Fragment {
    private static final String TAG = "SendDataFragment";
    private EditText mEditText;
    private Button mSaveButton;

    public interface Callback {
        void onSendRequested(String text);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send, container, false);
        mEditText = v.findViewById(R.id.editText);
        mSaveButton = v.findViewById(R.id.sendButton);
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Callback)getActivity()).onSendRequested(mEditText.getText().toString());
            }
        });
    }
}
