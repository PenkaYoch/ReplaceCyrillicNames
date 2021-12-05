package bg.replacername.replacername.fragments

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bg.replacername.replacername.R
import bg.replacername.replacername.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NameFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(NameViewModel::class.java) }

    private val mAdapter = NameRecyclerViewAdapter(null)

    private var cursor: Cursor? = null

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, { cursor: Cursor ->
            if (cursor.count == 0) {
                viewModel.fillDatabaseWithDefaultExercises()
            } else {
                mAdapter.swapCursor(cursor)?.close()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.replaceButton.setOnClickListener {
            binding.changedTextView.text =
                this.viewModel.checkAndReplaceNames(binding.insertTextView.text.toString())
        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.namesListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.namesListRecyclerView.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}