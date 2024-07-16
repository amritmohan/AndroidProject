package com.example.myproject1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var usernames:EditText;
    lateinit var  password:EditText
    lateinit var btn:Button


    // maine ye declare kiya hai taki mai firebase Auth cclass ke methods ko access kar sakau

    lateinit var firebaseobj: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase;
    lateinit var databaseReference: DatabaseReference;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        usernames=findViewById(R.id.username)
        password=findViewById(R.id.pass)
        btn=findViewById(R.id.button)

        firebaseDatabase=FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference().child("Users")
        val hashMap= HashMap<String,String>();



        btn.setOnClickListener {

            firebaseobj=FirebaseAuth.getInstance()

            val email:String=usernames.text.toString().trim()
            val psw:String=password.text.toString().trim()
            hashMap.put("Email",email)
            hashMap.put("password",psw)


            firebaseobj.createUserWithEmailAndPassword(email,psw).addOnSuccessListener {
                Toast.makeText(this@MainActivity,"User Registered ",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,"Error Occurred ",Toast.LENGTH_LONG).show()

            }
            databaseReference.push().setValue(hashMap).addOnSuccessListener {
                Toast.makeText(this@MainActivity,"Data entered", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,"Error occurred",Toast.LENGTH_LONG).show()
            }

            databaseReference.addValueEventListener(object :  ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = dataSnapshot.getValue<String>()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })



        }



    }
}