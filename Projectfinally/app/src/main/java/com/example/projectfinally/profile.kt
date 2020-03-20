package com.example.projectfinally

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class profile : Fragment() {

    var PhotoURL : String = ""
    var Name : String = ""

    fun newInstance(url: String,name : String): profile {

        val profile = profile()
        val bundle = Bundle()
        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        profile.setArguments(bundle)

        return profile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        val login_button2 = view.findViewById(R.id.login_button2) as Button
        val btn_1 = view.findViewById(R.id.btn1) as Button
        val btn_2 = view.findViewById(R.id.btn2) as Button


        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)

        tvName.setText(Name)

        login_button2.setOnClickListener{

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Confirm to Exit App")
            alertDialog.setMessage("Are you sure you want to log out ?")

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"
            ) { dialog, which ->
                dialog.dismiss()
                LoginManager.getInstance().logOut()
                val authen = authen()
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.layout, authen,"fragment_authen")
                transaction.addToBackStack("fragment_authen")
                transaction.commit()
            }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"
            ) { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()

            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            btnPositive.layoutParams = layoutParams
            btnNegative.layoutParams = layoutParams
        }



        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().getReference()
        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mMessagesRef = mRootRef.child("messages")

        btn_2.setOnClickListener {
            val menucake = menucake().sent_data(Name, PhotoURL)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, menucake,"menucake")
            transaction.addToBackStack("menucake")
            transaction.commit()
        }

        btn_1.setOnClickListener {
            val menublizzard = menublizzard().sent_data(Name, PhotoURL)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, menublizzard,"menublizzard")
            transaction.addToBackStack("menublizzard")
            transaction.commit()
        }

        // Inflate the layout for this fragment
        return view
    }

    data class FriendlyMessage(
        var username: String? = "",
        var text: String? = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()
            Name = bundle.getString("Name").toString()
        }
    }



}
