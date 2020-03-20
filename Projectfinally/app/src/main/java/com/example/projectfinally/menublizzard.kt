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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class menublizzard : Fragment() {

    override fun onCreateView(inflater : LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menublizzard, container, false)
        // Inflate the layout for this fragment

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        Glide.with(activity!!.baseContext)
            .load(msg_image_fb)
            .into(ivProfilePicture)

        tvName.setText(msg_name_fb)

//        val login_button2 = view.findViewById(R.id.login_button2) as Button
//        login_button2.setOnClickListener{
//
//            val alertDialog = AlertDialog.Builder(context).create()
//            alertDialog.setTitle("Confirm")
//            alertDialog.setMessage("Are you sure you want to back ?")
//
//            alertDialog.setButton(
//                AlertDialog.BUTTON_POSITIVE, "Yes"
//            ) { dialog, which ->
//                dialog.dismiss()
//                LoginManager.getInstance().logOut()
//                activity!!.supportFragmentManager.popBackStack()
//            }
//
//            alertDialog.setButton(
//                AlertDialog.BUTTON_NEGATIVE, "No"
//            ) { dialog, which ->
//                dialog.dismiss()
//            }
//            alertDialog.show()
//
//            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//
//            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
//            layoutParams.weight = 10f
//            btnPositive.layoutParams = layoutParams
//            btnNegative.layoutParams = layoutParams
//        }

        /// start back to profile
        val login_button2 = view.findViewById(R.id.login_button2) as Button
        login_button2.setOnClickListener{
            val profile = profile().newInstance(msg_image_fb,msg_name_fb)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, profile,"profile")
            transaction.addToBackStack("profile")
            transaction.commit()
        }
        /// end back to profile

        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("data_bizzard")

        mMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val list = JSONArray()
                val listView = view.findViewById<RecyclerView>(R.id.recyview_layout)
                for (ds in dataSnapshot.children) {
                    val jObject = JSONObject()

                    val name = ds.child("name").getValue(String::class.java)!!
                    val image1 = ds.child("image1").getValue(String::class.java)!!
                    val image_cup = ds.child("image_cup").getValue(String::class.java)!!
                    val sizeM = ds.child("sizeM").getValue(String::class.java)!!
                    val sizeL = ds.child("sizeL").getValue(String::class.java)!!
                    val sizeXL = ds.child("sizeXL").getValue(String::class.java)!!

                    jObject.put("name_fb",msg_name_fb)
                    jObject.put("image_fb",msg_image_fb)
                    jObject.put("key",ds.key)
                    jObject.put("name",name)
                    jObject.put("image1",image1)
                    jObject.put("image_cup",image_cup)
                    jObject.put("sizeM",sizeM)
                    jObject.put("sizeL",sizeL)
                    jObject.put("sizeXL",sizeXL)

                    list.put(jObject)
                }
                val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)
                //ตั้งค่า Layout
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
                recyclerView.layoutManager = layoutManager
                //ตั้งค่า Adapter
                val adapter = BizzardRecyclerAdapter(activity!!.baseContext,list)
                recyclerView.adapter = adapter

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


        return view
    }

    private var msg_name_fb : String = ""
    private var msg_image_fb : String = ""

    fun sent_data(name_fb: String, image_fb: String): menublizzard {

        val fragment = menublizzard()
        val bundle = Bundle()

        bundle.putString("msg_name_fb", name_fb)
        bundle.putString("msg_image_fb", image_fb)

        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

            msg_name_fb = bundle.getString("msg_name_fb").toString()
            msg_image_fb = bundle.getString("msg_image_fb").toString()

        }
    }

}
