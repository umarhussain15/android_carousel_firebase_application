package carousel.teamo.com.carouselapplication;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroductionFragment extends Fragment {

    private static final String ARG_HEADING ="heading" ;
    private static final String ARG_DESCRIPTION ="description" ;
    private static final String ARG_ID = "drawable";
    private OnFragmentInteractionListener mListener;

    private ImageView imageView;
    private TextView heading, description;

    public IntroductionFragment() {}
    public static IntroductionFragment newInstance(String heading, String description,int drawableId) {
        IntroductionFragment fragment = new IntroductionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HEADING, heading);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_ID, drawableId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String text1=getArguments().getString("heading");
        String text2=getArguments().getString("description");
        int drawableId= getArguments().getInt("drawable");
        View view=inflater.inflate(R.layout.fragment_introduction, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(drawableId);
        heading= (TextView) view.findViewById(R.id.heading);
        heading.setText(text1);
        description = (TextView) view.findViewById(R.id.description);
        description.setText(text2);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
