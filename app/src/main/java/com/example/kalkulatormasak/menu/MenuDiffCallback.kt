package com.example.kalkulatormasak.menu

import androidx.recyclerview.widget.DiffUtil
import com.example.kalkulatormasak.model.Bahan
import com.example.kalkulatormasak.model.Menu
import com.example.kalkulatormasak.model.MenuWithBahan

class MenuDiffCallback(private val oldData: List<MenuWithBahan>, private val newData: List<MenuWithBahan>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].menu.menuId == newData[newItemPosition].menu.menuId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]

        return old.menu == new.menu && old.listBahan == new.listBahan
    }
}