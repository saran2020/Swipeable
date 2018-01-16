package app.test.my.swipable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Saran Sankaran on 1/16/18.
 */

public class MessageFragment extends android.support.v4.app.Fragment {

    private TextView textMessage;

    public static MessageFragment newInstance(final String message) {

        Bundle args = new Bundle();
        args.putString("MESSAGE", message);

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_message, container, false);

        String message = getArguments().getString("MESSAGE", "Default");

        textMessage = view.findViewById(R.id.text_message);
        textMessage.setText(message);

        return view;
    }
}
