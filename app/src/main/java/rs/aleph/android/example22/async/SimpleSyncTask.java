package rs.aleph.android.example22.async;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import rs.aleph.android.example22.R;
import rs.aleph.android.example22.tools.ReviewerTools;

/**
 * Created by milossimic on 10/22/16.
 * AsyncTask klasa prima tri parametra prilikom specijalizacije
 * Korisnici sami definisu tip u zavisnosti od posla koji zele da obave.
 *
 * Prvi argument predstavlja ulazne parametre, ono so zelimo
 * da posaljemo zadatku. Recimo ime fajla koji zelimo da skinemo
 *
 * Drugi argument je indikator kako ce se meriti progres. Koliko je posla
 * zavrseno i koliko je opsla ostalo.
 *
 * Treci parametar je povratna vrednost, tj sta ce metoda doInBackground
 * vratiti kao poratnu vrednost metodi onPostExecute
 */
public class SimpleSyncTask extends AsyncTask<Integer, Void, Integer>{

    private Context context;
    private static int NOTIFICATION_ID = 1;
    String className = getClass().getName();

    public SimpleSyncTask(Context context) {
        this.context = context;
    }

    /**
     * Metoda se poziva pre samog starta pozadinskog zadatka
     * Sve pripreme odraditi u ovoj metodi, ako ih ima.
     */
    @Override
    protected void onPreExecute() {
    }


    /**
     * Posao koji se odvija u pozadini, ne blokira glavnu nit aplikacije.
     * Sav posao koji dugo traje izvrsavati unutar ove metode.
     */
    @Override
    protected Integer doInBackground(Integer... params) {
        try {
            //simulacija posla koji se obavlja u pozadini i traje duze vreme
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return params[0];
    }


    /**
     *
     * Kada se posao koji se odvija u pozadini zavrsi, poziva se ova metoda
     * Ako je potrebno osloboditi resurse ili obrisati elemente koji vise ne trebaju.
     */
    @Override
    protected void onPostExecute(Integer products) {
        Toast.makeText(context, ReviewerTools.getConnectionType(products), Toast.LENGTH_SHORT).show();
        showNotification();

    }

    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_name);

                builder.setSmallIcon(R.drawable.ic_action_name);
                builder.setContentTitle(context.getString(R.string.notification_title));
                builder.setContentText(context.getString(R.string.notification_text));
                builder.setLargeIcon(bitmap);
                builder.setLights(Color.YELLOW, 100, 100);
                builder.setVibrate(new long[]{300,500,300,500});
        Log.d(className,"Definisani su parametri notifikacija.");

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());

    }
}
