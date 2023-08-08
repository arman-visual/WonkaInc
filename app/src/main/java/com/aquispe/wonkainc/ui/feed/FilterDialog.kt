package com.aquispe.wonkainc.ui.feed

import android.app.Activity
import android.app.AlertDialog
import android.widget.ArrayAdapter
import com.aquispe.wonkainc.R
import com.aquispe.wonkainc.databinding.LayoutFilterSearchBinding

class FilterDialog(
    private var activity: Activity,
    private val onClickApplyFilter: (String?, String?) -> Unit,
) {

    fun create(): AlertDialog {

        val binding = LayoutFilterSearchBinding.inflate(activity.layoutInflater)

        val filterDialog = AlertDialog.Builder(activity)
            .setCancelable(true)
            .setView(binding.root)
            .create()

        spinnersConfiguration(binding)

        binding.btApplyFilter.setOnClickListener {
            val genderSelected = binding.spGender.selectedItem as String?
            val gender = if (genderSelected == "Select") null else {
                if (genderSelected.equals("Female"))
                    "F"
                else
                    "M"
            }

            val professionSelected = binding.spProfession.selectedItem as String?
            val profession = if (professionSelected == "Select") null else professionSelected

            onClickApplyFilter(gender, profession)
            filterDialog.dismiss()
        }

        binding.tvClearFilter.setOnClickListener {
            binding.spGender.setSelection(0)
            binding.spProfession.setSelection(0)
            onClickApplyFilter(null, null)
            filterDialog.dismiss()
        }

        return filterDialog
    }

    private fun spinnersConfiguration(binding: LayoutFilterSearchBinding) {
        ArrayAdapter.createFromResource(
            activity,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spGender.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            activity,
            R.array.profession_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spProfession.adapter = adapter
        }
    }
}
