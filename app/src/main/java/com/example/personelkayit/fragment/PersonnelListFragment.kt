package com.example.personelkayit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personelkayit.adapter.ListRecyclerAdapter
import com.example.personelkayit.databinding.FragmentPersonnelListBinding
import com.example.personelkayit.model.Personnel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class PersonnelListFragment : Fragment() {

    private lateinit var binding: FragmentPersonnelListBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    val personnelList : ArrayList<Personnel> = ArrayList()
    var adapter : ListRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonnelListBinding.inflate(layoutInflater)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.listRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = ListRecyclerAdapter(requireContext(),personnelList)
        binding.listRecyclerView.adapter = adapter

        getDataFromFirestore()


        return view

    }
    fun getDataFromFirestore() {

        db.collection("Personnels").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_LONG).show()
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

                            println(name)
                            println(title)

                            val personnel = Personnel(name,title,userEmail,telephone,adminEmail,userId)
                            personnelList.add(personnel)
                        }
                        adapter!!.notifyDataSetChanged()

                    }
                }

            }
        }

    }



}