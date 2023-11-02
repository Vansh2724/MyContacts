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

    // Change contactList to a MutableList to make it mutable
    private val contactList = mutableListOf(
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sortedContactList = contactList.sortedBy { it.name }
        recyclerView = binding.contactList // Initialize recyclerView using the binding
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(sortedContactList)

        binding.addContactButton.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Contact")
            .setPositiveButton("Add") { dialog, _ ->
                val name = dialogView.findViewById<EditText>(R.id.nameEditText).text.toString()
                val number = dialogView.findViewById<EditText>(R.id.numberEditText).text.toString()

                if (name.isNotEmpty() && number.isNotEmpty()) {
                    // Add the new contact to the contactList
                    contactList.add(Contact(name, number))
                    recyclerView.adapter?.notifyItemInserted(contactList.size - 1)
                    recyclerView.scrollToPosition(contactList.size - 1)
                    val sortedContactList = contactList.sortedBy { it.name }
                    recyclerView = binding.contactList // Initialize recyclerView using the binding
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = ContactAdapter(sortedContactList)

                    Log.d("ContactApp", "Added contact: $name, $number")

                    dialog.dismiss()
                } else {
                    Log.e("ContactApp", "Name or number is empty")
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
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

        // Set an OnClickListener for the call button
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



