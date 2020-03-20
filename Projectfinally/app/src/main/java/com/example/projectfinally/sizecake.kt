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
class sizecake : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sizecake, container, false)


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
            alertDialog.setMessage("Are you sure you want to bank ?")

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "Yes"
            ) { dialog, which ->
                dialog.dismiss()
                LoginManager.getInstance().logOut()
                activity!!.supportFragmentManager.popBackStack()
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

        val cost_m : TextView = view.findViewById(R.id.cost_m);
        val cost_l : TextView = view.findViewById(R.id.cost_l);
        val cost_xl : TextView = view.findViewById(R.id.cost_xl);

        val img_m : ImageView = view.findViewById(R.id.img_m);
        val img_l : ImageView = view.findViewById(R.id.img_l);
        val img_xl : ImageView = view.findViewById(R.id.img_xl);

        val choose_m : Button = view.findViewById(R.id.choose_m);
        val choose_l : Button = view.findViewById(R.id.choose_l);
        val choose_xl : Button = view.findViewById(R.id.choose_xl);

        cost_m.setText("Size M :  " + msg_sizeM + "  Baht")
        cost_l.setText("Size L :  " + msg_sizeL + "  Baht")
        cost_xl.setText("Size XL :  " + msg_sizeXL + "  Baht")

        Glide.with(activity!!.baseContext)
            .load(msg_image1)
            .into(img_m)

        Glide.with(activity!!.baseContext)
            .load(msg_image1)
            .into(img_l)

        Glide.with(activity!!.baseContext)
            .load(msg_image1)
            .into(img_xl)


        choose_m.setOnClickListener {
            val receipt = receipt().sent_data(msg_name_fb, msg_image_fb, msg_name, msg_image1, "M", msg_sizeM)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, receipt,"receipt")
            transaction.addToBackStack("receipt")
            transaction.commit()
        }

        choose_l.setOnClickListener {
            val receipt = receipt().sent_data(msg_name_fb, msg_image_fb, msg_name, msg_image1, "L", msg_sizeL)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, receipt,"receipt")
            transaction.addToBackStack("receipt")
            transaction.commit()
        }

        choose_xl.setOnClickListener {
            val receipt = receipt().sent_data(msg_name_fb, msg_image_fb, msg_name, msg_image1, "XL", msg_sizeXL)
            val fm = fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, receipt,"receipt")
            transaction.addToBackStack("receipt")
            transaction.commit()
        }


        return view
    }

    private var msg_name_fb : String = ""
    private var msg_image_fb : String = ""
    private var msg_name : String = ""
    private var msg_image1 : String = ""
    private var msg_image_cup : String = ""
    private var msg_sizeM: String = ""
    private var msg_sizeL: String = ""
    private var msg_sizeXL: String = ""

    fun sent_data(name_fb: String, image_fb: String, name: String, image1: String, image_cup: String, sizeM: String, sizeL: String, sizeXL: String): sizecake {

        val fragment = sizecake()
        val bundle = Bundle()

        bundle.putString("msg_name_fb", name_fb)
        bundle.putString("msg_image_fb", image_fb)
        bundle.putString("msg_name", name)
        bundle.putString("msg_image1", image1)
        bundle.putString("msg_image_cup", image_cup)
        bundle.putString("msg_sizeM", sizeM)
        bundle.putString("msg_sizeL", sizeL)
        bundle.putString("msg_sizeXL", sizeXL)

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
            msg_image_cup = bundle.getString("msg_image_cup").toString()
            msg_sizeM = bundle.getString("msg_sizeM").toString()
            msg_sizeL = bundle.getString("msg_sizeL").toString()
            msg_sizeXL = bundle.getString("msg_sizeXL").toString()


        }
    }

}
