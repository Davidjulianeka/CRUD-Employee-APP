package com.example.demofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Adapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Show : AppCompatActivity() {
    lateinit var ref:DatabaseReference
    lateinit var list:MutableList<Users>
    lateinit var listView: ListView
    lateinit var btnInsert : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.ListView)
        btnInsert = findViewById(R.id.InsertBtn)

        btnInsert.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()) {
                    list.clear()
                    for (h in snapshot.children)
                    {
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(this@Show,R.layout.users,list)
                    listView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}