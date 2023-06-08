package com.example.personelkayit.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.personelkayit.databinding.ActivityEditPersonnelBinding
import com.example.personelkayit.databinding.ActivityListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditPersonnelActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityEditPersonnelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPersonnelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore


        //Bundle Gelen Veriler
        val name = intent.getStringExtra("name")
        val title = intent.getStringExtra("title")
        val userEmail = intent.getStringExtra("userEmail")
        val telephone = intent.getStringExtra("telephone")
        val userId = intent.getStringExtra("userId")

        binding.nameInput.setHint(name)
        binding.titleInput.setHint(title)
        binding.emailInput.setHint(userEmail)
        binding.telephoneInput.setHint(telephone)


    }
    fun backImageClicked (view : View) {

        val intent = Intent(applicationContext, ListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun saveButtonClicked(view: View) {

        val userId = intent.getStringExtra("userId")

        val updateName = binding.nameInput.text.toString()
        val updateTitle = binding.titleInput.text.toString()
        val updateUserEmail = binding.emailInput.text.toString()
        val updateTelephone = binding.telephoneInput.text.toString()

        val collectionRef = db.collection("Personnels")
        val query = collectionRef.whereEqualTo("userId", userId).limit(1)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val documentId = documentSnapshot.id

                // Belirli dökümana erişmek ve alanları güncellemek için
                val userRef = db.collection("Personnels").document(documentId)

                val updateData = HashMap<String, Any>()

                if (!updateName.isEmpty()) {
                    updateData["name"] = updateName
                }

                if (!updateTitle.isEmpty()) {
                    updateData["title"] = updateTitle
                }

                if (!updateUserEmail.isEmpty()) {
                    updateData["userEmail"] = updateUserEmail
                }
                if (!updateTelephone.isEmpty()) {
                    updateData["telephone"] = updateTelephone
                }

                if (updateData.isNotEmpty()) {
                    userRef.update(updateData).addOnSuccessListener {
                        Log.d(ContentValues.TAG, "Kullanıcı bilgileri güncellendi.")
                        // Başarılı güncelleme durumunda yapılacak işlemler
                        Toast.makeText(this@EditPersonnelActivity, "Update Successful", Toast.LENGTH_LONG).show()

                        // EditText içeriğini temizle
                        binding.nameInput.text.clear()
                        binding.titleInput.text.clear()
                        binding.emailInput.text.clear()
                        binding.telephoneInput.text.clear()


                    }.addOnFailureListener { e ->
                        Log.e(ContentValues.TAG, "Kullanıcı bilgileri güncellenirken hata oluştu.", e)
                        // Hata durumunda yapılacak işlemler
                    }
                } else {
                    Log.d(ContentValues.TAG, "Güncellenecek alan bulunamadı.")
                    // Güncellenecek alan yoksa yapılacak işlemler
                }
            } else {
                Log.d(ContentValues.TAG, "Kullanıcı bulunamadı.")
                // Kullanıcı bulunamadığı durumda yapılacak işlemler
            }
        }.addOnFailureListener { e ->
            Log.e(ContentValues.TAG, "Kullanıcı sorgulanırken hata oluştu.", e)
            // Hata durumunda yapılacak işlemler
        }
    }


    fun deleteButtonClicked (view : View) {

        db = FirebaseFirestore.getInstance()

        val userId = intent.getStringExtra("userId")

        val postsRef = db.collection("Personnels")

            postsRef.whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val postRef = postsRef.document(document.id)

                        // Gönderiyi silme işlemi
                        postRef.delete()
                            .addOnSuccessListener {
                                // Silme işlemi başarılı
                                println("Personnnel Deleted")
                                Toast.makeText(
                                    this@EditPersonnelActivity,
                                    "Personnel Deleted",
                                    Toast.LENGTH_LONG).show()
                                finish()
                                val intent = Intent(applicationContext, ListActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                // Hata durumunda yapılacak işlemler
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Hata durumunda yapılacak işlemler
                }


        }
}