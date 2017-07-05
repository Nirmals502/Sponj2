package sponj.sponj;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import DATABASE_HANDLER.CSVWriter;
import DATABASE_HANDLER.DatabaseHandler;
import DATABASE_HANDLER.Utility;
import fragments.Calender_fragment;
import fragments.Future_date;
import fragments.People_Fragmant;
import fragments.Present_date;
import fragments.Previous_date;
import fragments.Topic_Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity implements Future_date.OnFragmentInteractionListener, Present_date.OnFragmentInteractionListener, Previous_date.OnFragmentInteractionListener
        , Calender_fragment.OnFragmentInteractionListener, People_Fragmant.OnFragmentInteractionListener, Topic_Fragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    private static final int NUM_PAGES = 3;
    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    ImageView indicator1, indicator2, indicator3;
    TextView Txt_day, Txt_Date, Txt_Mont_year, Text_Vw_Topic_people;
    RelativeLayout Rlv_today, Rlv_Calender, Rlv_people, Rlv_Topic;
    FrameLayout Frame_layout;
    TextView Txt_Month;
    Typeface type;
    RelativeLayout Rlv_Toolbar_main_holder, Rlv_toolbar_calender, Rlv_toolbar_people, Rlv_toolbar_Topic;
    ImageView Img_today_icon, Img_back;
    SharedPreferences shared;
    Bundle extras;
    String Check_screen = "";
    ImageView Img_backup;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        Rlv_Toolbar_main_holder = (RelativeLayout) findViewById(R.id.Rlv_holder);
        Rlv_toolbar_calender = (RelativeLayout) findViewById(R.id.Rlv_holderr);
        Rlv_toolbar_people = (RelativeLayout) findViewById(R.id.Rlv_holder_people);
        Img_today_icon = (ImageView) findViewById(R.id.imageView6);
        Img_back = (ImageView) findViewById(R.id.imageView11);
        //Img_back.setVisibility();
        Text_Vw_Topic_people = (TextView) findViewById(R.id.textView_people);
        setSupportActionBar(toolbar);
        Rlv_Toolbar_main_holder.setVisibility(View.VISIBLE);
        Rlv_toolbar_calender.setVisibility(View.GONE);
        Rlv_toolbar_people.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Button Btn_open = (Button) toolbar.findViewById(R.id.button3);
        Button Btn_open2 = (Button) toolbar.findViewById(R.id.button33);
        Button Btn_open3 = (Button) toolbar.findViewById(R.id.button3_poeple);
        //Button Btn_open3 = (Button) toolbar.findViewById(R.id.button3_poeple);
        indicator1 = (ImageView) toolbar.findViewById(R.id.imageView3);
        indicator2 = (ImageView) toolbar.findViewById(R.id.imageView2);
        indicator3 = (ImageView) toolbar.findViewById(R.id.imageView4);

        Txt_day = (TextView) toolbar.findViewById(R.id.textView3);
        Txt_Date = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Txt_Mont_year = (TextView) toolbar.findViewById(R.id.textView4);
        Txt_Month = (TextView) toolbar.findViewById(R.id.textView44);
        Frame_layout = (FrameLayout) findViewById(R.id.frame);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {

                Check_screen = "";
            } else {

                Check_screen = extras.getString("Open_people");
            }
        } else {

            Check_screen = (String) savedInstanceState.getSerializable("Open_people");
        }

        Btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });
        Btn_open2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });
        Btn_open3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        indicator1.setImageResource(R.drawable.dark_gray);
        indicator2.setImageResource(R.drawable.whitedot);
        indicator3.setImageResource(R.drawable.dark_gray);
        Calendar sCalendar = Calendar.getInstance();
        shared = getSharedPreferences("Sponj", MODE_PRIVATE);
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
            Img_back.setVisibility(View.VISIBLE);
        }
        Img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_back.setVisibility(View.GONE);
                shared = getSharedPreferences("Sponj", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.clear();
                editor.commit();
//                Intent i = new Intent(MainActivity.this, Search_Result_Screen.class);
//                startActivity(i);
                finish();
            }
        });
        //  sCalendar.add(Calendar.DATE, +1);
        int Date = sCalendar.get(Calendar.MONTH);
        int day = sCalendar.get(Calendar.DAY_OF_MONTH);
        int year = sCalendar.get(Calendar.YEAR);


        String Str_to_date = String.valueOf(Date + 1);
        final String Str_to_year = String.valueOf(year);
        String Str_to_day = String.valueOf(day);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        final String month_name = month_date.format(sCalendar.getTime());
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        type = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
//        Typeface type2 = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");

        Txt_Date.setTypeface(type);
        Txt_day.setTypeface(type);
        Txt_Mont_year.setTypeface(type);
        Txt_day.setText(dayLongName.toUpperCase());
        Txt_Date.setText(Str_to_day);
        Txt_Mont_year.setText(month_name.toUpperCase() + " " + Str_to_year);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        View headerView = rightNavigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView iv = (ImageView) headerView.findViewById(R.id.imageView);
        Rlv_Calender = (RelativeLayout) headerView.findViewById(R.id.relativeLayout22);
        Rlv_today = (RelativeLayout) headerView.findViewById(R.id.relativeLayout2);
        Rlv_people = (RelativeLayout) headerView.findViewById(R.id.relativeLayout222);
        Rlv_Topic = (RelativeLayout) headerView.findViewById(R.id.relativeLayout2222);
        Img_backup = (ImageView) headerView.findViewById(R.id.imageView8);
        TextView Txt_today = (TextView) headerView.findViewById(R.id.textView);
        TextView Txt_Calandr = (TextView) headerView.findViewById(R.id.textVieww);
        TextView Txt_People = (TextView) headerView.findViewById(R.id.textViewww);
        TextView Txt_Topic = (TextView) headerView.findViewById(R.id.textViewwww);
        Txt_today.setTypeface(type);
        Txt_Calandr.setTypeface(type);
        Txt_People.setTypeface(type);
        Txt_Topic.setTypeface(type);

        Img_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db = new DatabaseHandler(MainActivity.this);
