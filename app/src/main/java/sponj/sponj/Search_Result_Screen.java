package sponj.sponj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import DATABASE_HANDLER.DatabaseHandler;
import INTERFACE.Database_value;
import fragments.Previous_date;


public class Search_Result_Screen extends Activity {
    ListView LV_Search_result;
    ArrayList<HashMap<String, String>> Get_name = new ArrayList<HashMap<String, String>>();
    DatabaseHandler db;
    TextView Txt_Header;
    ImageView Img_back;
    Typeface type;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__result__screen);
        db = new DatabaseHandler(Search_Result_Screen.this);
        LV_Search_result = (ListView) findViewById(R.id.Rlv_lv);
        Txt_Header = (TextView) findViewById(R.id.textView44);
        Img_back = (ImageView) findViewById(R.id.imageView7);
        type = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        String Value_;
        final String Check_screen;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                Value_ = null;
                Check_screen = null;
            } else {
                Value_ = extras.getString("DATE_ID");
                Check_screen = extras.getString("Check_screen");
            }
        } else {
            Value_ = (String) savedInstanceState.getSerializable("DATE_ID");
            Check_screen = (String) savedInstanceState.getSerializable("Check_screen");
        }
        Txt_Header.setTypeface(type);
        Txt_Header.setText(Value_);
        if (Check_screen.contentEquals("#Datewise")) {
            String Date__ = extras.getString("date");
            String Month_name_ = extras.getString("monthname");
            String Year_ = extras.getString("year");
            String Strng_notes = db.GET_NOTES_BY_DATE(Value_);
            HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
            Hash_map_fetch.put("Notes", Strng_notes);
            Hash_map_fetch.put("Date", Value_);
            Hash_map_fetch.put("Month_", Month_name_);
            Hash_map_fetch.put("Year_", Year_);
            Hash_map_fetch.put("Date_", Date__);


            // adding contact to contact list
            Get_name.add(Hash_map_fetch);
        } else {


            List<Database_value> Namee = db.get_All_notes();


            for (Database_value AV : Namee) {
                HashMap<String, String> Hash_map_fetch = new HashMap<String, String>();
                String Notes = AV.get_TITTLE();
                String Datet = AV.get_DATE();
                String Date_with_id = AV.get_DATE_with_month();

//                try {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyy");
//                    Date d = dateFormat.parse(Datet);
//                     d.getMonth();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                try {


                    String[] separated = Value_.split(" ");
                    String Str_1 = separated[0]; // this will contain "Fruit"
                    String STR_2 = separated[1];
                    String STR_3 = separated[2];
                    if (Notes.contains(Str_1)||Notes.contains(STR_3)) {
                        Hash_map_fetch.put("Notes", Notes);
                        Hash_map_fetch.put("Date", Datet);
                        Hash_map_fetch.put("Date_with_id", Date_with_id);


                        // adding contact to contact list
                        if (Get_name.contains(Datet)) {

                        } else {
                            Get_name.add(Hash_map_fetch);
                        }

                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    if (Notes.contains(Value_)) {
                        Hash_map_fetch.put("Notes", Notes);
                        Hash_map_fetch.put("Date", Datet);
                        Hash_map_fetch.put("Date_with_id", Date_with_id);


                        // adding contact to contact list
                        if (Get_name.contains(Datet)) {

                        } else {
                            Get_name.add(Hash_map_fetch);
                        }

                    }
                }


            }
        }
        ListView_adapter_local adapter = new ListView_adapter_local(Search_Result_Screen.this,
                Get_name
        );
        LV_Search_result.setAdapter(adapter);
        LV_Search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Check_screen.contentEquals("#Datewise")) {
                    String date = Get_name.get(position).get("Date");
                    String Month__ = Get_name.get(position).get("Month_");
                    String Year__ = Get_name.get(position).get("Year_");
                    String date_ = Get_name.get(position).get("Date_");
                    //Toast.makeText(Search_Result_Screen.this,date,Toast.LENGTH_LONG).show();
                    SharedPreferences shared = getSharedPreferences("Sponj", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Month_name", Month__);
                    editor.putString("Date", date_);
                    editor.putString("Year_", Year__);

                    editor.commit();
                    Intent i1 = new Intent(Search_Result_Screen.this, MainActivity.class);
                    startActivity(i1);
                } else {
                    String Date_with = Get_name.get(position).get("Date_with_id");

                    String[] separated = Date_with.split("/");
                    String Str_DATE = separated[0]; // this will contain "Fruit"
                    String STR_month = separated[1];
                    String STR_Year = separated[2];
                    int int_month = Integer.parseInt(STR_month);
                    int_month = int_month - 1;
                    STR_month = String.valueOf(int_month);
                    SharedPreferences shared = getSharedPreferences("Sponj", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("Month_name", STR_month);
                    editor.putString("Date", Str_DATE);
                    editor.putString("Year_", STR_Year);

                    editor.commit();
                    Intent i1 = new Intent(Search_Result_Screen.this, MainActivity.class);
                    startActivity(i1);
//                    try {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyy");
//                    Date d = dateFormat.parse(Date_with);
//                     d.getMonth();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                }
                //finish();

            }
        });
        // adapter.getFilter().filter("nirmal");
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_screen.contentEquals("Topic")){
                    Intent i = new Intent(Search_Result_Screen.this, MainActivity.class);

                    i.putExtra("Open_people", "Topic");
                    startActivity(i);
                    finish();
                }else if(Check_screen.contentEquals("people")){
                    Intent i = new Intent(Search_Result_Screen.this, MainActivity.class);

                    i.putExtra("Open_people", "people");
                    startActivity(i);
                    finish();
                }else{
                    finish();
                }

            }
        });

    }


    public class ListView_adapter_local extends BaseAdapter implements Filterable {
        Context context;
        private ValueFilter valueFilter;
        private ArrayList<Contacts> mStringFilterList;
        private ArrayList<Contacts> _Contacts;
        ArrayList<HashMap<String, String>> Name_list = new ArrayList<HashMap<String, String>>();

        public ListView_adapter_local(Context context, ArrayList<HashMap<String, String>> Name_list) {
            this.context = context;
            this.Name_list = Name_list;
        }


        /*private view holder class*/
        public class ViewHolder {


            public TextView Tittle;
            public TextView Date;
            public Button Delete;


        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.search_lv_result, null);
                holder = new ViewHolder();


                holder.Tittle = (TextView) convertView.findViewById(R.id.textView7);
                holder.Date = (TextView) convertView.findViewById(R.id.textView6);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.Tittle.setText((Name_list.get(position).get("Notes")));
            holder.Date.setText((Name_list.get(position).get("Date")));
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

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {

                valueFilter = new ValueFilter();
            }

            return valueFilter;
        }

        private class ValueFilter extends Filter {

            //Invoked in a worker thread to filter the data according to the constraint.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<Contacts> filterList = new ArrayList<Contacts>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).getName().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            Contacts contacts = new Contacts();
                            contacts.setName(mStringFilterList.get(i).getName());
                            contacts.setId(mStringFilterList.get(i).getId());
                            filterList.add(contacts);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;
            }


            //Invoked in the UI thread to publish the filtering results in the user interface.
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                _Contacts = (ArrayList<Contacts>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    public class Contacts {

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


    //Invoked in the UI thread to publish the filtering results in the user interface.


}
