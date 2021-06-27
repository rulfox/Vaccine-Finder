package com.arany.corona

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arany.corona.Constants.NO_SELECTED_INDEX
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.databinding.ItemDefaultPowerSpinnerLibraryBinding


class DistrictAdapter(powerSpinnerView: PowerSpinnerView) : RecyclerView.Adapter<DistrictAdapter.IconSpinnerViewHolder>(), PowerSpinnerInterface<DistrictAbstract.District> {

    override var index: Int = powerSpinnerView.selectedIndex
    override val spinnerView: PowerSpinnerView = powerSpinnerView
    override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<DistrictAbstract.District>? = null

    private val spinnerItems: MutableList<DistrictAbstract.District> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconSpinnerViewHolder {
        val binding = ItemDefaultPowerSpinnerLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IconSpinnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                notifyItemSelected(position)
            }
        }
    }

    override fun onBindViewHolder(holder: IconSpinnerViewHolder, position: Int) {
        holder.bind(spinnerItems[position], spinnerView)
    }

    override fun setItems(itemList: List<DistrictAbstract.District>) {
        this.spinnerItems.clear()
        this.spinnerItems.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun notifyItemSelected(index: Int) {
        if (index == NO_SELECTED_INDEX) return
        val oldIndex = this.index
        this.index = index
        this.spinnerView.notifyItemSelected(index, spinnerItems[index].districtName.toString())
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = oldIndex.takeIf { it != NO_SELECTED_INDEX }?.let { spinnerItems[oldIndex] },
            newIndex = index,
            newItem = spinnerItems[index]
        )
    }

    override fun getItemCount() = this.spinnerItems.size

    class IconSpinnerViewHolder(private val binding: ItemDefaultPowerSpinnerLibraryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DistrictAbstract.District, spinnerView: PowerSpinnerView) {
            binding.itemDefaultText.apply {
                text = item.districtName
                setTextColor(spinnerView.currentTextColor)
            }
            binding.root.setPadding(
                spinnerView.paddingLeft,
                spinnerView.paddingTop,
                spinnerView.paddingRight,
                spinnerView.paddingBottom
            )
        }
    }
}
