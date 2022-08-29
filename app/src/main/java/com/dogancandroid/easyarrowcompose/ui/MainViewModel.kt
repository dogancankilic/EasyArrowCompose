package com.dogancandroid.easyarrowcompose.ui

import androidx.lifecycle.ViewModel
import com.dogancandroid.easyarrowcompose.R

/**
 * @author dogancankilic
 * Created on 26.08.2022
 */
class MainViewModel : ViewModel() {
    val itemList: MutableList<Item> = mutableListOf()

    init {
        populateItems()
    }

    private fun populateItems() {
        repeat(20) {
            itemList.add(
                Item(
                    "Fjallraven - Foldsack No. $it ",
                    R.drawable.bag
                )
            )
        }

    }
}