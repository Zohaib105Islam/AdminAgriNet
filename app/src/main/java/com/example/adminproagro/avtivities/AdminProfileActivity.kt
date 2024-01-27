package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivityAdminProfileBinding
import com.example.adminproagro.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminRefrence: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // intialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminRefrence = database.reference.child("user")

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.name.isEnabled = false
        binding.address.isEnabled = false
        binding.email.isEnabled = false
        binding.phone.isEnabled = false
        binding.password.isEnabled = false
        binding.saveInfoBtn.isEnabled = false

        // var isEnable=false
        binding.editImg.setOnClickListener {

            binding.name.isEnabled = !binding.name.isEnabled
            binding.email.isEnabled = !binding.email.isEnabled
            binding.address.isEnabled = !binding.address.isEnabled
            binding.phone.isEnabled = !binding.phone.isEnabled
            binding.password.isEnabled = !binding.password.isEnabled
            binding.name.requestFocus()
            binding.saveInfoBtn.isEnabled = ! binding.saveInfoBtn.isEnabled

        }

        binding.saveInfoBtn.setOnClickListener(){
            updateUserData()
            binding.name.isEnabled = false
            binding.address.isEnabled = false
            binding.email.isEnabled = false
            binding.phone.isEnabled = false
            binding.password.isEnabled = false
            binding.saveInfoBtn.isEnabled = false
        }

        retrieveUserData()
    }



    private fun retrieveUserData() {

        val userEmail:String = auth.currentUser?.email?.toString()?:""
        val hashedEmail = userEmail.replace('.', ',')

        if (hashedEmail != null){
            val userRefrence1 = adminRefrence.child(hashedEmail)

                userRefrence1.addListenerForSingleValueEvent(object  : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            var ownerName = snapshot.child("name").getValue()
                            var email = snapshot.child("email").getValue()
                            var password = snapshot.child("password").getValue()
                            var address = snapshot.child("address").getValue()
                            var phone = snapshot.child("phone").getValue()

                            setDataToTextView(ownerName,email,password,address,phone)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }


    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.password.setText(password.toString())
        binding.address.setText(address.toString())
        binding.phone.setText(phone.toString())
    }


    private fun updateUserData() {
       var updateName =  binding.name.text.toString()
      var updateEmail =   binding.email.text.toString()
       var updatePassword =  binding.password.text.toString()
      var updatePhone =   binding.address.text.toString()
      var updateAddress =   binding.phone.text.toString()

        val userEmail:String = auth.currentUser?.email?.toString()?:""
        val hashedEmail = userEmail.replace('.', ',')
        if(hashedEmail != null){

            val userRefrence = adminRefrence.child(hashedEmail)
            userRefrence.child("name").setValue(updateName)
            userRefrence.child("email").setValue(updateEmail)
            userRefrence.child("password").setValue(updatePassword)
            userRefrence.child("phone").setValue(updatePhone)
            userRefrence.child("address").setValue(updateAddress)

            Toast.makeText(this,"Profile updated !!",Toast.LENGTH_LONG).show()
            // update email and password in firebase authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }else{
            Toast.makeText(this,"Profile not updated !!",Toast.LENGTH_LONG).show()
        }

    }
}