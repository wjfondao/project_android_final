package com.example.projectfinally

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class CakeRecyclerAdapter(context: Context, val dataSource: JSONArray) : RecyclerView.Adapter<CakeRecyclerAdapter.Holder>() {

    private val thiscontext : Context = context

    class Holder(view : View) : RecyclerView.ViewHolder(view) {

        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var image: ImageView

        fun Holder(){
            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.tv_name) as TextView
            image = View.findViewById<View>(R.id.imgV) as ImageView
        }

    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout, parent, false))
    }


    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()

        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("name").toString() )

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image1").toString())
            .into(holder.image)

        holder.layout.setOnClickListener{
            val sizecake = sizecake().sent_data(
                dataSource.getJSONObject(position).getString("name_fb").toString(),
                dataSource.getJSONObject(position).getString("image_fb").toString(),
                dataSource.getJSONObject(position).getString("name").toString(),
                dataSource.getJSONObject(position).getString("image1").toString(),
                dataSource.getJSONObject(position).getString("image_cup").toString(),
                dataSource.getJSONObject(position).getString("sizeM").toString(),
                dataSource.getJSONObject(position).getString("sizeL").toString(),
                dataSource.getJSONObject(position).getString("sizeXL").toString()
            )
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            val transaction : FragmentTransaction = manager!!.beginTransaction()
            transaction.replace(R.id.layout, sizecake,"sizecake")
            transaction.addToBackStack("sizecake")
            transaction.commit()
        }
    }


}
