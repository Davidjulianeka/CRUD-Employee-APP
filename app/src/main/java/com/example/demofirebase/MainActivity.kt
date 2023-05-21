package com.example.demofirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var inputName:EditText
    private lateinit var inputEmail:EditText
    private lateinit var inputSalary:EditText
    private lateinit var btnInsert:Button
    private lateinit var btnView:Button

    lateinit var ref:DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsert = findViewById(R.id.btnSubmit)
        inputEmail = findViewById(R.id.inputEmail)
        inputName = findViewById(R.id.inputNama)
        inputSalary = findViewById(R.id.inputSalary)
        btnView = findViewById(R.id.btnView)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        btnInsert.setOnClickListener {
            saveData()
        }

        btnView.setOnClickListener {
            val intent = Intent(this,Show::class.java)
            startActivity(intent)
        }
    }

    fun setClearText()
    {
        inputName.setText("")
        inputEmail.setText("")
        inputSalary.setText("")
        inputName.requestFocus()
    }

    fun saveData()
    {
        val nama = inputName.text.toString()
        val email = inputEmail.text.toString()
        val salary = inputSalary.text.toString()
        val id = ref.push().key.toString()
        Log.e("MainActivity","Data${nama}")
        Log.e("MainActivity","Data${email}")
        Log.e("MainActivity","Data${salary}")

        val user = Users(id,nama,email,salary)

        try {
            if(nama.isEmpty() || email.isEmpty())
            {
                Toast.makeText(this,"Data tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }
            else
            {
                ref.child(id).setValue(user).addOnCompleteListener {
                    Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show()
                    setClearText()
                    val intent = Intent(this,Show::class.java)
                    startActivity(intent)
                }
            }
        }catch (e:Exception){
            Log.e("ErrorMain","Error :${e}" )
        }
    }
}