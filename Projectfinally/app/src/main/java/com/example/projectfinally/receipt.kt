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

/**
 * A simple [Fragment] subclass.
 */
class receipt : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_receipt, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val tvName = view.findViewById(R.id.tv_name) as TextView
        Glide.with(activity!!.baseContext)
            .load(msg_image_fb)
            .into(ivProfilePicture)

        tvName.setText(msg_name_fb)

        val login_button2 = view.findViewById(R.id.login_button2) as Button
        login_button2.setOnClickListener{

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Confirm to Exit App")
            alertDialog.setMessage("Are you sure you want to log out ?")

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Yes"
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

            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, "No"
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

        val btnback = view.findViewById(R.id.btnback) as Button
        btnback.setOnClickListener{
            val profile = profile().newInstance(msg_image_fb,msg_name_fb)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, profile,"profile")
            transaction.addToBackStack("profile")
            transaction.commit()
        }

        val name : TextView = view.findViewById(R.id.name);
        val size : TextView = view.findViewById(R.id.size);
        val cost : TextView = view.findViewById(R.id.cost);

        val img_list : ImageView = view.findViewById(R.id.img_list);

        name.setText("Product : " + msg_name)
        size.setText("Size : " + msg_size)
        cost.setText("Cost :   " + msg_cost + "   Baht")

        Glide.with(activity!!.baseContext)
            .load(msg_image1)
            .into(img_list)

        return view
    }

    private var msg_name_fb : String = ""
    private var msg_image_fb : String = ""
    private var msg_name : String = ""
    private var msg_image1 : String = ""
    private var msg_size: String = ""
    private var msg_cost: String = ""

    fun sent_data(name_fb: String, image_fb: String, name: String, image1: String, size: String, cost: String): receipt {

        val fragment = receipt()
        val bundle = Bundle()

        bundle.putString("msg_name_fb", name_fb)
        bundle.putString("msg_image_fb", image_fb)
        bundle.putString("msg_name", name)
        bundle.putString("msg_image1", image1)
        bundle.putString("msg_size", size)
        bundle.putString("msg_cost", cost)
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

            msg_name_fb = bundle.getString("msg_name_fb").toString()
            msg_image_fb = bundle.getString("msg_image_fb").toString()
            msg_name = bundle.getString("msg_name").toString()
            msg_image1 = bundle.getString("msg_image1").toString()
            msg_size = bundle.getString("msg_size").toString()
            msg_cost = bundle.getString("msg_cost").toString()


        }
    }

}
