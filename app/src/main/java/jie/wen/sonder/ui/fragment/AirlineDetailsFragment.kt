package jie.wen.sonder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import jie.wen.sonder.MainActivity
import jie.wen.sonder.R
import jie.wen.sonder.databinding.AirlineDetailsFragmentBinding
import jie.wen.sonder.model.dto.PassengerDataDTO

class AirlineDetailsFragment : Fragment() {
    private lateinit var binding: AirlineDetailsFragmentBinding

    companion object {
        fun newInstance() = AirlineDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = AirlineDetailsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getSerializable(MainActivity.PASSENGER_DETAILS) as? PassengerDataDTO)?.let { passengerData ->
            passengerData.airlineDatumDTOS?.let { airlineData ->
                binding.content.text = resources.getString(R.string.detail_content, passengerData.name, passengerData.trips)

                binding.logoView.let {
                    Picasso.get()
                        .load(airlineData[0].logo)
                        .error(R.drawable.ic_dummy_logo)
                        .fit()
                        .into(it)
                }

                binding.airlineName.text = airlineData[0].name
                binding.slogan.text = airlineData[0].slogan
                binding.address.text = airlineData[0].headQuaters
                binding.website.text = airlineData[0].webSite
            }
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.setActionBarTitle(resources.getString(R.string.details_title))
        (activity as? MainActivity)?.showActionBarBackButton(true)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as? MainActivity)?.onBackPressed()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}