package com.example.personelkayit.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personelkayit.R
import com.example.personelkayit.adapter.SearchRecyclerAdapter
import com.example.personelkayit.databinding.FragmentSearchBinding
import com.example.personelkayit.model.Personnel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    val personnelList : ArrayList<Personnel> = ArrayList()
    var adapter : SearchRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = SearchRecyclerAdapter(requireContext(),personnelList)
        binding.searchRecyclerView.adapter = adapter

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Burada yapılacak bir işlem yok
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                getDataFromFirestore(searchText,context!!)
            }

            override fun afterTextChanged(s: Editable?) {
                // Burada yapılacak bir işlem yok
            }
        })

        return view

    }

    fun getDataFromFirestore(str: String, context: Context) {


        db.collection("Personnels")
            .orderBy("name")
            .startAt(str)
            .endAt(str + "\uf8ff")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {

                            personnelList.clear()
                            val documents = snapshot.documents
                            for (document in documents) {
                                val name = document.get("name") as String
                                val title = document.get("title") as String
                                val userEmail = document.get("userEmail") as String
                                val telephone = document.get("telephone") as String
                                val adminEmail = document.get("adminEmail") as String
                                val userId = document.get("userId") as String

                                if (userEmail != null) {
                                    val personnel = Personnel(name,title,userEmail,telephone, adminEmail,userId)
                                    personnelList.add(personnel)
                                }

                            }
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
    }


}