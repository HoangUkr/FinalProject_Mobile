package com.example.quizapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Questions.class, version = 1)
public abstract class QuestionRoomDatabase extends RoomDatabase {

    private static QuestionRoomDatabase INSTANCE;

    public abstract QuestionDao questionDao();

    public static synchronized  QuestionRoomDatabase getInstance(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QuestionRoomDatabase.class, "question_database")
                    .fallbackToDestructiveMigration().addCallback(RoomDBCallBack).build();
        }
        return INSTANCE;

    }

    private static RoomDatabase.Callback RoomDBCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private  QuestionDao questionDao;

        private PopulateDbAsyncTask(QuestionRoomDatabase db){
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.insert(new Questions("The area of a right triangle is 50. One of its angles is 45°. Find the lengths of the catheti of the triangle.",
                    "5", "6", "7", "8", "10th Grade", "Math",1));

            questionDao.insert(new Questions("In a right triangle ABC, tan(A) = 3/4. Find sin(A) and cos(A).",
                    "2/3, 3/5", "1/2, 1/2", "3/5, 4/5", "7/8, 1/8", "10th Grade", "Math", 3));

            questionDao.insert(new Questions("Which line given by its equation below contains the points (1, -1) and (3, 5)?",
                    "-2y -6x = 0", "2y = 6x - 8", "y = 3x + 4", "y = -3x + 4", "10th Grade", "Math", 2));

            questionDao.insert(new Questions("Simplify the algebraic expression -2(x - 3) + 4(-2 x + 8)",
                    "-10x + 38", "-10x + 36", "-8x - 38", "-8x + 38", "10th Grade", "Math", 1));

            questionDao.insert(new Questions("Simplify ( 8 x3 ) / ( 2 x(-3))",
                    "-4x6", "4x(-6)", "4x0", "4x6", "10th Grade", "Math", 4));

            //====Chemistry=====//
            questionDao.insert(new Questions("The atomic number of an atom is always equal to",
                    "the number of neutrons", "the number of electrons", "the number of protons", "the number of protons plus neutrons", "10th Grade", "Chemistry",3));

            questionDao.insert(new Questions("Carbohydrates are made up of the three elements of",
                    "nitrogen, oxygen, and hydrogen.", "carbon, hydrogen, and oxygen.", "sulfur, hydrogen, and oxygen.", "nitrogen, phosphorus, and oxygen.", "10th Grade", "Chemistry", 2));

            questionDao.insert(new Questions("The alkaline earth metal in period 5 is",
                    "Vanadium", "Niobium", "Rubidium", "Strontium", "10th Grade", "Chemistry", 1));

            questionDao.insert(new Questions("To balance a chemical equation, you may adjust the",
                    "coefficients", "subscripts", "formulas of the product.", "either of coefficients or subscripts.", "10th Grade", "Chemistry", 1));

            questionDao.insert(new Questions("Which of the following terms refers to anything that takes up space and has mass?",
                    "Matter", "Element", "Electron", "Proton", "10th Grade", "Chemistry", 1));
            //=====Physics====//

            questionDao.insert(new Questions("Given vectors v1=<0,−3,2> and v2=<−3,4,5>, find v1+v2 :",
                    "<-3;4;6>", "<-4;1;6>", "<-4;2;4>", "<-3;1;7>", "12th Grade", "Math", 4));

            questionDao.insert(new Questions("Find a so that the vectors <a,−6,3> and <1,0,−2> are perpendicular. :",
                    "5", "6", "7", "8", "12th Grade", "Math", 2));

            questionDao.insert(new Questions("An aircraft tracking station determines the distance from a common point O to each aircraft and the angle between the aicrafts. If angle O between the two aircrafts is equal to 49 o and the distances from point O to the two aircrafts are 50 km and 72 km, find distance d between the two aircrafts.(round answers to 1 decimal place).",
                    "54", "54.2", "54.4", "54.6", "12th Grade", "Math", 3));

            questionDao.insert(new Questions("A ship leaves port at 1 pm traveling north at the speed of 30 miles/hour. At 3 pm, the ship adjusts its course 20 degrees eastward. How far is the ship from the port at 4pm? (round to the nearest unit).",
                    "88", "89", "90", "87", "12th Grade", "Math", 2));

            questionDao.insert(new Questions(" A triangle has sides equal to 4 m, 11 m and 8 m. Find its angles (round answers to 1 decimal place).",
                    "72, 38, 70", "129.8, 34, 16.2", "24, 36, 120", "20, 72, 88", "12th Grade", "Math", 2));
            return null;
        }
    }
}
