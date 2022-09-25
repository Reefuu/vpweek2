package com.example.vpweek2

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import Database.GlobalVar
import Model.User
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vpweek2.databinding.ActivityAddEditMovieBinding
import Ayam
import Sapi
import Kambing

class AddEditMovie : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddEditMovieBinding
    private lateinit var movie: User
    var position = -1
    var image: String = ""
    var genre:String = ""


    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val uri = it.data?.data
            if(uri != null){
                baseContext.getContentResolver().takePersistableUriPermission(uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            viewBind.imageView2.setImageURI(uri)
            image = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddEditMovieBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
        if (viewBind.animalType.checkedRadioButtonId < 0) {
            viewBind.animalType.check(viewBind.animalType.getChildAt(0).id)
        }
    }
    private fun getintent(){
        position = intent.getIntExtra("position", -1)
        if(position != -1){
            val movie = GlobalVar.listDataMovie[position]
            viewBind.toolbar2.title = "Edit Hewan"
            viewBind.movieAdd.text = "Edit"
            viewBind.imageView2.setImageURI(Uri.parse(GlobalVar.listDataMovie[position].imageUri))
            viewBind.Rating.editText?.setText(movie.rating)
            viewBind.Title.editText?.setText(movie.title)
            if (movie.genre == "Sapi") {
                viewBind.animalType.check(viewBind.animalType.getChildAt(0).id)
            } else if (movie.genre == "Ayam") {
                viewBind.animalType.check(viewBind.animalType.getChildAt(1).id)
            } else if (movie.genre == "Kambing") {
                viewBind.animalType.check(viewBind.animalType.getChildAt(2).id)
            }
        }
    }

    private fun listener(){
        viewBind.imageView2.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.movieAdd.setOnClickListener{
            var title = viewBind.Title.editText?.text.toString().trim()
            var rating = viewBind.Rating.editText?.text.toString().trim()
            if (viewBind.animalType.checkedRadioButtonId == viewBind.sapiBtn.id) {
                genre = "Sapi"
            } else if (viewBind.animalType.checkedRadioButtonId == viewBind.ayamBtn.id) {
                genre = "Ayam"
            } else if (viewBind.animalType.checkedRadioButtonId == viewBind.kambingBtn.id) {
                genre = "Kambing"
            }
            movie = User(title, genre, rating, image)
            checker()
        }

        viewBind.toolbar2.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker()
    {
        var isCompleted:Boolean = true

        if(movie.title!!.isEmpty()){
            viewBind.Title.error = "this cannot be empty"
            isCompleted = false
        }else{
            viewBind.Title.error = ""
        }

        movie.imageUri = image

        if(viewBind.Rating.editText?.text.toString().isEmpty() || viewBind.Rating.editText?.text.toString().toInt() < 0)
        {
            viewBind.Rating.error = "this cannot be empty"
            isCompleted = false
        }

        if(isCompleted == true)
        {
            var hewanApa: User = movie
            if (position == -1) {
                if (genre == "Sapi") {
                    hewanApa = Sapi(movie.title, movie.genre, movie.rating, movie.imageUri)
                } else if (genre == "Ayam") {
                    hewanApa = Ayam(movie.title, movie.genre, movie.rating, movie.imageUri)
                } else if (genre == "Kambing") {
                    hewanApa = Kambing(movie.title, movie.genre, movie.rating, movie.imageUri)
                }
                GlobalVar.listDataMovie.add(hewanApa)

            } else {
                if (genre == "Sapi") {
                    hewanApa = Sapi(movie.title, movie.genre, movie.rating, movie.imageUri)
                } else if (genre == "Ayam") {
                    hewanApa = Ayam(movie.title, movie.genre, movie.rating, movie.imageUri)
                } else if (genre == "Kambing") {
                    hewanApa = Kambing(movie.title, movie.genre, movie.rating, movie.imageUri)
                }
                GlobalVar.listDataMovie[position] = hewanApa
            }
            finish()
        }
    }
}