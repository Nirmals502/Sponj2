package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import sponj.sponj.R;
import sponj.sponj.Search_Result_Screen;

public class AlphabetListAdapter extends BaseAdapter {
    int int_counter = 0;
    public static abstract class Row {
    }

    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }

    public static final class Item extends Row {
        public final String text;

        public Item(String text) {
            this.text = text;
        }
    }

    private List<Row> rows;
    ArrayList<String> list = new ArrayList<String>();

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Row getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.row_item, parent, false);
            }

            final Item item = (Item) getItem(position);
            final CheckBox Check_box = (CheckBox) view.findViewById(R.id.checkBox2);
            Check_box.setTag("" + position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(item.text);
            final String Str_Value = item.text;
            final String Str_position = String.valueOf(position);
            try {
                String Str_valuee = list.get(position);
                if (Str_valuee.contentEquals(Str_Value)) {
                    Check_box.setChecked(true);
                } else {
                    Check_box.setChecked(false);
                }
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                // Check_box.setChecked(false);
            }

            Check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Check_box.isChecked()) {
                        int_counter++;
                        if (int_counter < 4) {

//                        int_counter = 3;


                            String Strn_value;

                            //Check_box.setChecked(true);

                            int position = Integer.parseInt((String) v.getTag());
                            final String Str_Value = item.text;
                            SharedPreferences shared = v.getContext().getSharedPreferences("Sponj", v.getContext().MODE_PRIVATE);
                            String Str_Month = (shared.getString("People_name", ""));
                            if (!Str_Month.contentEquals("")) {
                                Strn_value = Str_Month + " " + "#" + Str_Value;
                            } else {
                                Strn_value = "#" + Str_Value;
                            }


                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("People_name", Strn_value);

                            editor.commit();

                            // Toast.makeText(v.getContext(), Str_Value, Toast.LENGTH_LONG).show();
                            if (int_counter == 3) {


                                Intent i = new Intent(v.getContext(), Search_Result_Screen.class);

                                i.putExtra("DATE_ID", Strn_value);
                                i.putExtra("Check_screen", "Topic");
                                v.getContext().startActivity(i);
                                editor.clear();
                                editor.commit();
//                                list.clear();
//                                notifyDataSetChanged();
//                                for (int i1 = 0; i1 < rows.size(); i1++) {
//                                    //View mChild = mListView.getChildAt(i1);
//
//                                    //Replace R.id.checkbox with the id of CheckBox in your layout
//
//                                    Check_box.setChecked(false);
//                                }
                            }
                        } else {
                            Toast.makeText(v.getContext(), "you can  select only 3 ", Toast.LENGTH_LONG).show();
                            Check_box.setChecked(false);
                            int_counter = 3;
                        }
                    } else {
                        int_counter--;
                    }
//                    final String Str_position = String.valueOf(position);
                    try {
                        String Str_Check_position = list.get(position);
                        if (Str_Check_position.contentEquals(Str_Value)) {

                            //int position = Integer.parseInt((String) v.getTag());

                            //int Int_position = Integer.parseInt(Str_position);
                            int pos = list.indexOf(Str_Value);
                            list.remove(pos);

                        } else {
                            //  int position = Integer.parseInt((String) v.getTag());

                            // final String Str_position = String.valueOf(position);

                            list.add(Str_Value);
                        }
                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        //  list.add(Str_Value);
                    }
                }
            });
//            Check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//
//                    }else{
//                        int_counter--;
//                    }
//                }
//            });
            Check_box.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Check_box.setChecked(true);
//                    if (Check_box.isChecked()) {
//                        int_counter++;
//                    }
//                    if (int_counter == 4) {
//                        int_counter = 3;
//                    }
                    int position = Integer.parseInt((String) v.getTag());
                    final String Str_Value = item.text;

                    // Toast.makeText(v.getContext(), Str_Value, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(v.getContext(), Search_Result_Screen.class);

                    i.putExtra("DATE_ID", "#" + Str_Value);
                    i.putExtra("Check_screen", "#" + Str_Value);
                    v.getContext().startActivity(i);
                    Check_box.setChecked(false);
                    SharedPreferences shared = v.getContext().getSharedPreferences("Sponj", v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.clear();
                    editor.commit();
//                    final String Str_position = String.valueOf(position);
                    try {
                        String Str_Check_position = list.get(position);
                        if (Str_Check_position.contentEquals(Str_Value)) {

                            //int position = Integer.parseInt((String) v.getTag());

                            //int Int_position = Integer.parseInt(Str_position);
                            int pos = list.indexOf(Str_Value);
                            list.remove(pos);

                        } else {
                            //  int position = Integer.parseInt((String) v.getTag());

                            // final String Str_position = String.valueOf(position);

                            list.add(Str_Value);
                        }
                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        //  list.add(Str_Value);
                    }
                    return false;
                }
            });

        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, parent, false);
            }

            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            String input = section.text;
            String output = input.substring(0, 1).toUpperCase() + input.substring(1);
            textView.setText(output);
        }

        return view;
    }

}
