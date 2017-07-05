package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import sponj.sponj.MainActivity;
import sponj.sponj.R;
import sponj.sponj.Search_Result_Screen;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Calender_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Calender_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calender_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CalendarView clnder;
    private OnFragmentInteractionListener mListener;

    public Calender_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calender_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Calender_fragment newInstance(String param1, String param2) {
        Calender_fragment fragment = new Calender_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clnder = (CalendarView) view.findViewById(R.id.calendarView);
        clnder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Date_id = Str_to_day + month_name + Str_to_year;

                String Str_to_date = String.valueOf(dayOfMonth);
                String Str_to_year = String.valueOf(year);
                String Str_Month = String.valueOf(month);
                String Str_month_name = theMonth(month);
                if(Str_month_name.contentEquals("June")){
                    Str_month_name="Jun";
                }
                String Str_Date = Str_to_date + Str_month_name + Str_to_year;
//                Intent i = new Intent(getActivity(), Search_Result_Screen.class);
//
//                i.putExtra("DATE_ID", Str_Date);
//                i.putExtra("date", Str_to_date);
//                i.putExtra("monthname", Str_Month);
//                i.putExtra("year", Str_to_year);
//                i.putExtra("Check_screen", "#Datewise");
//                getActivity().startActivity(i);

                SharedPreferences shared = getActivity().getSharedPreferences("Sponj", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Month_name", Str_Month);
                editor.putString("Date", Str_to_date);
                editor.putString("Year_", Str_to_year);

                editor.commit();
                Intent i1 = new Intent(getActivity(), MainActivity.class);
                startActivity(i1);

                // Toast.makeText(getActivity(), dayOfMonth + "/" + month + "/" + year, 4000).show();
            }
        });
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

    public static Calender_fragment newInstance(String text) {

        Calender_fragment f = new Calender_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
