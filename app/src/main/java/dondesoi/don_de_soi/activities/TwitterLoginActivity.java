package dondesoi.don_de_soi.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import dondesoi.don_de_soi.R;

/**
 * A login screen that offers login via email/password.
 */
public class TwitterLoginActivity extends BaseActivity {
    String textToShare = "j'ai donné mon sang grâce à l'application don de soi #donDeSoi";

    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_twitter_login);


        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();


                String token =authToken.token;
                String secret = authToken.secret;



                Uri uri=Uri.parse("https://twitter.com/");

                TweetComposer.Builder builder = new TweetComposer.Builder(TwitterLoginActivity.this)
                        .text(textToShare);

                builder.show();
                login(session);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(TwitterLoginActivity.this, "Connexion impossible", Toast.LENGTH_LONG).show();
            }
        });


    }

   public void login(TwitterSession session){
        String username = session.getUserName();
        Intent intent = new Intent(TwitterLoginActivity.this, MainActivity.class);
        intent.putExtra("username", username);

       //startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode,resultCode,data);
    }
}