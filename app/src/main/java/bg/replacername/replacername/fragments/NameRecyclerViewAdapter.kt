package bg.replacername.replacername.fragments

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bg.replacername.replacername.database.NamesDB
import bg.replacername.replacername.databinding.ListItemNamesBinding
import bg.replacername.replacername.models.Name

class NameRecyclerViewAdapter(
    private var cursor: Cursor?)//private val values: List<Workout>?
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ListItemNamesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NamesViewHolder(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cursor = cursor

        if (cursor != null && cursor.count != 0) {
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }
            val item = Name(cyrillicName = cursor.getString(cursor.getColumnIndex(NamesDB.Columns.CYRILLIC_NAME)),
                latinName = cursor.getString(cursor.getColumnIndex(NamesDB.Columns.LATIN_NAME)))

            val namesView = (holder as NamesViewHolder).namesViewHolder
            namesView.cyrillicTextView.text = item.cyrillicName
            namesView.latinNameTextView.text = item.latinName
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    inner class NamesViewHolder(val namesViewHolder: ListItemNamesBinding) : RecyclerView.ViewHolder(namesViewHolder.root)

    fun swapCursor(newCursor: Cursor?): Cursor? {
        if (newCursor === cursor) {
            return null
        }
        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor
        if (newCursor != null) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, numItems)
        }

        return oldCursor
    }
}