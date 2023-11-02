package com.example.mycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val contactList = listOf(
        Contact("Patel Tirth", "88662 48170"),
        Contact("Patel Smit", "97269 34451"),
        Contact("Chiru", "96621 52694"),
        Contact("Patel Ved", "96625 22535"),
        Contact("Patel Vansh", "84011 38433"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_item)
    }

    class ContactAdapter(private val contactList: List<Contact>) :
        RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

        class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Define views in the item layout
            val nameTextView: TextView = itemView.findViewById(R.id.contactName)
            val numberTextView: TextView = itemView.findViewById(R.id.contactNumber)
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
        }

        override fun getItemCount(): Int {
            return contactList.size
        }
    }
    val recyclerView = findViewById<RecyclerView>(R.id.contactList)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ContactAdapter(contactList)


}