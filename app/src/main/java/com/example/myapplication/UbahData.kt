package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class UbahActivity : AppCompatActivity() {

    private lateinit var databaseHelper1: DatabaseHelper1
    private lateinit var imageView: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextHarga: EditText
    private lateinit var editTextDeskripsi: EditText
    private lateinit var buttonChoose: Button
    private lateinit var buttonSave: Button
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button
    private lateinit var listView: ListView

    private var selectedImageUri: String? = null
    private var itemList = mutableListOf<Product>() // Menggunakan tipe data Product

    private lateinit var adapter: ArrayAdapter<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_data)

        databaseHelper1 = DatabaseHelper1(this)

        imageView = findViewById(R.id.imageView)
        editTextName = findViewById(R.id.editTextName)
        editTextHarga = findViewById(R.id.editTextHarga)
        editTextDeskripsi = findViewById(R.id.editTextDeskripsi)
        buttonChoose = findViewById(R.id.buttonChoose)
        buttonSave = findViewById(R.id.buttonSave)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)
        listView = findViewById(R.id.listView)

        // Inisialisasi adapter dengan tipe data Product
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        buttonChoose.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val harga = editTextHarga.text.toString()
            val deskripsi = editTextDeskripsi.text.toString()

            if (name.isNotEmpty() && harga.isNotEmpty() && deskripsi.isNotEmpty() && selectedImageUri != null) {
                val id = databaseHelper1.insertProduct(name, harga, deskripsi, selectedImageUri!!)
                Toast.makeText(this, "Data saved with ID: $id", Toast.LENGTH_SHORT).show()

                updateListView()

                resetInputFields()
            } else {
                Toast.makeText(this, "Please fill in all fields and choose an image", Toast.LENGTH_SHORT).show()
            }
        }

        buttonUpdate.setOnClickListener {
            val selectedItem = listView.selectedItem as? Product
            if (selectedItem != null) {
                val name = editTextName.text.toString()
                val harga = editTextHarga.text.toString()
                val deskripsi = editTextDeskripsi.text.toString()

                if (name.isNotEmpty() && harga.isNotEmpty() && deskripsi.isNotEmpty() && selectedImageUri != null) {
                    val rowsAffected = databaseHelper1.updateProduct(
                        selectedItem.id,
                        name,
                        harga,
                        deskripsi,
                        selectedImageUri!!
                    )
                    Toast.makeText(this, "Data updated. Rows affected: $rowsAffected", Toast.LENGTH_SHORT).show()

                    updateListView()

                    resetInputFields()
                } else {
                    Toast.makeText(this, "Please fill in all fields and choose an image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select an item to update", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            val selectedItem = listView.selectedItem as? Product
            if (selectedItem != null) {
                val rowsAffected = databaseHelper1.deleteProduct(selectedItem.id)
                Toast.makeText(this, "Data deleted. Rows affected: $rowsAffected", Toast.LENGTH_SHORT).show()

                updateListView()

                resetInputFields()
            } else {
                Toast.makeText(this, "Please select an item to delete", Toast.LENGTH_SHORT).show()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = itemList[position]
            // Tampilkan data yang dipilih di input fields
            editTextName.setText(selectedItem.name)
            editTextHarga.setText(selectedItem.price)
            editTextDeskripsi.setText(selectedItem.description)

            // Simpan URI gambar yang dipilih

            // Tampilkan gambar yang dipilih di ImageView
            Glide.with(this)
                .load(selectedImageUri)
                .into(imageView)

            Toast.makeText(this, "Image URI: $selectedImageUri", Toast.LENGTH_SHORT).show()
        }
    }

    private val PICK_IMAGE_REQUEST = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            selectedImageUri = imageUri.toString()

            Glide.with(this)
                .load(selectedImageUri)
                .into(imageView)

            Toast.makeText(this, "Image URI: $selectedImageUri", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateListView() {
        itemList.clear()
        itemList.addAll(databaseHelper1.getAllProducts())

        // Beritahu adapter bahwa data telah berubah
        adapter.notifyDataSetChanged()
    }

    private fun resetInputFields() {
        editTextName.text.clear()
        editTextHarga.text.clear()
        editTextDeskripsi.text.clear()
        imageView.setImageDrawable(null)
        selectedImageUri = null
    }
}
