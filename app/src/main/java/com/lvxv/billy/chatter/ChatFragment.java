package com.lvxv.billy.chatter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    //private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param// param1 Parameter 1.
     * @param// param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
       // Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
*/



        //subscribe to chat room
        FirebaseMessaging.getInstance().subscribeToTopic("chat");


    }



    RecyclerView rv;
    //TextView me;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        //create list view
        rv =(RecyclerView) rootView.findViewById(R.id.Chat);

       ChatRoomData();

        final Button SendMessage = (Button) rootView.findViewById(R.id.sender);
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage.setEnabled(false);
                SharedPreferences data = getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
                final String email = data.getString("Email","Illegal");
                final String username = data.getString("UserName","Illegal");

                EditText mess = (EditText) rootView.findViewById(R.id.message);
               final  String message = mess.getText().toString();

                // Instantiate the RequestQueue.
                //http://10.0.2.2/
              //  String url ="http://192.168.43.51/datamanager/sendmessage.php";
               // String url = R.string.host +"/sendmessage.php";
                String url = "http://linkdb.000webhostapp.com/sendmessage.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Error, Please try again")){
                                    Toast toast = Toast.makeText(getContext(), response ,Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                                SendMessage.setEnabled(true);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                        VolleyLog.e("Error : ",error);
                        Toast toast = Toast.makeText(getContext(), "No Connection" ,Toast.LENGTH_SHORT);
                        toast.show();
                        SendMessage.setEnabled(true);


                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("username", username );
                        params.put("message", message);

                        return params;
                    }

                };

                // Add the request to the RequestQueue.
                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                       new IntentFilter("custom-event-name"));


        // Inflate the layout for this fragment
        return rootView;
    }

    public void onDestroyView(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);

        super.onDestroyView();
    }

     // Our handler for received Intents. This will be called whenever an Intent
     // with an action named "custom-event-name" is broadcasted.
     private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
     @Override
     public void onReceive(Context context, Intent intent) {
     // Get extra data included in the Intent
     //String message = intent.getStringExtra("message");
     Log.d("receiver", "Got message: ");
     //ShowToken.setText(message);


         //Toast toast = Toast.makeText(getContext(), "Updated" ,Toast.LENGTH_SHORT);
         //toast.show();
         ChatRoomData();
     }
     };






public void ChatRoomData(){
    //create array list
    final ArrayList<HashMap<String,String>> ChatRoom;
    ChatRoom = new ArrayList<>();


    //String url = "http://192.168.43.51/datamanager/displaychat.php";
        String url = "http://linkdb.000webhostapp.com/displaychat.php";

    JsonArrayRequest jsArrRequest = new JsonArrayRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response)  {
                    try {

                        //Toast toast = Toast.makeText(getContext(), Os.get("username").toString()  ,Toast.LENGTH_SHORT);
                        //toast.show();

                        for (int i = 0 ; i < response.length() ;i++ ){
                            JSONObject chatdata  = response.getJSONObject(i);
                            //temporary hashmap
                            HashMap<String,String> chatmessage = new HashMap<>();

                            chatmessage.put("Email",chatdata.getString("email").toString() );
                            chatmessage.put("Username",chatdata.getString("username").toString());
                            chatmessage.put("Message",chatdata.getString("message").toString() );
                            chatmessage.put("Receivetime",chatdata.getString("receivetime").toString() );

                            ChatRoom.add(chatmessage);

                        }

                        ChatRoomCreate(ChatRoom);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    //mTxtDisplay.setText("Response: Error");
                     Toast toast = Toast.makeText(getContext(), "Response: Error Chat" ,Toast.LENGTH_SHORT);
                        toast.show();
                }
            });

// Access the RequestQueue through your singleton class.
    MySingleton.getInstance(getContext()).addToRequestQueue(jsArrRequest);
}

    private void ChatRoomCreate(ArrayList<HashMap<String,String>> ChatRoom){

        SharedPreferences data = getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String UserEmail = data.getString("Email", "Illegal");

        ChatAdapter adapter= new ChatAdapter(getActivity(),ChatRoom, UserEmail);


        rv.setAdapter(adapter);
        //rv.setLayoutManager(new LinearLayoutManager(getContext()));



    }


/*
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
*/
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

   // public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    //    void onFragmentInteraction(Uri uri);
    //}

}
