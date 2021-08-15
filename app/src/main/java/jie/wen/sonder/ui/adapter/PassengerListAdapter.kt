package jie.wen.sonder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jie.wen.sonder.R
import jie.wen.sonder.databinding.PassengerListItemViewBinding
import jie.wen.sonder.model.dto.PassengerDataDTO

class PassengerListAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PassengerListAdapter.PassengerListItemViewHolder>() {
    interface OnItemClickListener {
        fun onItemClicked(passengerDataDTO: PassengerDataDTO)
    }

    val items: ArrayList<PassengerDataDTO> = ArrayList()
    private lateinit var binding: PassengerListItemViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerListAdapter.PassengerListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = PassengerListItemViewBinding.inflate(layoutInflater, parent, false)
        return PassengerListItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: PassengerListItemViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    inner class PassengerListItemViewHolder(itemBinding: PassengerListItemViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(passengerDataDTO: PassengerDataDTO, onItemClickListener: OnItemClickListener) {
            with(binding) {
                passengerDataDTO.run {
                    nameView.text = this.name
                    tripsView.text = trips.toString()

                    this.airlineDatumDTOS?.let {
                        Picasso.get()
                            .load(it[0].logo)
                            .error(R.drawable.ic_dummy_logo)
                            .fit()
                            .into(logoView)
                        airlineView.text = it[0].name
                        countryView.text = it[0].country
                    }
                }

                root.setOnClickListener{
                    onItemClickListener.onItemClicked(passengerDataDTO)
                }
            }
        }
    }
}