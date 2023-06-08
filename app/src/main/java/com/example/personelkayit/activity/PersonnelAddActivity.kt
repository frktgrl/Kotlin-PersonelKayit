package com.example.personelkayit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personelkayit.databinding.ActivityPersonnelAddBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class PersonnelAddActivity : AppCompatActivity() {


    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityPersonnelAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonnelAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.saveButton.setOnClickListener {

            val userId = UUID.randomUUID().toString()
            val postMap = hashMapOf<String, Any>()

            postMap["adminEmail"] = "${auth.currentUser?.email.toString()}"
            postMap["name"] = binding.nameInput.text.toString()
            postMap["title"] = binding.titleInput.text.toString()
            postMap["userEmail"] = binding.emailInput.text.toString()
            postMap["telephone"] = binding.telephoneInput.text.toString()
            postMap["date"] = Timestamp.now()
            postMap["userId"] = userId

            db.collection("Personnels").add(postMap).addOnCompleteListener { task ->

                if (task.isComplete && task.isSuccessful) {

                    Toast.makeText(
                        this@PersonnelAddActivity,
                        "Personnel Add",
                        Toast.LENGTH_LONG).show()
                    finish()
                    val intent = Intent(applicationContext, ListActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

}