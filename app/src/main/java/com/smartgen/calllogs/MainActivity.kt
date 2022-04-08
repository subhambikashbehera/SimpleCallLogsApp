package com.smartgen.calllogs

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    val columns= listOf<String>(CallLog.Calls._ID,CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.CACHED_NAME,CallLog.Calls.DURATION).toTypedArray()
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView=findViewById(R.id.listView)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),102)
        }else{
            readContacts()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==102 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            readContacts()
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    private fun readContacts() {

        val cursor=contentResolver.query(CallLog.Calls.CONTENT_URI,columns,null,null,"${CallLog.Calls.LAST_MODIFIED} DESC")
        val from= listOf<String>(CallLog.Calls.CACHED_NAME,CallLog.Calls.NUMBER,CallLog.Calls.DURATION,CallLog.Calls.TYPE).toTypedArray()
        val to= intArrayOf(R.id.name,R.id.number,R.id.duration,R.id.type)
        val cursorAdapter=SimpleCursorAdapter(this,R.layout.call_log_list,cursor,from,to,0)
        listView.adapter=cursorAdapter

    }


}