package bg.replacername.replacername.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import bg.replacername.replacername.utils.SingletonHolder

private const val DATABASE_NAME = "DeskWorkout.db"
private const val DATABASE_VERSION = 1

//private val CREATE_WORKOUT_TABLE_SQL = """CREATE TABLE ${WorkoutDB.TABLE_NAME} (
//    ${WorkoutDB.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//    ${WorkoutDB.Columns.NAME} VARCHAR(50) NOT NULL,
//    ${WorkoutDB.Columns.IMAGE_NAME} VARCHAR(50) NOT NULL,
//    ${WorkoutDB.Columns.DESCRIPTION} VARCHAR(100) NOT NULL,
//    ${WorkoutDB.Columns.TYPE} VARCHAR(10) NOT NULL,
//    ${WorkoutDB.Columns.DO_TIMES} INTEGER DEFAULT 0)""".replaceIndent(" ")

private val CREATE_NAMES_TABLE_SQL = """CREATE TABLE ${NamesDB.TABLE_NAME} (
    ${NamesDB.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
    ${NamesDB.Columns.CYRILLIC_NAME} VARCHAR(50) NOT NULL,
    ${NamesDB.Columns.LATIN_NAME} VARCHAR(50) NOT NULL)""".replaceIndent(" ")

//private val CREATE_WORKOUT_EXERCISES_TABLE_SQL = """CREATE TABLE ${WorkoutsExercisesDB.TABLE_NAME}(
//    ${WorkoutsExercisesDB.Columns.WORKOUT_ID} INTEGER NOT NULL,
//    ${WorkoutsExercisesDB.Columns.EXERCISE_ID} INTEGER NOT NULL,
//    ${WorkoutsExercisesDB.Columns.SERIES_COUNT} INTEGER DEFAULT 15,
//    FOREIGN KEY(${WorkoutsExercisesDB.Columns.WORKOUT_ID}) REFERENCES ${WorkoutDB.TABLE_NAME}(${WorkoutDB.Columns.ID}) ON DELETE CASCADE ON UPDATE CASCADE,
//    FOREIGN KEY(${WorkoutsExercisesDB.Columns.EXERCISE_ID}) REFERENCES ${ExerciseDB.TABLE_NAME}(${ExerciseDB.Columns.ID}) ON DELETE CASCADE ON UPDATE CASCADE)""".replaceIndent(" ")

//Този клас наследник на SQLiteOpenHelper  създава SQLite база данни
internal class AppDatabase (context : Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(CREATE_WORKOUT_TABLE_SQL)
        db.execSQL(CREATE_NAMES_TABLE_SQL)
        //db.execSQL(CREATE_PROJECT_TABLE_SQL)
//        db.execSQL(CREATE_WORKOUT_EXERCISES_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        if (newVersion > oldVersion) {
            onCreate(db)

        } else {
            // otherwise, create the database
            onCreate(db)
        }
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase) //sync database
}