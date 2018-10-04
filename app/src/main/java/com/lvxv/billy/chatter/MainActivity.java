package com.lvxv.billy.chatter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lvxv.billy.chatter.R.id.Chat;
import static com.lvxv.billy.chatter.R.id.chatFrag;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {//implements ChatFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
  //  private MesReceiver mesRec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_profile);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_chat);

/*
        Context contexto = getApplicationContext();
        CharSequence text = "Hello begin!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, text, duration);
        toast.show();
*/
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
      //  LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("custom-event-name"));

       // mesRec = new MesReceiver();
       // registerReceiver(mesRec,new IntentFilter("custom-event-name"));

    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

     //   unregisterReceiver(mesRec);
        super.onDestroy();
    }
/*

    private class MesReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: ");
            //ShowToken.setText(message);
            ChatFragment frag = (ChatFragment) getSupportFragmentManager().findFragmentByTag("chatFragment");

            if(frag == null) {
                showFragment();
            }
            //call fragment
            frag.Chatta();
            //frag.ChatRoomData();

            Context contexto = getApplicationContext();
             CharSequence text = message;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexto, text, duration);
            toast.show();

        }

    }
*/
    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
 /*   private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: ");
            //ShowToken.setText(message);
            ChatFragment frag = (ChatFragment) getSupportFragmentManager().findFragmentByTag("chatFragment");

            if(frag == null) {
                showFragment();
            }
            //call fragment
            frag.Chatta();


            Context contexto = getApplicationContext();
            CharSequence text = message;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexto, text, duration);
            toast.show();

        }
    };
*/
    public void showFragment(){
        ChatFragment frag = ChatFragment.newInstance();

        FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
        transact.add(R.id.chatFrag,frag,"chatFragment");

        transact.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onFragmentInteraction(Uri uri) {
        //to interact with fragment
    }
*/


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
          //  Bundle args = new Bundle();
           // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            //fragment.setArguments(args);
            return fragment;
        }

        ListView lv;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            SharedPreferences data = getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
            String Name = data.getString("UserName", "this a bug");
            String  Email = data.getString("Email", "me@email.com");

            TextView Nametext = (TextView) rootView.findViewById(R.id.Name);
            Nametext.setText(Name);
            TextView EmailText = (TextView) rootView.findViewById(R.id.Email);
            EmailText.setText(Email);


            //create list view
            lv =(ListView) rootView.findViewById(R.id.friends);

            FriendlistCreate();
            return rootView;
        }

        private void FriendlistCreate(){
            //create array list
            final ArrayList<HashMap<String,String>> Friendlist;
            Friendlist = new ArrayList<>();

            SharedPreferences data = getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
            final String UserEmail = data.getString("Email", "Illegal");

           // String url = "http://192.168.43.51/datamanager/friendlist.php";
           // String url = R.string.host +"/friendlist.php";
            String url = "http://linkdb.000webhostapp.com/friendlist.php";

            //String[] osArray = {"Android","IPhone","WindowsMobile","Blackberry", "WebOS","Ubuntu","Windows7","Max OS X"};

            //String[] explanation = {"made by google","made by apple","made by microsoft","who ?", "hmmm","linux they say","microsoft again","Max OS X"};

            JsonArrayRequest jsArrRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response)  {
                            try {

                                //Toast toast = Toast.makeText(getContext(), Os.get("username").toString()  ,Toast.LENGTH_SHORT);
                                //toast.show();

                                for (int i = 0 ; i < response.length() ;i++ ){
                                    JSONObject chatdata  = response.getJSONObject(i);

                                    if(!chatdata.getString("email").equals(UserEmail)){
                                        //temporary hashmap
                                        HashMap<String,String> friend = new HashMap<>();

                                        friend.put("Username",chatdata.getString("username").toString() );
                                        friend.put("Email",chatdata.getString("email").toString() );

                                        Friendlist.add(friend);
                                    }

                                }

                                ListAdapter adapter = new SimpleAdapter(
                                        getActivity() , Friendlist , R.layout.friend_listbox ,new  String[]{"Username","Email"},
                                        new int[]{R.id.Username, R.id.Email });
                                lv.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // mTxtDisplay.setText("Response: " + Os.getString(1).toString() );


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            //mTxtDisplay.setText("Response: Error");
                            Toast toast = Toast.makeText(getContext(), "Response: Error Friend" ,Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

// Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getContext()).addToRequestQueue(jsArrRequest);

/*

            for (int i = 0 ; i < osArray.length ;i++ ){
                //temporary hashmap
                HashMap<String,String> friend = new HashMap<>();

                friend.put("Username",osArray[i]);
                friend.put("Email",explanation[i]);

                Friendlist.add(friend);

            }
            */
        }
        

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 1){
                return ChatFragment.newInstance();
            }else{
                return PlaceholderFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
/*
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";

            }
            return null;
        }*/
    }
}
