package com.example.projectfinally

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        debugHashKey()

//        val fragment_profile = profile()
//        val manager_f = supportFragmentManager
//        val transaction_f = manager_f.beginTransaction()
//        transaction_f.replace(R.id.layout, fragment_profile,"fragment_profile")
//        transaction_f.addToBackStack("fragment_profile")
//        transaction_f.commit()


        val authen = authen()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.layout, authen,"fragment_authen")
        transaction.addToBackStack("fragment_authen")
        transaction.commit()

    }

    private fun debugHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.projectfinally",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }


}
