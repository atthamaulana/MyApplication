package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityUbahDataBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event saat button retronft di klik
        binding.imagebuttonretro.setOnClickListener {
            replaceFragment(RetroFragment())
        }
        binding.textbuttonretro.setOnClickListener {
            replaceFragment(RetroFragment())
        }

        //event saat button sharknft di klik
        binding.imagebuttonshark.setOnClickListener {
            replaceFragment(SharkFragment())
        }
        binding.textbuttonshark.setOnClickListener {
            replaceFragment(SharkFragment())
        }

        //event saat button racernft di klik
        binding.imagebuttonracer.setOnClickListener {
            replaceFragment(RacerFragment())
        }
        binding.textbuttonracer.setOnClickListener {
            replaceFragment(RacerFragment())
        }

        binding.imagebuttonubah.setOnClickListener {
            navigateToUbahData()
        }
        binding.textbuttonubah.setOnClickListener {
            navigateToUbahData()
        }

    }
    //ganti fragment
    fun replaceFragment(frg: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTrx = fragmentManager.beginTransaction()
        fragmentTrx.replace(R.id.fragmentbutton,frg)
        fragmentTrx.commit()
    }

    private fun navigateToUbahData() {
        val intent = Intent(this, UbahActivity::class.java)
        startActivity(intent)
    }



}