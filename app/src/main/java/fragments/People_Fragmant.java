package fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import DATABASE_HANDLER.DatabaseHandler;
import INTERFACE.Database_value;
import adapter.AlphabetListAdapter;
import adapter.Header_list_view_adapter;
import adapter.adapter_for_people;
import sponj.sponj.R;
import sponj.sponj.Search_Result_Screen;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link People_Fragmant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link People_Fragmant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class People_Fragmant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<String> listDataHeader;
    Header_list_view_adapter listAdapter;
    HashMap<String, List<String>> listDataChild;
    private OnFragmentInteractionListener mListener;
    DatabaseHandler db;
    ListView lv;
    ArrayList<HashMap<String, String>> Get_name = new ArrayList<HashMap<String, String>>();
    private adapter_for_people adapter = new adapter_for_people();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;

    public People_Fragmant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment People_Fragmant.
     */
    // TODO: Rename and change types and number of parameters
    public static People_Fragmant newInstance(String param1, String param2) {
        People_Fragmant fragment = new People_Fragmant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Database_value> Namee = db.get_Name();
        if (Namee.size() != 0) {
            ArrayList<String> list = new ArrayList<String>();
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            lv = (ListView) view.findViewById(R.id.lv);
            for (Database_value AV : Namee) {
                HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                String name = AV.getName();

                list.add(name);

            }

            Collections.sort(list);
            listAdapter = new Header_list_view_adapter(getActivity(), listDataHeader, listDataChild);
            List<adapter_for_people.Row> rows = new ArrayList<adapter_for_people.Row>();
            int start = 0;
            int end = 0;
            String previousLetter = null;
            Object[] tmpIndexItem = null;
            Pattern numberPattern = Pattern.compile("[0-9]");
            try {


                for (String country : list) {
                    String firstLetter = country.substring(0, 1);

                    // Group numbers together in the scroller
//                if (numberPattern.matcher(firstLetter).matches()) {
//                    firstLetter = "#";
//                }

                    // If we've changed to a new letter, add the previous letter to the alphabet scroller
                    if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                        end = rows.size() - 1;
                        tmpIndexItem = new Object[3];
                        tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                        tmpIndexItem[1] = start;
                        tmpIndexItem[2] = end;
                        alphabet.add(tmpIndexItem);

                        start = end + 1;
                    }

                    // Check if we need to add a header row
                    if (!firstLetter.equals(previousLetter)) {
                        rows.add(new adapter_for_people.Section(firstLetter));
                        sections.put(firstLetter, start);
                    }

                    // Add the country to the list
                    rows.add(new adapter_for_people.Item(country));
                    previousLetter = firstLetter;
                }

                if (previousLetter != null) {
                    // Save the last letter
                    tmpIndexItem = new Object[3];
                    tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                    tmpIndexItem[1] = start;
                    tmpIndexItem[2] = rows.size() - 1;
                    alphabet.add(tmpIndexItem);
                }

                adapter.setRows(rows);
                // setting list adapter
                lv.setAdapter(adapter);
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
//            lv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent  i1 = new Intent(getActivity(),Search_Result_Screen.class);
//                    getActivity().startActivity(i1);
//                }
//            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i1 = new Intent(getActivity(), Search_Result_Screen.class);
                    getActivity().startActivity(i1);
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people__fragmant, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
