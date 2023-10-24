package com.example.databasefirebase
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase

//Create
fun addContacto(contacto: Contacto) {
    // Log the attempt to add a contact
    Log.d("Firebase", "Attempting to add contact: $contacto")
    // Get a reference to the database
    val database = FirebaseDatabase.getInstance()
    val contactsRef = database.getReference("contactos")
    // Attempt to add the contact and log the result
    contactsRef.push().setValue(contacto).addOnSuccessListener {
        Log.d("Firebase", "Successfully added contact: $contacto")
    }.addOnFailureListener { exception ->
        Log.e("Firebase", "Failed to add contact: $contacto", exception)
    }
}

//Read
fun readContacto(onResult: (List<Contacto>)-> Unit){
    val database = FirebaseDatabase.getInstance()
    val contactsRef = database.getReference("contactos")

    val listener = object : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot){
            val contactos = dataSnapshot.children.mapNotNull {
                it.getValue(Contacto::class.java)
            }
            onResult(contactos)
        }
        override fun onCancelled(error: DatabaseError) {
            // Handle database error
            Log.e("DatabaseError", error.message)
        }
    }
    contactsRef.addValueEventListener(listener)
}

//Update
fun updateContacto(contacto: Contacto){
    val database = FirebaseDatabase.getInstance()
    val contactsRef = database.getReference("contactos")

    contactsRef.child(contacto.id).setValue(contacto)
}

// Delete
fun deleteContacto(contacto: Contacto){
    val database = FirebaseDatabase.getInstance()
    val contactsRef = database.getReference("contactos")

    contactsRef.child(contacto.id).removeValue()
}
