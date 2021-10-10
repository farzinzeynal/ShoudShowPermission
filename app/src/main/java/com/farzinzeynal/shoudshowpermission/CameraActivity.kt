package com.farzinzeynal.shoudshowpermission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class CameraActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1242

    private var neverAskAgain = false
    private var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        sharedPreferences = this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        editor=sharedPreferences?.edit()

        neverAskAgain = sharedPreferences!!.getBoolean("never",false)

        Log.i("boolean1", neverAskAgain.toString())

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE);


    }


   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@CameraActivity, "Allowed", Toast.LENGTH_LONG).show()
            }
            else{
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this@CameraActivity, Manifest.permission.CAMERA))
                {
                    if(neverAskAgain)
                    {
                        Toast.makeText(this@CameraActivity, "Show Dialog", Toast.LENGTH_LONG).show()
                        showMyDialog()
                    }
                    else{
                        editor?.putBoolean("never",true)
                        editor?.apply()
                        editor?.commit()
                        finish()
                        Toast.makeText(this@CameraActivity, "Should not Show", Toast.LENGTH_LONG).show()
                    }
                }
                else
                {
                    Toast.makeText(this@CameraActivity, "Denied", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

        }

    }

    private fun showMyDialog() {

    }

}