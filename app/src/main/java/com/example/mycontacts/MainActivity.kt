package com.example.mycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import android.util.Log
import android.content.Intent
import android.net.Uri
import android.content.Context
import android.widget.ImageButton
import com.example.mycontacts.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton

    private val originalContactList = mutableListOf(
        Contact("Patel Tirth", "8866248170"),
        Contact("Patel Smit", "9726934451"),
        Contact("Chiru", "9662152694"),
        Contact("Patel Ved", "9662522535"),
        Contact("Patel Vansh", "8401138433"),
        Contact("Patel Tirth", "8866248170"),
        Contact("Patel Smit", "9726934451"),
        Contact("Chiru", "9662152694"),
        Contact("Patel Ved", "9662522535"),
        Contact("Patel Vansh", "8401138433"),
        Contact("Patel Tirth", "8866248170"),
        Contact("Patel Smit", "9726934451"),
        Contact("Chiru", "9662152694"),
        Contact("Patel Ved", "9662522535"),
        Contact("Patel Vansh", "8401138433"),
        Contact("Patel Tirth", "8866248170"),
        Contact("Patel Smit", "9726934451"),
        Contact("Chiru", "9662152694"),
        Contact("Patel Ved", "9662522535"),
        Contact("Patel Vansh", "8401138433")
    )

    private val contactList = mutableListOf<Contact>()

    private lateinit var contactAdapter: ContactAdapter // Define the adapter as a class-level property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        searchEditText = binding.searchEditText
        searchButton = binding.searchButton
        recyclerView = binding.contactList // Initialize recyclerView using the binding

        // Copy the original list to the current list
        contactList.addAll(originalContactList)

        val sortedContactList = contactList.sortedBy { it.name }
        contactAdapter = ContactAdapter(sortedContactList) // Initialize the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter // Set the adapter to the RecyclerView

        binding.addContactButton.setOnClickListener {
            showAddContactDialog()
        }
        searchButton.setOnClickListener {
            performSearch()
        }
    }
    private fun showAddContactDialog() {
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Contact")
            .setPositiveButton("Add") { dialog, _ ->
                val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
                val numberEditText = dialogView.findViewById<EditText>(R.id.numberEditText)
                val name = nameEditText.text.toString()
                val number = numberEditText.text.toString()

                if (name.isNotEmpty() && number.isNotEmpty()) {
                    // Add the new contact to the contactList
                    val newContact = Contact(name, number)
                    contactList.add(newContact)
                    val sortedContactList = contactList.sortedBy { it.name }
                    contactAdapter = ContactAdapter(sortedContactList)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = contactAdapter
                    contactAdapter.notifyItemInserted(contactList.size - 1)

                    Log.d("ContactApp", "Added contact: $name, $number")

                    dialog.dismiss()
                } else {
                    Log.e("ContactApp", "Name or number is empty")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
    private fun performSearch() {
        val query = searchEditText.text.toString().trim()
        val filteredContacts = contactList.filter {
            it.name.contains(query, ignoreCase = true) || it.number.contains(query)
        }
        recyclerView.adapter = ContactAdapter(filteredContacts)
    }
    override fun onBackPressed() {
        if (searchEditText.text.toString().isNotEmpty()) {
            searchEditText.text.clear() // Clear the search text
            val sortedContactList = contactList.sortedBy { it.name }
            contactAdapter = ContactAdapter(sortedContactList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = contactAdapter
        } else {
            super.onBackPressed()
        }
    }
}
class ContactAdapter(private val contactList: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.contactName)
        val numberTextView: TextView = itemView.findViewById(R.id.contactNumber)
        val callButton: ImageButton = itemView.findViewById(R.id.callButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameTextView.text = contact.name
        holder.numberTextView.text = contact.number

        holder.callButton.setOnClickListener {
            initiatePhoneCall(holder.numberTextView.text.toString(), it.context)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    private fun initiatePhoneCall(phoneNumber: String, context: Context) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(dialIntent)
    }
}



