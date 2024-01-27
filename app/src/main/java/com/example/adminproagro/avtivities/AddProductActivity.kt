
package com.example.adminproagro.avtivities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminproagro.databinding.ActivityAddProductBinding
import com.example.adminproagro.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddProductActivity : AppCompatActivity() {

    // Constants
    private val IMAGE_REQUEST_CODE = 1

    private val binding: ActivityAddProductBinding by lazy {
        ActivityAddProductBinding.inflate(layoutInflater)
    }

    // Product
    private lateinit var productName: String
    private lateinit var productPrice: String
    private lateinit var productDescription: String
    private lateinit var productIngredients: String
    private var productImageUri: Uri? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private var galleryLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Firebase & database Instance
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.chooseImg.setOnClickListener {
            openGallery()
        }

        binding.addProductBtn.setOnClickListener {
            addProduct()
        }

        // Initialize the gallery launcher
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    binding.selectedImageView.setImageURI(it)
                    productImageUri = it
                }
            }
        }
    }

    private fun addProduct() {
        productName = binding.etProductName.text.toString().trim()
        productPrice = binding.etProductPrice.text.toString().trim()
        productDescription = binding.etProductDisc.text.toString().trim()
        productIngredients = binding.etProductIngredient.text.toString().trim()

        if (!(productName.isBlank() || productPrice.isBlank() || productDescription.isBlank() || productIngredients.isBlank())) {
            uploadData()
          //  Toast.makeText(this, "Product added Successfully!!", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "Fill in all the details!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadData() {
        val menuRef = database.getReference("product")
        val newItemKey: String? = menuRef.push().key

        if (productImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("product_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(productImageUri!!)
           // Toast.makeText(this, "Image upload in Storage!!", Toast.LENGTH_LONG).show()


            uploadTask.addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    Toast.makeText(this, "Image upload in Storage!!", Toast.LENGTH_LONG).show()

                    // Create a new menu item
                    val newItem = AllMenu(
                        newItemKey,
                        productName = productName,
                        productPrice = productPrice,
                        productDescription = productDescription,
                        productIngredient = productIngredients,
                        productImage = downloadUrl.toString()
                    )

                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data uploaded in RealTime!!", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Data not upload in RealTime!!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image not upload in Storage!!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Please select an image!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher?.launch(intent)
    }
}