//                SQLiteDatabase sqldb = db.getReadableDatabase(); //My Database class
//
//                Cursor c = null;
//
//
//                //main code begins here
//
//                try {
//
//                    c = sqldb.rawQuery("select * from _NOTES", null);
//
//                    int rowcount = 0;
//
//                    int colcount = 0;
//
//                    File sdCardDir = Environment.getExternalStorageDirectory();
//
//                    String filename = "MyBackUp.csv";
//
//                    // the name of the file to export with
//
//                    File saveFile = new File(sdCardDir, filename);
//
//                    FileWriter fw = new FileWriter(saveFile);
//
//
//                    BufferedWriter bw = new BufferedWriter(fw);
//
//                    rowcount = c.getCount();
//
//                    colcount = c.getColumnCount();
//
//                    if (rowcount > 0) {
//
//                        c.moveToFirst();
//
//
//                        for (int i = 0; i < colcount; i++) {
//
//                            if (i != colcount - 1) {
//
//
//                                bw.write(c.getColumnName(i) + ",");
//
//
//                            } else {
//
//
//                                bw.write(c.getColumnName(i));
//
//
//                            }
//
//                        }
//
//                        bw.newLine();
//
//
//                        for (int i = 0; i < rowcount; i++) {
//
//                            c.moveToPosition(i);
//
//
//                            for (int j = 0; j < colcount; j++) {
//
//                                if (j != colcount - 1)
//
//                                    bw.write(c.getString(j) + ",");
//
//                                else
//
//                                    bw.write(c.getString(j));
//
//                            }
//
//                            bw.newLine();
//
//                        }
//
//                        bw.flush();
//
//                        Toast.makeText(MainActivity.this, "Exported Successfully", Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (Exception ex) {
//
//                    if (sqldb.isOpen()) {
//
//                        sqldb.close();
//
//                        // infotext.setText(ex.getMessage().toString());
//
//                    }
//
//
//                } finally {
//
//
//                }
                boolean result = Utility.checkPermission(MainActivity.this);

                if (result) {

                    new ExportDatabaseFileTask().execute();
                    //galleryIntent();


                }


            }
        });

        Rlv_people.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Rlv_Toolbar_main_holder.setVisibility(View.GONE);
                Rlv_toolbar_calender.setVisibility(View.GONE);
                Rlv_toolbar_people.setVisibility(View.VISIBLE);
                mPager.setVisibility(View.GONE);
                Frame_layout.setVisibility(View.VISIBLE);
                Frame_layout.removeAllViews();
                Text_Vw_Topic_people.setTypeface(type);
                Text_Vw_Topic_people.setText("@ People");
                android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                People_Fragmant fragment = new People_Fragmant();

                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        Rlv_Topic.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Rlv_Toolbar_main_holder.setVisibility(View.GONE);
                Rlv_toolbar_calender.setVisibility(View.GONE);
                Rlv_toolbar_people.setVisibility(View.VISIBLE);
                mPager.setVisibility(View.GONE);
                Frame_layout.setVisibility(View.VISIBLE);
                Frame_layout.removeAllViews();
                Text_Vw_Topic_people.setTypeface(type);
                Text_Vw_Topic_people.setText("# Topic");
                android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Topic_Fragment fragment = new Topic_Fragment();

                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        Rlv_today.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Rlv_Toolbar_main_holder.setVisibility(View.VISIBLE);
                Rlv_toolbar_calender.setVisibility(View.GONE);
                Rlv_toolbar_people.setVisibility(View.GONE);
                Frame_layout.setVisibility(View.GONE);
                Frame_layout.removeAllViews();
                mPager.setVisibility(View.VISIBLE);
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        Rlv_Calender.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Rlv_Toolbar_main_holder.setVisibility(View.GONE);
                Rlv_toolbar_people.setVisibility(View.GONE);
                Rlv_toolbar_calender.setVisibility(View.VISIBLE);
                Txt_Month.setTypeface(type);
                Txt_Month.setText(month_name + " " + Str_to_year);
                mPager.setVisibility(View.GONE);
                Frame_layout.setVisibility(View.VISIBLE);
                Frame_layout.removeAllViews();
                android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Calender_fragment fragment = new Calender_fragment();

                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        Img_today_icon.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Rlv_Toolbar_main_holder.setVisibility(View.VISIBLE);
                Rlv_toolbar_calender.setVisibility(View.GONE);
                Rlv_toolbar_people.setVisibility(View.GONE);
                Frame_layout.setVisibility(View.GONE);
                Frame_layout.removeAllViews();
                mPager.setVisibility(View.VISIBLE);
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        iv.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();
                //Toast.makeText(MainActivity.this, id, Toast.LENGTH_LONG).show();


                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    indicator1.setImageResource(R.drawable.whitedot);
                    indicator2.setImageResource(R.drawable.dark_gray);
                    indicator3.setImageResource(R.drawable.dark_gray);
                    Calendar sCalendar = Calendar.getInstance();
                    SharedPreferences shared = getSharedPreferences("Sponj", MODE_PRIVATE);
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
                        Img_back.setVisibility(View.VISIBLE);
                    }

                    sCalendar.add(Calendar.DATE, -1);
                    int Date = sCalendar.get(Calendar.MONTH);
                    int day = sCalendar.get(Calendar.DAY_OF_MONTH);
                    int year = sCalendar.get(Calendar.YEAR);

                    String Str_to_date = String.valueOf(Date + 1);
                    String Str_to_year = String.valueOf(year);
                    String Str_to_day = String.valueOf(day);
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    String month_name = month_date.format(sCalendar.getTime());
                    String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                    Txt_day.setText(dayLongName.toUpperCase());
                    Txt_Date.setText(Str_to_day);
                    Txt_Mont_year.setText(month_name.toUpperCase() + " " + Str_to_year);
                } else if (position == 1) {

                    indicator1.setImageResource(R.drawable.dark_gray);
                    indicator2.setImageResource(R.drawable.whitedot);
                    indicator3.setImageResource(R.drawable.dark_gray);
                    Calendar sCalendar = Calendar.getInstance();

                    SharedPreferences shared = getSharedPreferences("Sponj", MODE_PRIVATE);
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
                        Img_back.setVisibility(View.VISIBLE);
                    }
                    //  sCalendar.add(Calendar.DATE, +1);
                    int Date = sCalendar.get(Calendar.MONTH);
                    int day = sCalendar.get(Calendar.DAY_OF_MONTH);
                    int year = sCalendar.get(Calendar.YEAR);

                    String Str_to_date = String.valueOf(Date + 1);
                    String Str_to_year = String.valueOf(year);
                    String Str_to_day = String.valueOf(day);
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    String month_name = month_date.format(sCalendar.getTime());
                    String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                    Txt_day.setText(dayLongName.toUpperCase());
                    Txt_Date.setText(Str_to_day);
                    Txt_Mont_year.setText(month_name.toUpperCase() + " " + Str_to_year);
                } else if (position == 2) {
                    indicator1.setImageResource(R.drawable.dark_gray);
                    indicator2.setImageResource(R.drawable.dark_gray);
                    indicator3.setImageResource(R.drawable.whitedot);
                    Calendar sCalendar = Calendar.getInstance();
                    SharedPreferences shared = getSharedPreferences("Sponj", MODE_PRIVATE);
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
                        Img_back.setVisibility(View.VISIBLE);
                    }
                    sCalendar.add(Calendar.DATE, +1);
                    int Date = sCalendar.get(Calendar.MONTH);
                    int day = sCalendar.get(Calendar.DAY_OF_MONTH);
                    int year = sCalendar.get(Calendar.YEAR);

                    String Str_to_date = String.valueOf(Date + 1);
                    String Str_to_year = String.valueOf(year);
                    String Str_to_day = String.valueOf(day);
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    String month_name = month_date.format(sCalendar.getTime());
                    String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                    Txt_day.setText(dayLongName.toUpperCase());
                    Txt_Date.setText(Str_to_day);
                    Txt_Mont_year.setText(month_name.toUpperCase() + " " + Str_to_year);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (Check_screen.contentEquals("people"))

        {
            Rlv_Toolbar_main_holder.setVisibility(View.GONE);
            Rlv_toolbar_calender.setVisibility(View.GONE);
            Rlv_toolbar_people.setVisibility(View.VISIBLE);
            mPager.setVisibility(View.GONE);
            Frame_layout.setVisibility(View.VISIBLE);
            Frame_layout.removeAllViews();
            Text_Vw_Topic_people.setTypeface(type);
            Text_Vw_Topic_people.setText("@ People");
            android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            People_Fragmant fragment = new People_Fragmant();

            fragmentTransaction.add(R.id.frame, fragment);
            fragmentTransaction.commit();
            drawer.closeDrawer(GravityCompat.END);
        } else if (Check_screen.contentEquals("Topic"))

        {
            Rlv_Toolbar_main_holder.setVisibility(View.GONE);
            Rlv_toolbar_calender.setVisibility(View.GONE);
            Rlv_toolbar_people.setVisibility(View.VISIBLE);
            mPager.setVisibility(View.GONE);
            Frame_layout.setVisibility(View.VISIBLE);
            Frame_layout.removeAllViews();
            Text_Vw_Topic_people.setTypeface(type);
            Text_Vw_Topic_people.setText("# Topic");
            android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Topic_Fragment fragment = new Topic_Fragment();

            fragmentTransaction.add(R.id.frame, fragment);
            fragmentTransaction.commit();
            drawer.closeDrawer(GravityCompat.END);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return Previous_date.newInstance("FirstFragment, Instance 1");
                case 1:
                    return Present_date.newInstance("SecondFragment, Instance 1");
                case 2:
                    return Future_date.newInstance("ThirdFragment, Instance 1");

                default:
                    return Present_date.newInstance("SecondFragment, Default");

            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    class ExportDatabaseFileTask extends AsyncTask<String, Void, Boolean>

    {
        //private Context mContext;

//        public ExportDatabaseFileTask(Context context) {
//            mContext = context;
//        }

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);


        // can use UI thread here

        @Override

        protected void onPreExecute()

        {

            this.dialog.setMessage("Exporting database...");

            this.dialog.show();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            this.dialog.dismiss();
            String FILE = Environment.getExternalStorageDirectory()+"";
            Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
            // sendIntent.setType("text/html");
            sendIntent.setType("application/csv");
            sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "");
            String temp_path = FILE + "/" + "Sponj.csv";
            File F = new File(temp_path);
            Uri U = Uri.fromFile(F);
            sendIntent.putExtra(Intent.EXTRA_STREAM, U);
            startActivity(Intent.createChooser(sendIntent, "Send Mail"));
        }


        // automatically done on worker thread (separate from UI thread)

        protected Boolean doInBackground(final String... args)

        {
            db = new DatabaseHandler(MainActivity.this);
            File dbFile = getDatabasePath(db.getDatabaseName());

//         DBHelper dbhelper = new DBHelper(getApplicationContext());
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "Sponj.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase dbb = db.getReadableDatabase();
                Cursor curCSV = dbb.rawQuery("SELECT * FROM _NOTES", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2)};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                curCSV.close();
            } catch (Exception sqlEx) {
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            }
            return null;
        }
    }
}







