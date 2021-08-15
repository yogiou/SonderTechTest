package jie.wen.sonder.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jie.wen.sonder.MainActivity
import jie.wen.sonder.R
import jie.wen.sonder.databinding.PassengerListFragmentBinding
import jie.wen.sonder.model.dto.PassengerDataDTO
import jie.wen.sonder.model.dto.PassengerListResponseDTO
import jie.wen.sonder.other.Resource
import jie.wen.sonder.other.Status
import jie.wen.sonder.ui.adapter.PassengerListAdapter
import jie.wen.sonder.viewmodel.PassengerListViewModel

@AndroidEntryPoint
class PassengerListFragment : Fragment(), PassengerListAdapter.OnItemClickListener {
    private lateinit var passengerListAdapter: PassengerListAdapter
    private lateinit var binding: PassengerListFragmentBinding
    private lateinit var viewModel: PassengerListViewModel

    companion object {
        fun newInstance() = PassengerListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PassengerListFragmentBinding.inflate(layoutInflater)
        passengerListAdapter = PassengerListAdapter(this)
        binding.passengerListView.run {
            if (adapter == null) {
                adapter = passengerListAdapter
                addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
                layoutManager = LinearLayoutManager(context)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (!recyclerView.canScrollVertically(1) && dy > 0
                            && passengerListAdapter.itemCount > 0) {
                            //scrolled to BOTTOM
                            loadPassengerList()
                        }
                    }
                })
            }
        }

        viewModel = ViewModelProvider(this).get(PassengerListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val passengerListResponseObserver = Observer<Resource<PassengerListResponseDTO>> { response ->
            response?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.data?.let { passengerList ->
                            val pos = passengerListAdapter.itemCount
                            passengerListAdapter.items.addAll(passengerList)
                            passengerListAdapter.notifyItemRangeInserted(pos, passengerList.size)
                        }

                        binding.loadingIndicator.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingIndicator.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.loadingIndicator.visibility = View.GONE
                        it.message?.let { msg ->
                            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }

        viewModel.passengerListResponseDTOLiveData.observe(viewLifecycleOwner, passengerListResponseObserver)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (passengerListAdapter.itemCount == 0 && binding.passengerListView.isEnabled) {
            loadPassengerList()
        }

        (activity as? MainActivity)?.setActionBarTitle(resources.getString(R.string.passenger_list))
        (activity as? MainActivity)?.showActionBarBackButton(false)
    }

    override fun onItemClicked(passengerDataDTO: PassengerDataDTO) {
        (activity as? MainActivity)?.goToPassengerDetailsFragment(passengerDataDTO)
    }

    private fun loadPassengerList() {
        (activity as? MainActivity)?.let {
            context?.let { context ->
                if (it.isOnline(context)) {
                    viewModel.makeApiCall()
                }
            }
        }
    }
}