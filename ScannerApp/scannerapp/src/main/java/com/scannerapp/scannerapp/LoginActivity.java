package com.scannerapp.scannerapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView matr_nr_View;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        matr_nr_View = (AutoCompleteTextView) findViewById(R.id.matriculation_number);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }


    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        matr_nr_View.setError(null);

        // Store values at the time of the login attempt.
        String matr_nr = matr_nr_View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid matriculation number.
        if (TextUtils.isEmpty(matr_nr)) {
            matr_nr_View.setError(getString(R.string.error_field_required));
            focusView = matr_nr_View;
            cancel = true;
        } else if (!isMatrNrValid(matr_nr)) {
            matr_nr_View.setError(getString(R.string.error_invalid_matrnr));
            focusView = matr_nr_View;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(matr_nr);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isMatrNrValid(String matr_nr) {
        return matr_nr.length() == 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void finish(String qr_code_string, boolean bonus) {
        Intent intent = new Intent(this, QRcode.class);
        intent.putExtra("QR_STRING", qr_code_string);
        intent.putExtra("BONUS", bonus);
        startActivity(intent);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String matr_nr;
        private String xml_data;
        private String qr_code_string;
        private boolean bonus;

        UserLoginTask(String matrnr) {
            matr_nr = matrnr;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String ase_url = "https://aseatdappp1942369597trial.hanatrial.ondemand.com/ASEATD/OData.svc/Exercises?$filter=Student%20eq%20" + matr_nr +"&$orderby=Id%20desc&$top=1";

            xml_data = downloadXML(ase_url);
            if (xml_data == null) {
                return false;
            } else {
                return parse(xml_data);
            }

        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();
                Log.d(TAG, "xml result: " + xmlResult.toString());

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            return null;
        }

        public boolean parse(String xmlData) {
            boolean status = true;
            boolean inQRCode = false;
            boolean inBonus = false;
            String textValue = "";

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(xmlData));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("Qrcode".equalsIgnoreCase(tagName)) {
                                inQRCode = true;
                            } else if ("Bonus".equalsIgnoreCase(tagName)) {
                                inBonus = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            textValue = xpp.getText();
                            Log.d(TAG, "textvalue " + textValue);
                            break;

                        case XmlPullParser.END_TAG:
                            if (inQRCode) {
                                qr_code_string = textValue;
                                inQRCode = false;
                                } else if (inBonus) {
                                bonus = Boolean.parseBoolean(textValue);
                            }
                            break;

                        default:
                            // nothing else to do
                    }
                    eventType = xpp.next();
                }

            } catch (Exception e) {
                status = false;
                e.printStackTrace();
            }

            if (qr_code_string == null) {
                status = false;
            }
            Log.d(TAG, "status " + status);
            Log.d(TAG, "qrstring " + qr_code_string);
            Log.d(TAG, "bonus " + bonus);
            return status;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish(qr_code_string, bonus);
            } else {
                matr_nr_View.setError(getString(R.string.error_invalid_matrnr));
                matr_nr_View.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }
}

