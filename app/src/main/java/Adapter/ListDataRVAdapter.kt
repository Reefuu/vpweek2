package Adapter

import Database.GlobalVar
import Interface.CardListener
import Model.User
import Model.Biji
import Model.Rumput
import Ayam
import Sapi
import Kambing
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.vpweek2.AddEditMovie
import com.example.vpweek2.R
import com.example.vpweek2.databinding.CardFilmBinding

class ListDataRVAdapter(val listUser: ArrayList<User>, val cardListener: CardListener) :
    RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {

    class viewHolder(val itemView: View, val cardListener: CardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding = CardFilmBinding.bind(itemView)


        fun setData(data: User) {
            binding.namaHewan.text = data.title
            binding.jenisHewan.text = data.genre
            binding.usia.text = "Usia: " + data.rating + " tahun"
            if (data.imageUri!!.isNotEmpty()) {
                binding.picCard.setImageURI(Uri.parse(data.imageUri))
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_film, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(GlobalVar.listDataMovie[position])
        with(holder) {
            binding.editbtnn.setOnClickListener {
                val myintent = Intent(it.context, AddEditMovie::class.java).apply {
                    putExtra("position", position)
                }
                it.context.startActivity(myintent)
            }
            binding.delbtnn.setOnClickListener {
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Delete Animal")
                builder.setMessage("Are you sure you want to delete this animal?")

                builder.setPositiveButton(android.R.string.yes) { function, which ->

                    Toast.makeText(it.context, "Animal Deleted", Toast.LENGTH_SHORT).show()

                    GlobalVar.listDataMovie.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemChanged(position, itemCount)
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(
                        it.context,
                        android.R.string.no, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.show()

            }
            binding.imageView3.setOnClickListener {
                Toast.makeText(
                    it.context,
                    GlobalVar.listDataMovie[position].pegang(),
                    Toast.LENGTH_SHORT
                ).show()

            }
            binding.imageView4.setOnClickListener {
                if(GlobalVar.listDataMovie[position] is Ayam){
                    Toast.makeText(it.context, GlobalVar.listDataMovie[position].kasiMakan(Biji()), Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(it.context, GlobalVar.listDataMovie[position].kasiMakan(Rumput()), Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}