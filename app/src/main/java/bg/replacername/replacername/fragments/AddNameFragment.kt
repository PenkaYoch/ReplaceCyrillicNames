package bg.replacername.replacername.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import bg.replacername.replacername.R
import bg.replacername.replacername.databinding.FragmentSecondBinding
import bg.replacername.replacername.models.Name
import android.content.DialogInterface




/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNameFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel by lazy { ViewModelProvider(this).get(NameViewModel::class.java) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val regex = "([А-Я][а-я]*)".toRegex()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            val cyrillicName = binding.cyrillicNameEditText.text.toString()
            val latinName = binding.latinNameEditText.text.toString()
            if (cyrillicName.matches(regex) && latinName.matches(regex)) {
                viewModel.putRelation(
                    Name(
                        cyrillicName = cyrillicName,
                        latinName = latinName
                    )
                )
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            } else {
                val builder1: AlertDialog.Builder = AlertDialog.Builder(context)
                builder1.setMessage(getString(R.string.not_valid_names))
                builder1.setCancelable(true)

                builder1.setPositiveButton(
                    getString(R.string.ok_button),
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

                val alert11: AlertDialog = builder1.create()
                alert11.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}