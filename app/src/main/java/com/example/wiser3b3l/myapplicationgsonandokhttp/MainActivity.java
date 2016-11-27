package com.example.wiser3b3l.myapplicationgsonandokhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    okhttp3.OkHttpClient client;

    MediaType JSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        JSON = MediaType.parse("application/json; charset=utf-8");

//        new JSONFeedArrayTask().execute();
        new PostTask().execute();

    }

    public class PostTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        @Override
        protected String doInBackground(String... urls) {
            try {

//                String fName, String lName, int userId, String userAbout, String userDOB, String userEmail, String userGender, String userLocation

                User newUser = new User("ppppppppppp", "kkkkkkkkkkkk", 20, "mmmmmmmmmmmmm", "1994-10-10", "uuuuuuuu@csumb.edu", "M", "2016 - 10 - 27");

                Gson gson = new Gson();
                String json = gson.toJson(newUser);


                client = new OkHttpClient();
                okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, json);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://lowcost-env.8jm8a7kdcg.us-west-2.elasticbeanstalk.com/webapi/users/addUser")
                        .post(body)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                return response.toString();



            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.myText);
            textView.setText(s);
        }



    }


    public class JSONFeedArrayTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                okhttp3.OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new Request.Builder()
                        .url("http://lowcost-env.8jm8a7kdcg.us-west-2.elasticbeanstalk.com/webapi/users")
                        .build();

                okhttp3.Response response = client.newCall(request).execute();
                String result = response.body().string();

                Gson gson = new Gson();

                Type collectionType = new TypeToken<Collection<User>>() {}.getType();
                Collection<User> userListJson = gson.fromJson(result,collectionType);
                User[] userListOBJ = userListJson.toArray(new User[userListJson.size()]);
                return userListOBJ[0].getfName()+userListOBJ[0].getlName();


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return null;

        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.myText);
            textView.setText(s);
        }
    }
}
