package com.mehrab.mvvm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {
    private static PostDatabase instance;
    public abstract PostDao postDao();
    public static synchronized PostDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PostDatabase.class, "post_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("room_db","database created");
            //new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //Log.d("room_db","database opened");
            //new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostDao postDao;
        private PopulateDbAsyncTask(PostDatabase db) {
            postDao = db.postDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            postDao.insert(new Post(1,1,"asasas","asasas"));
            postDao.insert(new Post(1,2,"asasas","asasas"));
            postDao.insert(new Post(1,3,"asasas","asasas"));
            postDao.insert(new Post(1,4,"asasas","asasas"));
            return null;
        }
    }
}
