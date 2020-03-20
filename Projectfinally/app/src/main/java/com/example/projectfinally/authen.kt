package com.example.projectfinally


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class authen : Fragment() {

    var user : FirebaseUser? = null
    lateinit var facebookSignInButton : LoginButton
    var callbackManager : CallbackManager? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_authen, container, false)

        //     ------------------------------   start fragment --------------------------
        val button : Button = view.findViewById(R.id.BTLogin);

        val editusername : EditText = view.findViewById(R.id.ETUsername)
        val editupassword: EditText = view.findViewById(R.id.ETPassword)

        //     ------------------------------   end fragment --------------------------

        //-------------------------------start on click button-------------------------------------
        button.setOnClickListener {

            // เรียกไฟล์ recipes.json object ที่ชื่อว่า login
            // ---------------------------------------- ขั้นตอนการเอาค่าของ json มาใช้ --------------------------------------
            val jsonString : String = loadJsonFromAsset("user.json", activity!!.baseContext).toString()
            val json = JSONObject(jsonString)
            val jsonArray = json.getJSONArray("user")
            // ---------------------------------------- ขั้นตอนการเอาค่าของ json มาใช้ --------------------------------------


            // ---------------------------------------- ตัวแปรเช็ค username --------------------------------------
            var user_check = true
            // ---------------------------------------- ตัวแปรเช็ค password --------------------------------------
            var password_check = true

            var username = ""
            var password = ""
            var img =""

            // ---------------------------------------- ลูปการเช็คค่าในตอน login --------------------------------------
            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                username = jsonObject.getString("username")
                password = jsonObject.getString("password")
                img = jsonObject.getString("img")

                user_check = true
                password_check = true


                if(editusername.getText().toString() == username && editupassword.getText().toString() != password){
                    password_check = false
                }
                else if(editusername.getText().toString() != username && editupassword.getText().toString() == password){
                    user_check = false
                }

                else if(editusername.getText().toString() != username && editupassword.getText().toString() != password){
                    user_check = false
                    password_check = false
                }else{
                    break;
                }
            }
            // ---------------------------------------- ลูปการเช็คค่าในตอน login --------------------------------------


            // ---------------------------------------- if else check login --------------------------------------
            if(!user_check && !password_check){
                Toast.makeText(getActivity(), "Username and Password is Wrong!", Toast.LENGTH_LONG).show();
            }
            else if(!user_check && password_check){
                Toast.makeText(getActivity(), "Username is Wrong!", Toast.LENGTH_LONG).show();
            }

            else if(user_check && !password_check){
                Toast.makeText(getActivity(), "Password is Wrong!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), "Welcome " + editusername.getText().toString(), Toast.LENGTH_LONG).show();
                val profile = profile().newInstance(img, username)
                val fm = fragmentManager
                val transaction: FragmentTransaction = fm!!.beginTransaction()

                transaction.replace(R.id.layout, profile, "profile")
                transaction.addToBackStack("profile")
                transaction.commit()
            }
            // ---------------------------------------- if else check login --------------------------------------

        }
        //-------------------------------end on click button-------------------------------------


        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton  = view.findViewById(R.id.login_button) as LoginButton
        firebaseAuth = FirebaseAuth.getInstance()
        facebookSignInButton.setReadPermissions("email")

        // If using in a fragment
        facebookSignInButton.setFragment(this)

        val token: AccessToken?
        token = AccessToken.getCurrentAccessToken()

        if (token != null) { //Means user is not logged in
            LoginManager.getInstance().logOut()
        }

        // Callback registration
        facebookSignInButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code

                handleFacebookAccessToken(loginResult!!.accessToken)

            }
            override fun onCancel() { // App code
            }
            override fun onError(exception: FacebookException) { // App code
            }
        })

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code

                    handleFacebookAccessToken(loginResult!!.accessToken)

                }

                override fun onCancel() { // App code
                }

                override fun onError(exception: FacebookException) { // App code
                }
            })

        // Inflate the layout for this fragment
        return view
    }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(activity!!.baseContext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token : AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                user = firebaseAuth!!.currentUser
                val profile = profile().newInstance(user!!.photoUrl.toString(),user!!.displayName.toString())
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, profile,"fragment_profile")
                transaction.addToBackStack("fragment_profile")
                transaction.commit()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.getException())
                Toast.makeText(activity!!.baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
