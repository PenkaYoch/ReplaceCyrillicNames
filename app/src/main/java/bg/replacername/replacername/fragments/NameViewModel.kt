package bg.replacername.replacername.fragments

import android.app.Application
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bg.replacername.replacername.database.NamesDB
import bg.replacername.replacername.models.Name
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NameViewModel (application: Application) : AndroidViewModel(application) {

    private val contentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            loadNames()
        }
    }

    private val databaseCursor = MutableLiveData<Cursor>()
    val cursor: LiveData<Cursor>
        get() = databaseCursor

    init {
        getApplication<Application>().contentResolver.registerContentObserver(
            NamesDB.CONTENT_URI,
            true, contentObserver
        )
        loadNames()

    }

    private fun loadNames() {
        GlobalScope.launch {
            val cursor = getApplication<Application>().contentResolver
                .query(NamesDB.CONTENT_URI, null, null, null, NamesDB.Columns.ID) //order
            databaseCursor.postValue(cursor)
        }
    }

    fun putRelation(newRelation: Name) { //, isUpdate: Boolean, oldFacNumber: Long
        val values = ContentValues()
        values.put(NamesDB.Columns.CYRILLIC_NAME, newRelation.cyrillicName)
        values.put(NamesDB.Columns.LATIN_NAME,newRelation.latinName)

//        if (!isUpdate){
        GlobalScope.launch {
            val uri =
                getApplication<Application>().contentResolver?.insert(
                    NamesDB.CONTENT_URI,
                    values
                )
        }
//        } else {
//            GlobalScope.launch {
//                getApplication<Application>().contentResolver?.update(
//                        WorkoutDB.buildUriFromId(oldFacNumber),values,null,null)
//            }
//        }
    }

    fun fillDatabaseWithDefaultExercises() {
        putRelation(Name(cyrillicName = "Иван", "Йоан"))
        putRelation(Name(cyrillicName = "Георги", "Джордж"))
    }

    fun checkAndReplaceNames(text: String): String {
        val regex = "([А-Я][а-я]*)".toRegex()
        val arrayOfFoundNames = mutableListOf<String>()
        val matches = regex.findAll(text)
        matches.forEach {
            if(!arrayOfFoundNames.contains(it.value)) {
                arrayOfFoundNames.add(it.value)
            }
        }
        var newText = text
        cursor.value?.moveToFirst()
        while (cursor.value?.isAfterLast == false) {

            val cyrillicName = cursor.value?.getColumnIndex(NamesDB.Columns.CYRILLIC_NAME)?.let {
                cursor.value?.getString(
                    it
                )
            }
            val latinName = cursor.value?.getColumnIndex(NamesDB.Columns.LATIN_NAME)?.let {
                cursor.value?.getString(
                    it
                )
            }
            if(cyrillicName != null && latinName != null) {
                if (arrayOfFoundNames.contains(cyrillicName)) {
                    newText = newText.replace(cyrillicName, latinName)
                }
            }
            cursor.value?.moveToNext()
        }
        return newText
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}