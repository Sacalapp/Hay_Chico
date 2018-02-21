package sacalapp.hay_chico;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by jarvicfelipe on 12/05/2017.
 */

public class UpdateTask  extends AsyncTask<Void, Void, Void> {

     Context mCon;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Set a time to simulate a long update process.
            Thread.interrupted();

            return null;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Give some feedback on the UI.
        //Toast.makeText(mCon, "Finished complex background function!",Toast.LENGTH_LONG).show();

        // Change the menu back
        ((Notificacion_partido) mCon).resetUpdating();
    }


    public UpdateTask(Context con) {
        mCon = con;

    }
}
