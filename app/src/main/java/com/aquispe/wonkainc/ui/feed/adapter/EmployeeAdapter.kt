package com.aquispe.wonkainc.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aquispe.wonkainc.R
import com.aquispe.wonkainc.databinding.ItemEmployeeBinding
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.ui.util.loadUrlWithCircleCrop

class EmployeeAdapter(var onClickDetailPost: (Employee) -> Unit) :
    PagingDataAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(EMPLOYEE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        if (employee != null) {
            holder.bind(employee)
            holder.itemView.setOnClickListener {
                onClickDetailPost(employee)
            }
        }
    }

    companion object {
        private val EMPLOYEE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean =
                oldItem == newItem
        }
    }

    class EmployeeViewHolder(private val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.tvFullnameProfile.text = buildString {
                append(employee.firstName)
                append(" ")
                append(employee.lastName)
            }

            binding.tvProfessionTag.text = employee.profession
            binding.tvCountryTag.text = employee.country
            binding.tvEmail.text = employee.email

            if(employee.image.isNotEmpty())
                binding.circleProfileImage.loadUrlWithCircleCrop(employee.image)
            else
                binding.circleProfileImage.setImageResource(R.drawable.profile_icon)
        }
    }
}
