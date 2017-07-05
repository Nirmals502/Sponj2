package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import DATABASE_HANDLER.DatabaseHandler;
import INTERFACE.Database_value;
import sponj.sponj.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Future_date.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Future_date#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Future_date extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText EdtText;
    String Name_value = "false";
    String Name_key = "";
    StringBuilder sb = new StringBuilder();
    DatabaseHandler db;
    ArrayList<HashMap<String, String>> Get_name = new ArrayList<HashMap<String, String>>();
    private OnFragmentInteractionListener mListener;
    ListView Lv_lookup;
    String Check_value = "";
    String Str_notes;
    String Date_id;
    String Str_Enter_captured = "false";
    String Str_lvSelected_item = "false";
    String Str_value_on_listviewclick;
    SharedPreferences shared;

    public Future_date() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Future_date.
     */
    // TODO: Rename and change types and number of parameters
    public static Future_date newInstance(String param1, String param2) {
        Future_date fragment = new Future_date();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EdtText = (EditText) view.findViewById(R.id.Edttext_line);
        Lv_lookup = (ListView) view.findViewById(R.id.Lv_lookup);
        Calendar sCalendar = Calendar.getInstance();
        shared = getActivity().getSharedPreferences("Sponj", getActivity().MODE_PRIVATE);
        String Str_Month = (shared.getString("Month_name", "nodata"));

        if (!Str_Month.contentEquals("nodata")) {
            String Str_Date = (shared.getString("Date", "nodata"));
            String Str_Year = (shared.getString("Year_", "nodata"));
            int int_month = Integer.parseInt(Str_Month);
            int int_date = Integer.parseInt(Str_Date);
            int int_Year = Integer.parseInt(Str_Year);
            sCalendar.set(Calendar.MONTH, int_month);
            sCalendar.set(Calendar.DATE, int_date);
            sCalendar.set(Calendar.YEAR, int_Year);

        }
        sCalendar.add(Calendar.DATE, +1);
        int Date = sCalendar.get(Calendar.MONTH);
        int day = sCalendar.get(Calendar.DAY_OF_MONTH);
        int year = sCalendar.get(Calendar.YEAR);
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(), "NotoSerif-Regular.ttf");
        EdtText.setTypeface(type2);
        Date = Date+1;
        String Str_to_date = String.valueOf(Date);
        String Str_to_year = String.valueOf(year);
        String Str_to_day = String.valueOf(day);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(sCalendar.getTime());
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//        Txt_day.setText(dayLongName);
//        Txt_Date.setText(Str_to_day);
//        Txt_Mont_year.setText(month_name + " " + Str_to_year);
        Date_id = Str_to_day + month_name + Str_to_year;
        String Check_database_value = db.Check_Date(Date_id);
        String StrG_Date_ID = Str_to_day + "/" + Str_to_date + "/" + Str_to_year;
        if (Check_database_value.contentEquals("")) {
            db.Save_notes("", Date_id, StrG_Date_ID);
        }


        //db.Save_notes("", Date_id);

        final String Strng_notes = db.GET_NOTES_BY_DATE(Date_id);
        EdtText.setText(Strng_notes);
        EdtText.setSelection(EdtText.getText().length());
        EdtText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.i("event", "captured");
                    Str_Enter_captured = "true";

                    return false;
                }

                return false;
            }
        });
        EdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //db.Save_notes(s.toString(), Date_id);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String last = s.toString();

                try {
                    Lv_lookup.setVisibility(View.GONE);

                    last = last.substring(last.length() - 1);
                    if (Name_value.contentEquals("True")) {
                        Name_key = Name_key + last;
                        //  sb.append(last);
                    }
                    if (last.toString().contentEquals("@")) {
                        Check_value = "@";
                        Get_name.clear();
                        List<Database_value> Namee = db.get_Name();


                        for (Database_value AV : Namee) {
                            HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                            String name = AV.getName();


                            Hash_map_fetch.put("Name", name);


                            // adding contact to contact list
                            Get_name.add(Hash_map_fetch);


                        }
                        ListView_adapter_local adapter = new ListView_adapter_local(getActivity(),
                                Get_name
                        );
                        Lv_lookup.setAdapter(adapter);
                        if (Namee.size() != 0) {
                            Lv_lookup.setVisibility(View.VISIBLE);
                        }
//                    String Strt = String.valueOf(before);
//                    Toast.makeText(getActivity(),Strt,Toast.LENGTH_LONG).show();
//                    Name_value=Name+last;
                        Name_value = "True";
                        // Dialog_View_edit();

                    } else if (last.toString().contentEquals("#")) {
                        Check_value = "#";
                        Get_name.clear();
                        List<Database_value> Namee = db.get_Topic_name();

                        for (Database_value AV : Namee) {
                            HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                            String name = AV.getName();


                            Hash_map_fetch.put("Name", name);


                            // adding contact to contact list
                            Get_name.add(Hash_map_fetch);


                        }
                        ListView_adapter_local adapter = new ListView_adapter_local(getActivity(),
                                Get_name
                        );
                        Lv_lookup.setAdapter(adapter);
                        if (Namee.size() != 0) {
                            Lv_lookup.setVisibility(View.VISIBLE);
                        }

                        Name_value = "True";

                    } else if (last.toString().contains(" ")) {
                        Name_value = "false";
                        if (Str_lvSelected_item.contentEquals("true")) {
                            Name_key = Str_value_on_listviewclick;
                            Str_lvSelected_item = "false";
                        }
                        if (Name_key.contentEquals("") || Name_key.contentEquals(" ")) {

                        } else {
                            if (Check_value.contentEquals("@")) {
                                String Str_Value_Exist = "";
                                List<Database_value> Namee = db.get_Name();
                                for (Database_value AV : Namee) {
                                    HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                                    String name = AV.getName();
                                    if (name.contentEquals(Name_key)) {
                                        Str_Value_Exist = "true";
                                    }


                                }

                                if (!Str_Value_Exist.contentEquals("true")) {
                                    db.Save(Name_key);
                                }

                            } else if (Check_value.contentEquals("#")) {
                                String Str_Value_Exist = "";
                                List<Database_value> Namee = db.get_Topic_name();
                                for (Database_value AV : Namee) {
                                    HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                                    String name = AV.getName();
                                    if (name.contentEquals(Name_key)) {
                                        Str_Value_Exist = "true";
                                    }


                                }

                                if (!Str_Value_Exist.contentEquals("true")) {
                                    db.Save_Topic(Name_key);
                                }
                            }

                        }


                        //Toast.makeText(getActivity(), Name_key, Toast.LENGTH_LONG).show();
                        Name_key = "";
                    } else if (Str_Enter_captured.contentEquals("true")) {
                        Name_value = "false";
                        if (Str_lvSelected_item.contentEquals("true")) {
                            Name_key = Str_value_on_listviewclick;
                            Str_lvSelected_item = "false";
                        }
                        if (Name_key.contentEquals("") || Name_key.contentEquals(" ")) {
                            //  Toast.makeText(getActivity(), "value Exist", Toast.LENGTH_LONG).show();
                        } else {
                            if (Check_value.contentEquals("@")) {
                                String Str_Value_Exist = "";
                                List<Database_value> Namee = db.get_Name();
                                for (Database_value AV : Namee) {
                                    HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                                    String name = AV.getName();
                                    if (name.contentEquals(Name_key)) {
                                        Str_Value_Exist = "true";
                                    }


                                }

                                if (!Str_Value_Exist.contentEquals("true")) {
                                    Name_key = Name_key.replaceAll("\n", "");
                                    db.Save(Name_key);
                                }

                            } else if (Check_value.contentEquals("#")) {
                                String Str_Value_Exist = "";
                                List<Database_value> Namee = db.get_Topic_name();
                                for (Database_value AV : Namee) {
                                    HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                                    String name = AV.getName();
                                    if (name.contentEquals(Name_key)) {
                                        Str_Value_Exist = "true";
                                    }


                                }

                                if (!Str_Value_Exist.contentEquals("true")) {

                                    Name_key = Name_key.replaceAll("\n", "");
                                    db.Save_Topic(Name_key);
                                }
                            }
                        }


                        //Toast.makeText(getActivity(), Name_key, Toast.LENGTH_LONG).show();
                        Name_key = "";
                        Str_Enter_captured = "false";
                    }
                    System.out.println("last character: " + last);
                } catch (java.lang.StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Str_notes = Str_notes;
                //  db.Save_notes("", Date_id);
                if (!Strng_notes.contentEquals(s.toString())) {
                    db.update_Notes(s.toString(), Date_id);
                }

                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

            }

        });
        Lv_lookup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Str_edttxt_txt = EdtText.getText().toString();
                String Str_Lv_item = Get_name.get(position).get("Name");
                EdtText.setText(Str_edttxt_txt + Str_Lv_item);
                EdtText.setSelection(EdtText.getText().length());
                Str_lvSelected_item = "true";
                Str_value_on_listviewclick = Str_Lv_item;
                Lv_lookup.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future_date, container, false);
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

    public static Future_date newInstance(String text) {

        Future_date f = new Future_date();
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

    public class ListView_adapter_local extends BaseAdapter {
        Context context;

        ArrayList<HashMap<String, String>> Name_list = new ArrayList<HashMap<String, String>>();

        public ListView_adapter_local(Context context, ArrayList<HashMap<String, String>> Name_list) {
            this.context = context;
            this.Name_list = Name_list;
        }

        /*private view holder class*/
        public class ViewHolder {


            public TextView Tittle;
            public Button Delete;


        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.name_list_adapter, null);
                holder = new ViewHolder();


                holder.Tittle = (TextView) convertView.findViewById(R.id.textView2);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.Tittle.setText((Name_list.get(position).get("Name")));

            return convertView;
        }

        @Override
        public int getCount() {
            return Name_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

    private void Dialog_View_edit() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(getActivity(), R.style.DialoueBox);

        dialog.setContentView(R.layout.layout_for_name_list);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        Get_name.clear();


        RelativeLayout RLV_popup = (RelativeLayout) dialog.findViewById(R.id.rLV_HOLDER);


        ListView Lv = (ListView) dialog.findViewById(R.id.LV);

        List<Database_value> Name = db.get_Name();

        for (Database_value AV : Name) {
            HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
            String name = AV.getName();


            Hash_map_fetch.put("Name", name);


            // adding contact to contact list
            Get_name.add(Hash_map_fetch);


        }
        ListView_adapter_local adapter = new ListView_adapter_local(getActivity(),
                Get_name
        );
        Lv.setAdapter(adapter);

        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();

            }
        });

        // adding each child node to HashMap key => value


    }

}
