package com.alirahimi.movieapp.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.showInvisible(status: Boolean) {
    if (status) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun RecyclerView.initRecycler(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}